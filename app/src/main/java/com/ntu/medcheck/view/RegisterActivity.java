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
        boolean success = false;
        RegisterMgr registerMgr = new RegisterMgr();
        success = registerMgr.register(getName(), getEmail(), getPassword(), getRePassword());

        if(success) { // successfully registered
            // add to firebase
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
    }

    public String getName() {
        EditText nameInput = (EditText)findViewById(R.id.registerNameInput);
        String name = nameInput.getText().toString();
        return name;
    }

    public String getEmail() {
        EditText emailInput = (EditText)findViewById(R.id.registerEmailInput);
        String email = emailInput.getText().toString();
        return email;
    }

    public String getPassword() {
        EditText passwordInput = (EditText)findViewById(R.id.registerPasswordInput);
        String password = passwordInput.getText().toString();
        return password;
    }

    public String getRePassword() {
        EditText rePasswordInput = (EditText)findViewById(R.id.registerRePasswordInput);
        String rePassword = rePasswordInput.getText().toString();
        return rePassword;
    }

}