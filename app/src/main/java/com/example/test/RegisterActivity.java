package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View v) {
        boolean passwordValid = false;
        boolean emailValid = false;
        passwordValid = checkValid();
        emailValid = verifyEmail();
        if(passwordValid && emailValid) {
            // add to firebase
            Intent i = new Intent(this, ListActivity.class);
            startActivity(i);
        }
    }

    public boolean checkValid() {
        // get email and password and check if they are valid
        return true;
    }

    public boolean verifyEmail() {
        return true;
    }
}