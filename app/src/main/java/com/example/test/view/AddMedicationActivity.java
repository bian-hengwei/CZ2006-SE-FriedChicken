package com.example.test.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.test.R;

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
