package com.ntu.medcheck.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

/**
 * The class displays setting page for users
 * User can change various settings on the page
 */
public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
    }

    public void save(View v) {
        // once click save button
        // save all settings on screen with setting mgr
    }

    /*
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
     */
}
