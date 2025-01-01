package com.iesaguadulce.lopez_salazar_mario_pmdm03.authentication;

import android.content.Context;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.R;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.EdittextPasswordBinding;

/**
 * Utility class for requesting a password from the user through a dialog.
 * Provides a method to display a password input dialog (edittext_password layout)
 * with an eye button to toggle the visibility of the password.
 * The result is returned through an OnCompleteListener.
 *
 * @author Mario López Salazar
 */
public class RequirePassword {


    /**
     * Displays a password input dialog and passes the result to an OnCompleteListener.
     *
     * @param activity              Activity used for displaying the dialog.
     * @param onPasswordGotListener Callback to handle the result (result containing the password or null).
     */
    public static void show(AppCompatActivity activity, OnCompleteListener<String> onPasswordGotListener) {
        EdittextPasswordBinding binding = EdittextPasswordBinding.inflate(activity.getLayoutInflater());

        // Configure the eye button and the textView mask for password visibility:
        binding.eyeButton.setOnClickListener(v -> {

            // Getting the current configuration:
            boolean isPasswordVisible = (binding.passwordInput.getInputType()
                    & (InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD - InputType.TYPE_TEXT_VARIATION_PASSWORD)) != 0;

            if (!isPasswordVisible) {
                // Currently hidden --> turning visible:
                binding.passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.eyeButton.setImageResource(R.drawable.eye_closed);
            } else {
                // Currently visible --> turning hidden:
                binding.passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.eyeButton.setImageResource(R.drawable.eye_opened);
            }

            // Moving on the cursor up to the end of the textView:
            binding.passwordInput.setSelection(binding.passwordInput.getText().length());
        });

        // Building and showing the dialog:
        new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
                .setTitle(R.string.password)
                .setIcon(R.drawable.pikachu_blue)
                .setMessage(R.string.password_requirement)
                .setView(binding.getRoot())

                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    String password = binding.passwordInput.getText().toString().trim();
                    if(password.isEmpty())
                        onPasswordGotListener.onComplete(Tasks.forResult(null));
                    else
                        onPasswordGotListener.onComplete(Tasks.forResult(password));
                })

                .setNegativeButton(R.string.cancel, (dialogInterface, i) ->
                        onPasswordGotListener.onComplete(Tasks.forResult(null)))

                // Hidding the keyboard when the dialog isdismissed:
                .setOnDismissListener(dialogInterface -> {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                        imm.hideSoftInputFromWindow(binding.passwordInput.getWindowToken(), 0);
                })
                .show();
    }
}
