package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntu.medcheck.R;
import com.ntu.medcheck.view.fragment.ScheduleFragment;

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

        FloatingActionButton addButton1 = findViewById(R.id.editType);
        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddCheckupActivity.this, SearchClinicActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton addButton2 = findViewById(R.id.editClinic);
        addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddCheckupActivity.this, SearchClinicActivity.class);
                startActivity(i);
            }
        });

        Button addCheckButton = findViewById(R.id.addCheckUp);
        addCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast addCheckToast = Toast.makeText(AddCheckupActivity.this, "Check Up Added Successfully", Toast.LENGTH_LONG);
                addCheckToast.setGravity(Gravity.CENTER,0,0);
                addCheckToast.show();
                Intent i = new Intent(AddCheckupActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

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

    public void returnToScheduleFrag(View v) {
        Intent i = new Intent(this, ScheduleFragment.class);
        startActivity(i);
    }

}
