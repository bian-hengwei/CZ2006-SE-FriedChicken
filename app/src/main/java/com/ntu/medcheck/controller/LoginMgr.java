package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.ntu.medcheck.R;
import com.ntu.medcheck.view.HomeActivity;

/**
 * Deals with all logic from LoginActivity.
 */
public class LoginMgr {

    /**
     * Log tag for debugging.
     */
    private static final String TAG = "LoginManager";

    /**
     * Try to verify login using firebase.
     * Gets input from the aca and verifies with firebase.
     * @param aca Login Activity.
     */
    public void verifyLogin(AppCompatActivity aca) {

        Log.d(TAG, "verifyLogin: start verifying");
        EditText emailText = aca.findViewById(R.id.loginEmailInput);
        EditText passwordText = aca.findViewById(R.id.loginPasswordInput);
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString();

        // checks if inputs are valid or not
        boolean valid = checkInputValid(email, password, aca);
        if (!valid) return;

        // verifies with firebase
        Log.d(TAG, "verifyLogin: verifies with firebase");
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //check if account is verified
                Log.d(TAG, "verifyLogin: authentication is valid");
                FirebaseUser user = fAuth.getCurrentUser();
                if (user.isEmailVerified()) {
                    Log.d(TAG, "verifyLogin: email is verified");
                    Log.d(TAG, "verifyLogin: logged in successfully");
                    Intent i = new Intent(aca, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    aca.startActivity(i);
                    aca.finish();
                    Toast.makeText(aca, R.string.loginSuccess, Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "verifyLogin: email not verified");
                    Toast.makeText(aca, R.string.verifyEmail, Toast.LENGTH_SHORT).show();
                }
            } 
            else {
                Log.d(TAG, "verifyLogin: login failed");
                try {
                    System.out.println(task.getException());
                    throw task.getException();
                } catch (FirebaseAuthInvalidUserException e) {
                    Toast.makeText(aca, R.string.loginFailUser, Toast.LENGTH_SHORT).show();
                }
                catch(FirebaseAuthInvalidCredentialsException e) {
                    Toast.makeText(aca, R.string.loginFailPw, Toast.LENGTH_SHORT).show();
                }
                catch(Exception e) {
                    Toast.makeText(aca, R.string.loginFailUnknown, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Checks if input is valid.
     * @param email Email input.
     * @param password Password input.
     * @param context Context for toast.
     * @return If input is valid.
     */
    public boolean checkInputValid(String email, String password, Context context) {
        if (email.isEmpty()) {
            Toast.makeText(context, R.string.emptyEmail, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (password.isEmpty()) {
            Toast.makeText(context, R.string.emptyPw, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
