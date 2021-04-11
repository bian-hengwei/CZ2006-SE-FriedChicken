package com.ntu.medcheck.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

public class EActivity extends AppCompatActivity {

    /**
     * Open easter egg page if user clicks on the title of application
     * @param savedInstanceState to save the state of the add checkup activity if user has made
     * any change on the page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);
    }

    /**
     * If home activity is already started, finish the easter egg page
     */
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}