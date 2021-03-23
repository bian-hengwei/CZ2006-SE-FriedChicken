package com.ntu.medcheck.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ntu.medcheck.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View v) {
        if(true) { // successfully registered
            // add to firebase
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
    }

}