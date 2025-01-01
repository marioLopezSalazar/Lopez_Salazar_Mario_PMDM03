package com.iesaguadulce.lopez_salazar_mario_pmdm03;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.authentication.LoginActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.authentication.SessionManager;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.LocalizationTools;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.database.Database;

import java.util.Locale;


/**
 * Subclass of PreferenceFragmentCompat designed to manage the settings screen of the application.
 * <ul>
 *     <li>Change application language dynamically.</li>
 *     <li>Allow or restrict Pokémon release functionality.</li>
 *     <li>Change light-night mode dynamically.</li>
 *     <li>Select the amount of Pokémon available in the Pokédex.</li>
 *     <li>Sign out the current user with confirmation.</li>
 *     <li>Delete the user's account with prior confirmation and re-authentication.</li>
 *     <li>Display information about the application in the "About" section.</li>
 * </ul>
 *
 * @author Mario López Salazar.
 */
public class SettingsFragment extends PreferenceFragmentCompat {


    /**
     * For launch the sign-out and the deleter user options
     */
    private SessionManager sessionManager;


    /**
     * Called when the SettingsFragment is being created.
     * This method inflates the preferences from XML resource, sets up listeners for user interactions with the preference controls
     * and initializes a SessionManager object.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the Preference with this key.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        // Inflating the view:
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Setting up a new SessionManager:
        sessionManager = new SessionManager(requireContext());

        // Setting up listeners for user interactions:
        Preference preference;

        preference = findPreference("english");
        if (preference != null)
            preference.setOnPreferenceChangeListener(this::onChangeLanguageClick);

        preference = findPreference("release");
        if (preference != null)
            preference.setOnPreferenceChangeListener(this::onAllowReleaseClick);

        preference = findPreference("light_night");
        if (preference != null)
            preference.setOnPreferenceChangeListener(this::onLightNightChange);

        preference = findPreference("pokedex_count");
        if (preference != null)
            preference.setOnPreferenceChangeListener(this::onPokedexSizeChange);

        preference = findPreference("close_session");
        if (preference != null)
            preference.setOnPreferenceClickListener(this::onCloseSessionClick);

        preference = findPreference("delete_user");
        if (preference != null)
            preference.setOnPreferenceClickListener(this::onDeleteUserClick);

        preference = findPreference("about");
        if (preference != null)
            preference.setOnPreferenceClickListener(this::onAboutClick);
    }


    /**
     * Called when the fragment becomes visible to the user.
     * This method customizes the Session category preference with the name of the currently logged-in Firebase user.
     */
    @Override
    public void onStart() {
        super.onStart();

        // Setting up the ActionBar title:
        AppCompatActivity activity;
        ActionBar actionBar;
        if ((activity = (AppCompatActivity) getActivity()) != null && (actionBar = activity.getSupportActionBar()) != null)
            actionBar.setTitle(R.string.settings);

        // Customizing the Session category title:
        Preference preference = findPreference("session_category");
        if (preference != null)
            preference.setTitle(getString(R.string.pokemon_trainer) + sessionManager.getUserName());
    }


    /**
     * Called when the user taps on the ChangeLanguage switch.
     * Launches the change of APP language between Spanish and English.
     *
     * @param preference The preference that was clicked.
     * @param o          Value of the property (true=English, false=Spanish).
     * @return True to indicate the click was handled.
     */
    private boolean onChangeLanguageClick(Preference preference, Object o) {

        // Launching change language task:
        changeLanguage(requireActivity(), (Boolean) o ? "en" : "es").addOnCompleteListener(taskChangeLanguage ->

                // Requiring the MainActivity to refresh certain visual elements:
                ((MainActivity) requireContext()).syncUILanguage()
        );
        return true;
    }


    /**
     * Changes the language of the application context to the specified language code.
     * It fetches localized types from the database, applies the language configuration, and updates the
     * resources of the given context.
     *
     * @param context      The context in which the language change should be applied.
     * @param languageCode The ISO language code (e.g., "en" or "es").
     * @return A Task that completes when the language change process is finished.
     */
    public static Task<Void> changeLanguage(Context context, String languageCode) {

        TaskCompletionSource<Void> taskChangeLanguage = new TaskCompletionSource<>();

        Database.getLocalizedTypes(languageCode.toUpperCase()).addOnCompleteListener(taskGetLocalizedTypes -> {

            // Saving localized types:
            LocalizationTools.setLocalTypes(taskGetLocalizedTypes.getResult());

            // Setting the language for this instance of the Java VM:
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);

            // Setting the language int the activity:
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

            // Marking changing language task as finished:
            taskChangeLanguage.setResult(null);
        });

        return taskChangeLanguage.getTask();
    }


    /**
     * Called when the user changes selection on Light-Night mode list.
     * Launches the change of APP theme.
     *
     * @param preference The preference that was selected.
     * @param o          Value of the property ("light", "dark" or "system").
     * @return True to indicate the change was handled.
     */
    private boolean onLightNightChange(Preference preference, Object o) {
        applyTheme((String) o);
        return true;
    }


    /**
     * Changes the Light-Night theme of the Activity.
     *
     * @param themePreference Theme selected: light", "dark" or "system".
     */
    static void applyTheme(String themePreference) {
        switch (themePreference) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }


    /**
     * Handles changes to the Pokédex size preference.
     *
     * @param preference The preference that triggered this callback.
     * @param o          The new value of the preference (last index to charge).
     * @return Always returns True indicating the preference change was handled.
     */
    private boolean onPokedexSizeChange(Preference preference, Object o) {
        int limit = Integer.parseInt((String) o);
        ((MainActivity) requireActivity()).refreshPokedex(limit);
        return true;
    }


    /**
     * Called when the user taps on the AllowRelease switch.
     * Requires the MainActivity to allow o forbid Pokémon releasing.
     *
     * @param preference The preference that was clicked.
     * @param o          Value of the property (true=Allow, false=Forbid).
     * @return True to indicate the click was handled.
     */
    private boolean onAllowReleaseClick(Preference preference, Object o) {
        ((MainActivity) requireActivity()).allowRelease(((boolean) o));
        return true;
    }


    /**
     * Handles the click event for the "Close Session" preference.
     * Displays a confirmation dialog to the user before signing out.
     * If the user confirms, method onClose() is called to perform the action.
     *
     * @param preference The preference that was clicked.
     * @return True to indicate the click was handled.
     */
    private boolean onCloseSessionClick(Preference preference) {
        new AlertDialog.Builder(requireContext(), R.style.AlertDialogStyle)
                .setTitle(R.string.close_session)
                .setIcon(R.drawable.pikachu_blue)
                .setMessage(R.string.close_session_confirmation)
                .setPositiveButton(R.string.yes, this::onClose)
                .setNegativeButton("No", null)
                .show();
        return true;
    }


    /**
     * Handles the dialog close event by signing out the user and navigating to the login screen
     * upon successful sign-out. Displays a message to the user indicating the result of the operation.
     *
     * @param dialogInterface The interface of the dialog that triggered the event.
     * @param i               Identifies the button that was clicked or the action taken.
     */
    private void onClose(DialogInterface dialogInterface, int i) {
        // Requiring the SessionManager to sign-out, and navigating to login (if done) when callback received:
        sessionManager.signOut(taskSignOut -> {
            if (taskSignOut.isSuccessful()) {
                Toast.makeText(requireContext(), R.string.sign_out_done, Toast.LENGTH_SHORT).show();
                navigateToLogin();
            } else
                Toast.makeText(requireContext(), R.string.sign_out_error, Toast.LENGTH_SHORT).show();
        });
    }


    /**
     * Handles the click event for the "Delete User" preference.
     * Displays a confirmation dialog to the user before starting the drop user task.
     * If the user confirms, method onDelete() is called to perform the action.
     *
     * @param preference The preference that was clicked.
     * @return True to indicate the click was handled.
     */
    private boolean onDeleteUserClick(Preference preference) {
        new AlertDialog.Builder(requireContext(), R.style.AlertDialogStyle)
                .setTitle(R.string.delete_user)
                .setIcon(R.drawable.pikachu_blue)
                .setMessage(R.string.delete_user_confirmation)
                .setPositiveButton(R.string.yes, this::onDelete)
                .setNegativeButton(R.string.no, null)
                .show();

        return true;
    }


    /**
     * Handles the dialog close event by deleting the user account and navigating to the login screen
     * upon successful drop. Displays a message to the user indicating the result of the operation.
     *
     * @param dialogInterface The interface of the dialog that triggered the event.
     * @param i               Identifies the button that was clicked or the action taken.
     */
    private void onDelete(DialogInterface dialogInterface, int i) {

        // Deleting user with SessionManager an deleting his/her Pokémon's collection in Firestore:
        sessionManager.dropUser(taskDeleteUser -> {

            if (taskDeleteUser.isSuccessful() && taskDeleteUser.getResult()) {
                Toast.makeText(requireContext(), R.string.delete_user_done, Toast.LENGTH_SHORT).show();
                navigateToLogin();
            } else
                Toast.makeText(requireContext(), R.string.delete_user_failed, Toast.LENGTH_SHORT).show();

        });
    }


    /**
     * Closes the MainActivity and navigates to the LoginActivity.
     */
    private void navigateToLogin() {
        Activity activity = requireActivity();
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(intent);
        activity.finish();
    }


    /**
     * Handles the click event for the "About" preference.
     * Displays an AlertDialog containing information about the application, such as the developer's name
     * and the current version of the app. If the version name cannot be retrieved, it displays "unknown".
     *
     * @param preference The preference that was clicked.
     * @return True to indicate that the click event was handled.
     */
    private boolean onAboutClick(Preference preference) {
        Activity activity = requireActivity();

        // Finding the version name of the app:
        String versionName;
        try {
            versionName = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = getString(R.string.unknown);
        }

        // Building the message text:
        String message = String.format("%s\n%s %s.",
                getString(R.string.developer_name),
                getString(R.string.version),
                versionName);

        // Showing the AlertDialog:
        new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
                .setTitle(R.string.about)
                .setIcon(R.drawable.pikachu_blue)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
        return true;
    }
}
