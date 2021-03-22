package com.example.test.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.test.R;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void changeLanguage(View v){
    //take input of language
    }

    public void changeFontSize(View v){
    //take input of font size
    }

    //Turn notification on or off
    public boolean adjustNotification(){
        return true;
    }

    public void changeVolume(View v){
    //take input of changed volume
    }

    //Turn vibration on or off
    public boolean changeVibration(){return true;}
}
