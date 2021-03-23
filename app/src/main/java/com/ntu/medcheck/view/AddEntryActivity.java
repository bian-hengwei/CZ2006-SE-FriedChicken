package com.ntu.medcheck.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

public class AddEntryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_schedule);
    }

    public void addToSchedule(View v){
    //add medication and checkup to schedule
    }
}
