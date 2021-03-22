package com.example.maptest.controller;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.maptest.R;
import com.example.maptest.view.HomepageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.maptest.view.LoginActivity;

public class LoginMgr {
    private Button loginbutton;
    private EditText mEmail, mPassword;
    FirebaseAuth fAuth;
    //function to login
    /*public void login(View view) {
        fAuth = FirebaseAuth.getInstance(); // get instance of firebase
        loginbutton = findViewById(R.id.Loginbtn);
        mEmail = findViewById(R.id.Email_login);
        mPassword = findViewById(R.id.Passwordlogin);
        String email = mEmail.getText().toString().trim(); //get email from EditText
        String password = mPassword.getText().toString().trim(); //get password from EditText

        //check if email is empty
        if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is required.");
            return;
        }
        //check if password is empty
        if(TextUtils.isEmpty(password)){
            mPassword.setError("Password is required.");
            return;
        }

        //authenticate the user
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //check if account is verified
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                        Toast.makeText(getApplicationContext(),"Logged in Successfully",Toast.LENGTH_SHORT).show();
                    }else{
                        //user.sendEmailVerification(); //email already sent during registration
                        Toast.makeText(getApplicationContext(), "Check ur email to verify your account", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid email/password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/
}
