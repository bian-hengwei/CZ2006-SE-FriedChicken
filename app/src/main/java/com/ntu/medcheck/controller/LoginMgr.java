package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.ntu.medcheck.R;
import com.ntu.medcheck.view.HomeActivity;

public class LoginMgr {


    FirebaseAuth fAuth;

    /**
     * use firebase to verify if login is successful
     * @param context, aca
     * @return true if successful
     */
    // takes in the information from LoginActivity and verifies with fAuth
    // try to use context or bundle??
    public void verifyLogin(Context context, AppCompatActivity aca) {

        System.out.println("hello0");

        EditText emailText = aca.findViewById(R.id.loginEmailInput);
        EditText passwordText = aca.findViewById(R.id.loginPasswordInput);

        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString();

        boolean valid = checkInputValid(email, password, context);

        System.out.println(email);
        System.out.println(password);

        if(!valid) {
            return;
        }
        fAuth = FirebaseAuth.getInstance();

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                //check if account is verified
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user.isEmailVerified()){
                    Intent i = new Intent(context, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    aca.startActivity(i);
                    aca.finish();
                    Toast.makeText(context.getApplicationContext(),"Logged in Successfully",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(context.getApplicationContext(), "Check your email to verify your account", Toast.LENGTH_LONG).show();
                }
            } else {
                try {
                    System.out.println(task.getException());
                    throw task.getException();
                } catch(FirebaseAuthInvalidUserException e) {
                    Toast.makeText(context, "Login failed, invalid user", Toast.LENGTH_LONG).show();
                }
                catch(FirebaseAuthInvalidCredentialsException e) {
                    System.out.println("1");
                    Toast.makeText(context, "Login failed, wrong password", Toast.LENGTH_LONG).show();
                }
                catch(Exception e) {
                    System.out.println("Login failed unknown Error");
                }
            }
        });
    }

    public boolean checkInputValid(String email, String password, Context context) {
        if(email.isEmpty()) {
            Toast.makeText(context, "Login failed, email is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(password.isEmpty()) {
            Toast.makeText(context, "Login failed, password is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
