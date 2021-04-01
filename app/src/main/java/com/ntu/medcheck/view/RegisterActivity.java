package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.RegisterMgr;

/**
 * This page displays register page for users
 * and calls HomeActivity after successful registration
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View v) {
        RegisterMgr registerMgr = new RegisterMgr();
        registerMgr.register(getName(), getEmail(), getPassword(), getRePassword(), getApplicationContext());
    }

    public String getName() {
        EditText nameInput = findViewById(R.id.registerNameInput);
        String name = nameInput.getText().toString().trim();
        return name;
    }

    public String getEmail() {
        EditText emailInput = findViewById(R.id.registerEmailInput);
        String email = emailInput.getText().toString().trim();
        return email;
    }

    public String getPassword() {
        EditText passwordInput = findViewById(R.id.registerPasswordInput);
        String password = passwordInput.getText().toString().trim();
        return password;
    }

    public String getRePassword() {
        EditText rePasswordInput = (EditText)findViewById(R.id.registerRePasswordInput);
        String rePassword = rePasswordInput.getText().toString().trim();
        return rePassword;
    }

}