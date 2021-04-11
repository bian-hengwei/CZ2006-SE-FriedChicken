package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.ntu.medcheck.R;
import com.ntu.medcheck.utils.SafeOnClickListener;

/**
 * This page displays the splash page for users when user opens the application
 * This page displays the logo of the application
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    /**
     * This method is called to display the splash when app is opened
     * @param savedInstanceState
     */
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

        findViewById(R.id.splashIcon).setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                startActivity(new Intent(SplashActivity.this, EActivity.class));
            }
        });
    }
}