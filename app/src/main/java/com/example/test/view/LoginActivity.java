package com.example.test.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.test.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v) {
        // verify
        String email = null;
        String password = null;
        boolean success = false; // later set to false

        // read in email and password from ui

        success = verifyPassword(email, password);
        if(success) {
            // go to home activity
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void register (View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void forgetPassword(View v) {
        Intent i = new Intent(this, ForgetPasswordActivity.class);
        startActivity(i);
    }

    public boolean verifyPassword(String email, String password) {
        return true;
    }
}