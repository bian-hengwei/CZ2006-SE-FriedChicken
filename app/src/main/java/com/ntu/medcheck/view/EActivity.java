package com.ntu.medcheck.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

public class EActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}