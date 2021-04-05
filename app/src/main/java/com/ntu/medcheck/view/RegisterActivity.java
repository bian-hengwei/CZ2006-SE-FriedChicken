package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.RegisterMgr;

/**
 * This page displays register page for users
 * and calls HomeActivity after successful registration
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            Log.d("ACTION BAR", "null");
        else actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("clicked", String.valueOf(item.getItemId()));
            finish();
        }
        return true;
    }

    public void register(View v) {
        RegisterMgr registerMgr = new RegisterMgr();
        registerMgr.register(this);
    }
}