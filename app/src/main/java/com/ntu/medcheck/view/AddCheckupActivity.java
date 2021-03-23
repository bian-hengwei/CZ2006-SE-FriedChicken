package com.ntu.medcheck.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

/**
 * Add check up page
 * contains textfields prompting check up entry details
 * calls schedule manager to add check up entry
 */
public class AddCheckupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checkup);
    }

    public void changeCheckupType(View v){
    //take input of type of checkup
    }

    public void changeClinic(View v){
    //take new clinic if user wants to change
    }

    public void chooseTime(View v){
    //take date and time of scheduled checkup
    }

    public void inputComment(View v){
    //take inputted comments
    }
}
