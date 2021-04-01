package com.ntu.medcheck.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                forgetPassword(v);
            }
        });
    }





    public void forgetPassword(View v) {
        UserProfileMgr userProfileMgr = new UserProfileMgr();
        String email = getEmail();
        Context context = getApplicationContext();
        userProfileMgr.resetPassword(email, context);
    }

    public String getEmail() {
        EditText email = findViewById(R.id.forgetPasswordEmailInput);
        String emailString = email.getText().toString().trim();
        return emailString;
    }

}