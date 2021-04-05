package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.MedicationMgr;
import com.ntu.medcheck.utils.SafeOnClickListener;

public class EditMedicationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    MedicationMgr medicationMgr = MedicationMgr.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medication);

        medicationMgr.displayEditMedication(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            Log.d("ACTION BAR", "null");
        else actionBar.setDisplayHomeAsUpEnabled(true);



       /* Spinner editUnitList = findViewById(R.id.editUnitList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dosageUnit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editUnitList.setAdapter(adapter);
        editUnitList.setOnItemSelectedListener(this);

        Spinner editMorningList1 = findViewById(R.id.editmhour);
        Spinner editAfternoonList1 = findViewById(R.id.editahour);
        Spinner editEveningList1 = findViewById(R.id.editnhour);

        Integer[] hours1 = new Integer[]{8,9,10,11};
        Integer[] hours2 = new Integer[]{12,13,14,15,16,17,18};
        Integer[] hours3 = new Integer[]{19,20,21,22,23,24};

        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,hours1);
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,hours2);
        ArrayAdapter<Integer> adapter5 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,hours3);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editMorningList1.setAdapter(adapter1);
        editMorningList1.setOnItemSelectedListener(this);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editAfternoonList1.setAdapter(adapter3);
        editAfternoonList1.setOnItemSelectedListener(this);

        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEveningList1.setAdapter(adapter5);
        editEveningList1.setOnItemSelectedListener(this);

        Spinner editMorningList2 = findViewById(R.id.mmin);
        Spinner editAfternoonList2 = findViewById(R.id.amin);
        Spinner editEveningList2 = findViewById(R.id.nmin);

        Integer[] mins = new Integer[]{00,15,30,45};
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,mins);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editMorningList2.setAdapter(adapter2);
        editMorningList2.setOnItemSelectedListener(this);
        editAfternoonList2.setAdapter(adapter2);
        editAfternoonList2.setOnItemSelectedListener(this);
        editEveningList2.setAdapter(adapter2);
        editEveningList2.setOnItemSelectedListener(this);

        CheckBox editmtimeSel = findViewById(R.id.editmorningTime);
        CheckBox editatimeSel = findViewById(R.id.editafternoonTime);
        CheckBox editntimeSel = findViewById(R.id.editeveningTime);

        editmtimeSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editMorningList1.setVisibility(View.VISIBLE);
                    editMorningList2.setVisibility(View.VISIBLE);
                }
                else {
                    editMorningList1.setVisibility(View.INVISIBLE);
                    editMorningList1.setVisibility(View.INVISIBLE);
                }
            }
        });

        editatimeSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editAfternoonList1.setVisibility(View.VISIBLE);
                    editAfternoonList2.setVisibility(View.VISIBLE);
                }
                else {
                    editAfternoonList1.setVisibility(View.INVISIBLE);
                    editAfternoonList2.setVisibility(View.INVISIBLE);
                }
            }
        });

        editntimeSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editEveningList1.setVisibility(View.VISIBLE);
                    editEveningList2.setVisibility(View.VISIBLE);
                }
                else {
                    editEveningList1.setVisibility(View.INVISIBLE);
                    editEveningList2.setVisibility(View.INVISIBLE);
                }
            }
        });*/
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
}
