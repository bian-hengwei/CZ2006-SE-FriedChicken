package com.ntu.medcheck.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ntu.medcheck.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button
    }

    public void resetPassword(View v) {
        // onclick
        // UserProfileMgr.reset()
    }

}