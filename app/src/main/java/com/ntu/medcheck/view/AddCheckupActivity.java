package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.CheckUpMgr;
import com.ntu.medcheck.utils.SafeOnClickListener;
import com.ntu.medcheck.view.fragment.CheckupFragment;

/**
 * Add check up page
 * contains textfields prompting check up entry details
 * calls schedule manager to add check up entry
 */
public class AddCheckupActivity extends AppCompatActivity {
    public TextView clinic_name;
    public TextView checkup_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checkup);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            Log.d("ACTION BAR", "null");
        else actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton editButton = findViewById(R.id.editClinic);
        editButton.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                Intent i = new Intent(AddCheckupActivity.this, SearchClinicActivity.class);
                startActivityForResult(i, 100);
            }
        });

        Button addCheckButton = findViewById(R.id.addCheckUp);
        addCheckButton.setOnClickListener(new SafeOnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOneClick(View v) {
                if (new CheckUpMgr().addCheckUp(AddCheckupActivity.this)) {
                    Toast addCheckupToast = Toast.makeText(AddCheckupActivity.this, R.string.AddCheckupSuccess, Toast.LENGTH_LONG);
                    addCheckupToast.show();
                    finish();
                }
                else {
                    Toast.makeText(AddCheckupActivity.this, R.string.AddCheckupFailure, Toast.LENGTH_LONG);
                }
            }
        });
        findViewById(R.id.deleteCheckUp).setOnClickListener(new SafeOnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOneClick(View v) {
                finish();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("clicked", String.valueOf(item.getItemId()));
            finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK) {

            Log.d("OnActivityResult", "onActivityResult: OK");
            checkup_type = findViewById(R.id.confirmed_checkup_type); //get the textview
            String setCheckUpType = data.getStringExtra("type of checkup"); //get the string value from the intent
            checkup_type.setText(setCheckUpType); //set checkup type
            Log.d("OnActivityResult", "onActivityResult: " + setCheckUpType);
            clinic_name = findViewById(R.id.confirmed_clinic_name); //get the textview
            String setClinicName = data.getStringExtra("Clinic name set"); //get the string value from the intent
            clinic_name.setText(setClinicName);
            Log.d("OnActivityResult", "onActivityResult: " + setClinicName);
        }
    }
}
