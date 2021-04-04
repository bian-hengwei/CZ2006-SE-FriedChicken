package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.ntu.medcheck.R;
import com.ntu.medcheck.view.HomeActivity;

public class LoginMgr {

    /**
     * use firebase to verify if login is successful
     * @param aca
     * @return true if successful
     */
    public void verifyLogin(AppCompatActivity aca) {

        EditText emailText = aca.findViewById(R.id.loginEmailInput);
        EditText passwordText = aca.findViewById(R.id.loginPasswordInput);

        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString();

        boolean valid = checkInputValid(email, password, aca);

        if (!valid) {
            return;
        }

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //check if account is verified
                FirebaseUser user = fAuth.getCurrentUser();
                if (user.isEmailVerified()) {
                    Intent i = new Intent(aca, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    aca.startActivity(i);
                    aca.finish();
                    Toast.makeText(aca, R.string.loginSuccess, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(aca, R.string.verifyEmail, Toast.LENGTH_SHORT).show();
                }
            } else {
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
