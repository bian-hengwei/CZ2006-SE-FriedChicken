package com.ntu.medcheck.view;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.UserProfileMgr;

/**
 * The class allows users to reset password if they forget the current password
 */

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button

        Button forgetPasswordBtn = findViewById(R.id.forgetPasswordResetPasswordButton);
        forgetPasswordBtn.setOnClickListener(v1 -> {
            UserProfileMgr userProfileMgr = new UserProfileMgr();
            Context context = getApplicationContext();
            userProfileMgr.resetPassword(this, context);
        });
    }
}