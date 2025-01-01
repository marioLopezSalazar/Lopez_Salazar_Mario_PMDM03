package com.iesaguadulce.lopez_salazar_mario_pmdm03.authentication;

import android.content.Context;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.R;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.database.Database;


/**
 * Utility class for FirebaseAuth which provides methods to sign-out and drop user the current user.
 *
 * @author Mario López Salazar
 */
public class SessionManager {


    /**
     * The context of the SettingsFragment.
     */
    private final Context context;

    /**
     * The currently signed-in FirebaseAuth user.
     */
    private final FirebaseUser user;


    /**
     * Constructs a SessionManager with the given context.
     *
     * @param context The context used to access resources and perform operations.
     */
    public SessionManager(@NonNull Context context) {
        this.context = context;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    /**
     * Gets the currently logged-in user name.
     *
     * @return The currently logged-in user name, or his/her email, or "unknown"..
     */
    public String getUserName() {
        if (user == null)
            return context.getString(R.string.unknown);
        else if (user.getDisplayName() == null)
            return user.getEmail();
        else
            return user.getDisplayName();
    }


    /**
     * Signs out the current user and executes a callback upon completion.
     *
     * @param onCompleteListener The listener to be called when the sign-out is complete.
     */
    public void signOut(OnCompleteListener<Void> onCompleteListener) {
        AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener(onCompleteListener);
    }


    /**
     * Manages the deleting user task and executes a callback upon completion.
     * This method manages the deletion when the user was logged in by email-password or by Google.
     *
     * @param onCompleteListener The listener to be called when the sign-out is complete.
     */
    public void dropUser(OnCompleteListener<Boolean> onCompleteListener) {

        if (user == null)
            // Null user (non desirable case):
            onCompleteListener.onComplete(notAuthenticationDone());

        else if (user.getProviderData().stream().anyMatch(provider -> provider.getProviderId().equals(GoogleAuthProvider.PROVIDER_ID)))
            // User authenticated using Google:
            dropUserWithGoogle(onCompleteListener);


        else if (user.getProviderData().stream().anyMatch(provider -> provider.getProviderId().equals(EmailAuthProvider.PROVIDER_ID))) {
            // User authenticated using email & password (password needs to be required to the user):
            RequirePassword.show((MainActivity) context, requiringPassword -> {
                if (requiringPassword.isSuccessful())
                    dropUserWithEmail(requiringPassword.getResult(), onCompleteListener);
                else
                    onCompleteListener.onComplete(notAuthenticationDone());
            });
        }
    }


    /**
     * Drops the currently authenticated user with Google Sign-In, and its Pokémon's collection.
     * This method first verifies the user's last signed-in Google account and re-authenticates
     * the user with Google credentials. If the re-authentication is successful, the user and its Pokémon's collection are deleted.
     *
     * @param onCompleteListener Callback to handle the completion of the user deletion process.
     */
    private void dropUserWithGoogle(OnCompleteListener<Boolean> onCompleteListener) {
        // Checking if there is a signed-in Google account:
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account == null) {
            onCompleteListener.onComplete(notAuthenticationDone());
            return;
        }

        // Retrieving Google credentials for re-authentication:
        String idToken = account.getIdToken();
        if (idToken == null) {
            onCompleteListener.onComplete(notAuthenticationDone());
            return;
        }
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        // Re-authenticating with the retrieved credentials:
        user.reauthenticate(credential).addOnCompleteListener(taskReauthenticate -> {
            if (taskReauthenticate.isSuccessful())

                // Finalizing the user and Pokémon's collection deletion:
                handleUserDeletion(onCompleteListener);
            else
                // Reauthentication failed:
                onCompleteListener.onComplete(notAuthenticationDone());
        });
    }


    /**
     * Drops the currently authenticated user using email and password for re-authentication.
     * If re-authentication with the provided credentials is successful, the user is deleted and its Pokémon's collection are deleted.
     *
     * @param password           Password of the currently authenticated user, used for re-authentication.
     * @param onCompleteListener Callback to handle the completion of the user deletion process.
     */
    private void dropUserWithEmail(String password, OnCompleteListener<Boolean> onCompleteListener) {

        // Retrieving the user's email and validating that the password is available:
        String email = user.getEmail();
        if (password == null || password.isEmpty() || email == null) {
            onCompleteListener.onComplete(notAuthenticationDone());
            return;
        }

        // Generating authentication credentials using the email and password:
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Re-authenticating the user with the generated credentials:
        user.reauthenticate(credential).addOnCompleteListener(taskReauthenticate -> {
            if (taskReauthenticate.isSuccessful())

                // Finalizing the user and Pokémon's collection deletion:
                handleUserDeletion(onCompleteListener);
            else
                // Reauthentication failed:
                onCompleteListener.onComplete(notAuthenticationDone());
        });
    }


    /**
     * Handles the user deletion process, which involves deleting the Pokémon collection associated with the user
     * and then deleting the user account itself.
     *
     * @param onCompleteListener A callback listener to handle the completion of the user deletion process.
     *                            The result will be True if both the Pokémon collection and user account
     *                            were successfully deleted, or False if any part of the process failed.
     */
    private void handleUserDeletion(OnCompleteListener<Boolean> onCompleteListener){

        // Deleting Pokémon's user collection:
        Database.dropUser(user).addOnCompleteListener( taskDeletePokemonCollection -> {

            if(taskDeletePokemonCollection.isSuccessful())
                // Deleting the user:
                AuthUI.getInstance().delete(context).addOnCompleteListener(taskDeleteUser ->
                        onCompleteListener.onComplete(Tasks.forResult(taskDeleteUser.isSuccessful())));

            else
                // Pokémon's collection couldn't be deleted:
                onCompleteListener.onComplete(Tasks.forResult(false));
        });
    }


    /**
     * Creates a Task containing a false value.
     * This is used to signal authentication-related failures.
     *
     * @return Task that immediately finishes with a false result value.
     */
    @NonNull
    public static Task<Boolean> notAuthenticationDone() {
        return Tasks.forResult(false);
    }

}
