package com.ntu.medcheck.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

public class ReminderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
    }

    public void remindMedication(View v){
    //reminder for taking medication
    }

    public void remindCheckup(View v){
    //reminder for coming checkup
    }
}
