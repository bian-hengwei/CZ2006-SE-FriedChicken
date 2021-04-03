package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ntu.medcheck.R;

/**
 * Add medication page
 * contains textfields prompting medication entry details
 * calls schedule manager to add medication entry
 */
public class AddMedicationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        Button addMedButton = findViewById(R.id.addMed);
        addMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast addMedToast = Toast.makeText(AddMedicationActivity.this, R.string.AddMedicationSuccess, Toast.LENGTH_LONG);
                addMedToast.setGravity(Gravity.CENTER, 0,0);
                addMedToast.show();
                Intent i = new Intent(AddMedicationActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        Spinner unitList = findViewById(R.id.unitList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.dosageUnit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitList.setAdapter(adapter);
        unitList.setOnItemSelectedListener(this);

        Spinner morningList1 = findViewById(R.id.mhour);
        Spinner afternoonList1 = findViewById(R.id.ahour);
        Spinner eveningList1 = findViewById(R.id.nhour);

        Integer[] hours1 = new Integer[]{8,9,10,11};
        Integer[] hours2 = new Integer[]{12,13,14,15,16,17,18};
        Integer[] hours3 = new Integer[]{19,20,21,22,23,24};

        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,hours1);
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,hours2);
        ArrayAdapter<Integer> adapter5 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,hours3);


        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        morningList1.setAdapter(adapter1);
        morningList1.setOnItemSelectedListener(this);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        afternoonList1.setAdapter(adapter3);
        afternoonList1.setOnItemSelectedListener(this);

        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eveningList1.setAdapter(adapter5);
        eveningList1.setOnItemSelectedListener(this);

        Spinner morningList2 = findViewById(R.id.mmin);
        Spinner afternoonList2 = findViewById(R.id.amin);
        Spinner eveningList2 = findViewById(R.id.nmin);

        Integer[] mins = new Integer[]{00,15,30,45};
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,mins);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        morningList2.setAdapter(adapter2);
        morningList2.setOnItemSelectedListener(this);
        afternoonList2.setAdapter(adapter2);
        afternoonList2.setOnItemSelectedListener(this);
        eveningList2.setAdapter(adapter2);
        eveningList2.setOnItemSelectedListener(this);

        CheckBox mtimeSel = findViewById(R.id.morningTime);
        CheckBox atimeSel = findViewById(R.id.afternoonTime);
        CheckBox ntimeSel = findViewById(R.id.eveningTime);
        mtimeSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    morningList1.setVisibility(View.VISIBLE);
                    morningList2.setVisibility(View.VISIBLE);
                }
                else {
                    morningList1.setVisibility(View.INVISIBLE);
                    morningList2.setVisibility(View.INVISIBLE);
                }
            }
        });

        atimeSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    afternoonList1.setVisibility(View.VISIBLE);
                    afternoonList2.setVisibility(View.VISIBLE);
                }
                else {
                    afternoonList1.setVisibility(View.INVISIBLE);
                    afternoonList2.setVisibility(View.INVISIBLE);
                }
            }
        });

        ntimeSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    eveningList1.setVisibility(View.VISIBLE);
                    eveningList2.setVisibility(View.VISIBLE);
                }
                else {
                    eveningList1.setVisibility(View.INVISIBLE);
                    eveningList2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
