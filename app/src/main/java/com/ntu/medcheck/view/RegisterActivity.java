package com.ntu.medcheck.view;

import android.os.Bundle;
import android.view.View;

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
        registerMgr.register(this);
    }
}