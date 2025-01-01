package com.iesaguadulce.lopez_salazar_mario_pmdm03.authentication;

import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.R;

import java.util.Arrays;
import java.util.List;


/**
 * Activity that handles user login using Firebase Authentication.
 * This class manages the authentication flow, including login with FirebaseUI,
 * handling session restoration, and navigation to the MainActivity based on login status.
 *
 * @author Mario López Salazar
 */
public class LoginActivity extends AppCompatActivity {


    /**
     * Called when the LoginActivity becomes visible to the user.
     * This method is used to restore a previously initiated session.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Attempting to retrieve an initially logged-in user, if available:
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Navigating directly to MainActivity when previously logged, otherwise launching authentication:
        if (user != null)
            goToMainActivity();
        else
            authentication();
    }


    /**
     * Called when there is no logged user.
     * This method uses FirebaseUI for an easy sign-in and login procedure.
     */
    private void authentication() {

        // Registering a Firebase Authentication contract, and also the Callback to be called when the authentication result is available:
        ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                this::onSignInResult
        );

        // Selecting the registration providers (by email & by Google):
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Creating and customizing the sign-in FirebaseUI intent:
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.pikachu_blue)
                .setTheme(R.style.Theme_Lopez_Salazar_Mario_PMDM03_login)
                .build();

        // Launching the sign-in intent:
        signInLauncher.launch(signInIntent);
    }


    /**
     * Called when the LoginActivity receives the Callback from the Firebase Authentication .
     * This method handles the result of the login process, determining whether it was successful or failed, and acts accordingly.
     *
     * @param result Result of the previously launched FirebaseAuthUIActivityResultContract.
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();

        if (response != null && result.getResultCode() == RESULT_OK)
            // Successfully signed in:
            goToMainActivity();
        else
            // Failed to signed in:
            Toast.makeText(this, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
    }


    /**
     * Used to finish this LoginActivity and start the MainActivity.
     */
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
