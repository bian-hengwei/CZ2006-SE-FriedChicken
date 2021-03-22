package com.example.maptest.view;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import com.example.maptest.R;

public class Screening_CentreActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening__centre);

        //dropdown selection box
        /*Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get selection
                choice = parent.getItemAtPosition(position).toString();
            }
            @Override
            //put nothing here
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mapFragment_default defaultMap = new mapFragment_default();
        fragmentTransaction.add(R.id.Container, defaultMap);
        fragmentTransaction.commit();



    }

    public void replaceFragment_CHAS(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //mapFragment_default defaultMap = new mapFragment_default();
        //mapFragment_breast breastMap = new mapFragment_breast();
        //mapFragment_cervical cervicalMap = new mapFragment_cervical();
        mapFragment_chas chasMap = new mapFragment_chas();
        fragmentTransaction.replace(R.id.Container, chasMap);
        fragmentTransaction.commit();

    }

    public void replaceFragment_breast(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //mapFragment_default defaultMap = new mapFragment_default();
        mapFragment_breast breastMap = new mapFragment_breast();
        //mapFragment_cervical cervicalMap = new mapFragment_cervical();
        fragmentTransaction.replace(R.id.Container, breastMap);
        fragmentTransaction.commit();

    }

    public void replaceFragment_cervical(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //mapFragment_default defaultMap = new mapFragment_default();
        //mapFragment_breast breastMap = new mapFragment_breast();
        mapFragment_cervical cervicalMap = new mapFragment_cervical();
        //mapFragment_chas chasMap = new mapFragment_chas();
        fragmentTransaction.replace(R.id.Container, cervicalMap);
        fragmentTransaction.commit();

    }



}