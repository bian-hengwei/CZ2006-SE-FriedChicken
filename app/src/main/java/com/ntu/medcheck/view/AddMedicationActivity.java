package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntu.medcheck.R;

/**
 * Add medication page
 * contains textfields prompting medication entry details
 * calls schedule manager to add medication entry
 */
public class AddMedicationActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
    }

    public void inputMedication(View v){
    //take input of medication name
    }

    public void inputDosage(View v){
    //take input of dosage
    }

    public void inputTime(View v){
    //take input of time
    }

    public void repeat(View v){
    //take input of repeat time
    }

    public void inputComment(View v){
    //take comment input
    }

    public void uploadPic(View v){
    //take uploaded pic
    }
}
