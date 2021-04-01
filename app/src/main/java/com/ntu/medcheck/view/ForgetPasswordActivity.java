package com.ntu.medcheck.view;

import android.os.Bundle;
import android.view.View;

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
    }

    public void resetPassword(View v) {
        UserProfileMgr userProfileMgr = new UserProfileMgr();
        userProfileMgr.resetPassword(getApplicationContext());
    }

}