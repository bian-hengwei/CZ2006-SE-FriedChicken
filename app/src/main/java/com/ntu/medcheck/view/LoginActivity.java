package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.LoginMgr;

/**
 * This class displays login page for users
 * and calls the HomeActivity when successfully logged in
 * The class also calls RegisterActivity when user clicks on register button
 * It will call the forgetPassword if user clicks on forget password button
 */
public class LoginActivity extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     *
     * @param v
     */
    public void login(View v) {
        // verify
        String emailString = getEmail();
        String passwordString = getPassword();

        boolean success = false; // later set to false

        // use verifyLogin to check login
        LoginMgr loginMgr = new LoginMgr();
        loginMgr.verifyLogin(emailString, passwordString, getApplicationContext());
        /*
        if(success) {
            // go to home activity
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }*/
    }

    public String getEmail() {
        EditText email = findViewById(R.id.loginEmailInput);
        String emailString = email.getText().toString().trim();
        System.out.println("!!!!!!!!!!!!!!!getemail!!!!!!!!!!!!!!");
        System.out.println(emailString);
        return emailString;
    }

    public String getPassword() {
        EditText password = (EditText)findViewById(R.id.loginPasswordInput);
        String passwordString = password.getText().toString();
        System.out.println("!!!!!!!!!!!!!!!getPassword!!!!!!!!!!!!!!");
        System.out.println(passwordString);
        return passwordString;
    }

    // get user input data
    public String getMainEmailInput() {
        EditText emailInput = (EditText)findViewById(R.id.loginEmailInput);
        String email = emailInput.getText().toString();
        return email;
    }

    public String getMainPasswordInput() {
        EditText passwordInput = (EditText)findViewById(R.id.loginPasswordInput);
        String password = passwordInput.getText().toString();
        return password;
    }


    /**
     *
     * @param v
     */
    public void register (View v) {
        // onClick of register button
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    /**
     *
     * @param v
     */
    public void forgetPassword(View v) {
        // onClick of forget password function
        Intent i = new Intent(this, ForgetPasswordActivity.class);
        startActivity(i);
    }
}