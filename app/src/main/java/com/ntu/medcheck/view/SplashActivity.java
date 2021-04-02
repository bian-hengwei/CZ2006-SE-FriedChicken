package com.ntu.medcheck.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.ntu.medcheck.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_DISPLAY_LENGTH = 1000;
        new Handler().postDelayed(() -> {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            Intent intent;
            if (fAuth.getCurrentUser() != null) {
                intent = new Intent(SplashActivity.this, HomeActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}