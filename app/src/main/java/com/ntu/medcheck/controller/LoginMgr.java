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
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.User;
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
                    //FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
                    //fDatabase.getReference("Users").keepSynced(true);
                    //fDatabase.getReference("Schedules").keepSynced(true);
                    Intent i = new Intent(aca, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    aca.startActivity(i);
                    aca.finish();
                    Toast.makeText(aca,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(aca, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                }
            } else {
                try {
                    System.out.println(task.getException());
                    throw task.getException();
                } catch (FirebaseAuthInvalidUserException e) {
                    Toast.makeText(aca, "Login failed, invalid user", Toast.LENGTH_LONG).show();
                }
                catch(FirebaseAuthInvalidCredentialsException e) {
                    Toast.makeText(aca, "Login failed, wrong password", Toast.LENGTH_LONG).show();
                }
                catch(Exception e) {
                    System.out.println("Login failed unknown Error");
                }
            }
        });
    }

    public boolean checkInputValid(String email, String password, Context context) {
        if (email.isEmpty()) {
            Toast.makeText(context, "Login failed, email is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (password.isEmpty()) {
            Toast.makeText(context, "Login failed, password is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
