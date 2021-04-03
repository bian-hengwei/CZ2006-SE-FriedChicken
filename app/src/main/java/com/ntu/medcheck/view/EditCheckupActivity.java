package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntu.medcheck.R;

public class EditCheckupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_checkup);

        FloatingActionButton editCheckupType = findViewById(R.id.editEditType);
        editCheckupType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditCheckupActivity.this, SearchClinicActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton editClinic = findViewById(R.id.editEditClinic);
        editClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditCheckupActivity.this, SearchClinicActivity.class);
                startActivity(i);
            }
        });
    }
}
