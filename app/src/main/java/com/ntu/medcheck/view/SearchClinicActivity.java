package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

/**
 * This class allows user to search for different types of checkup
 * and display clinics of different types on the Singapore map
 * When user clicks on a clinic, its information will be shown below the map
 * The class calls AddCheckupActivity if user wants to add a clinic to checkup schedule
 */
public class SearchClinicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_map);
    }

    public void enterCheckupType(View v){
    //choose a checkup type
    }

    public void displayMap(View v){
    //display Singapore map
    }

    public void displayClinicInfo(View v){
    //display the chosen clinic's info
    }

    //
    public void displayClinic(View v){
        //display clinics on map
    }

    public void addToSchedule(View v){
        //add the clinic to a new checkup schedule
        Intent i = new Intent(this, AddCheckupActivity.class);
        startActivity(i);
    }


}

