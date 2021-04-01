package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

/**
 * This class allows user to search for different types of checkup
 * and display clinics of different types on the Singapore map
 * When user clicks on a clinic, its information will be shown below the map
 * The class calls AddCheckupActivity if user wants to add a clinic to checkup schedule
 */
public class SearchClinicActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_map);

        Spinner typeList = findViewById(R.id.typeList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.checkupList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeList.setAdapter(adapter);
        typeList.setOnItemSelectedListener(this);

        Button addToScheduleButton = findViewById(R.id.addToSchedule);
        addToScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchClinicActivity.this, AddCheckupActivity.class);
                startActivity(i);
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String types = parent.getItemAtPosition(position).toString();
        // Msg shown on the bottom of the page
        // Toast.makeText(parent.getContext(),types, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

