package com.ntu.medcheck.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import com.ntu.medcheck.controller.MyNotificationPublisher;
import com.ntu.medcheck.utils.SafeOnClickListener;

/**
 * Edit check up page
 * Use the same page as AddCheckupActivity
 * Contains textfields prompting check up entry details
 * Calls CheckupMgr to edit check up entry and save edited checkup
 * @author Yin Jiarui
 */
public class EditCheckupActivity extends AppCompatActivity {

    public TextView clinic_name;
    public TextView checkup_type;
    private boolean save = false;
    private boolean delete = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
                Intent i = new Intent(EditCheckupActivity.this, SearchClinicActivity.class);
                startActivityForResult(i, 101);
            }
        });

        Button addCheckButton = findViewById(R.id.addCheckUp);
        addCheckButton.setOnClickListener(new SafeOnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOneClick(View v) {
                save = true;
                Toast.makeText(EditCheckupActivity.this, R.string.EditCheckupSuccess, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button deleteCheckButton = findViewById(R.id.deleteCheckUp);
        deleteCheckButton.setOnClickListener(new SafeOnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOneClick(View v) {
                delete = true;
                Toast.makeText(EditCheckupActivity.this, R.string.DeleteCheckupSuccess, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        new CheckUpMgr().display(this, getIntent());
    }

    /**
     * Finish the current page and return to the homepage if user clicks on back button
     * @param item passes the MenuItem selected
     * @return return a boolean to indicate the menu item is handled successfully
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (delete) return;
        if (!save) {
            new CheckUpMgr().display(this, getIntent());
        }
        if (new CheckUpMgr().addCheckUp(EditCheckupActivity.this)) {
            finish();
        }
        else {
            Toast.makeText(EditCheckupActivity.this, R.string.EditCheckupFailure, Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK) {

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
