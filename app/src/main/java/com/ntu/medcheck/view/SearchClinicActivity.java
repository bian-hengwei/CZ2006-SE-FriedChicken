package com.ntu.medcheck.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.ScreeningCentreMgr;

/**
 * This page displays search clinic page for users
 * @author Wang Xuege
 */

public class SearchClinicActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    /**
     * This method is called when this activity is created
     * It displays the map
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_map);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            Log.d("ACTION BAR", "null");
        else actionBar.setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ScreeningCentreMgr defaultMap = null;
        defaultMap = new ScreeningCentreMgr();// creating map fragment
        fragmentTransaction.add(R.id.Container, defaultMap); //putting map fragment on the activity
        fragmentTransaction.commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("clicked", String.valueOf(item.getItemId()));
            finish();
        }
        return true;
    }
}

