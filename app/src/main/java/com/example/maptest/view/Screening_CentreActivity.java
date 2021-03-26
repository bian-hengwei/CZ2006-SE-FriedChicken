package com.example.maptest.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.maptest.R;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Screening_CentreActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening__centre);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        MapFragment defaultMap = null;
        defaultMap = new MapFragment();
        fragmentTransaction.add(R.id.Container, defaultMap);
        fragmentTransaction.commit();
    }
}