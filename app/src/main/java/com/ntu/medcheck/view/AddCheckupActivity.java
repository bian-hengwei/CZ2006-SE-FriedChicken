package com.ntu.medcheck.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntu.medcheck.R;
import com.ntu.medcheck.utils.SafeOnClickListener;
import com.ntu.medcheck.view.fragment.CheckupFragment;

/**
 * Add check up page
 * contains textfields prompting check up entry details
 * calls schedule manager to add check up entry
 */
public class AddCheckupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checkup);

        FloatingActionButton addButton1 = findViewById(R.id.editType);
        addButton1.setOnClickListener(new SafeOnClickListener(){
            @Override
            public void onOneClick(View v) {
                Intent i = new Intent(AddCheckupActivity.this, SearchClinicActivity.class);
                startActivity(i);
            }
        })
        ;

        FloatingActionButton addButton2 = findViewById(R.id.editClinic);
        addButton2.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                Intent i = new Intent(AddCheckupActivity.this, SearchClinicActivity.class);
                startActivity(i);
            }
        });

        Button addCheckButton = findViewById(R.id.addCheckUp);
        addCheckButton.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                Toast addCheckupToast = Toast.makeText(AddCheckupActivity.this, R.string.AddCheckupSuccess, Toast.LENGTH_LONG);
                addCheckupToast.setGravity(Gravity.CENTER,0,0);
                addCheckupToast.show();
                Intent i = new Intent(AddCheckupActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

    }

    public void returnToScheduleFrag(View v) {
        // TODO: can go back to fragment?
        Intent i = new Intent(this, CheckupFragment.class);
        startActivity(i);
    }

}
