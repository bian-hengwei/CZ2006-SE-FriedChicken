package com.ntu.medcheck.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.MedicationMgr;
import com.ntu.medcheck.utils.SafeOnClickListener;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Add medication page
 * contains textfields prompting medication entry details
 * calls MedicationMgr to add medication entry and save results
 * @author Wang Xuege
 */
public class EditMedicationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MedicationMgr medicationMgr = MedicationMgr.getInstance();
    private boolean save = false;
    private boolean delete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            Log.d("ACTION BAR", "null");
        else actionBar.setDisplayHomeAsUpEnabled(true);


        ArrayList<String> index = medicationMgr.displayEditMedication(this);

        AppCompatActivity aca = this;

        Button addTime = findViewById(R.id.addMedTime);
        addTime.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                int i = Integer.parseInt(index.get(index.size() - 1)) + 1;
                index.add(Integer.toString(i));
                medicationMgr.dynamicAddTime(aca, index);
                if(i > 3){
                    Toast.makeText(aca, R.string.newMedTiming, Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button deleteTime = findViewById(R.id.deleteMedTime);
        deleteTime.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                if(index.size() == 1) {
                    Toast.makeText(aca, R.string.atLeastOneTime, Toast.LENGTH_SHORT).show();
                }
                else{
                    index.remove(index.size() - 1);
                    medicationMgr.dynamicAddTime(aca, index);
                    Toast.makeText(aca, R.string.removeMedTiming, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button addCheckButton = findViewById(R.id.addMed);
        addCheckButton.setOnClickListener(new SafeOnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOneClick(View v) {
                if (medicationMgr.checkStatus(aca)) {
                    save = true;
                    Toast.makeText(aca, R.string.EditMedicationSuccess, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        findViewById(R.id.deleteMed).setOnClickListener(new SafeOnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOneClick(View v) {
                delete = true;
                Toast.makeText(aca, R.string.DeleteMedicationSuccess, Toast.LENGTH_SHORT).show();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            medicationMgr.destroy(this, save, delete);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
