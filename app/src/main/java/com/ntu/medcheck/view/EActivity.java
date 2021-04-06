package com.ntu.medcheck.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        Log.d("Easter egg", "onStop: ");
        finish();
    }
}