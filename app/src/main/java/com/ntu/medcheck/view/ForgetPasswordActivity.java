package com.ntu.medcheck.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.UserProfileMgr;

/**
 * The class allows users enter their email and reset password if they forget the current password
 * @author Wang Xuege
 */

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            Log.d("ACTION BAR", "null");
        else actionBar.setDisplayHomeAsUpEnabled(true);

        Button forgetPasswordBtn = findViewById(R.id.forgetPasswordResetPasswordButton);
        forgetPasswordBtn.setOnClickListener(v1 -> {
            UserProfileMgr userProfileMgr = new UserProfileMgr();
            Context context = getApplicationContext();
            userProfileMgr.resetPassword(this, context);
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}