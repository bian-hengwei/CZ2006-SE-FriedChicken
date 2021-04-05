package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.LoginMgr;
import com.ntu.medcheck.utils.SafeOnClickListener;

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
        Log.d("LOGIN ONCREATE", "onCreate: ");
        findViewById(R.id.button).setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                LoginMgr loginMgr = new LoginMgr();
                loginMgr.verifyLogin(LoginActivity.this);
            }
        });
    }

    /**
     *
     * @param v
     */
    public void register (View v) {
        // onClick of register button
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     *
     * @param v
     */
    public void forgetPassword(View v) {
        // onClick of forget password function
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }
}