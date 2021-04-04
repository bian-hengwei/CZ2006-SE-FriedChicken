package com.ntu.medcheck.controller;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.MedicationEntry;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.model.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleMgr {

    Schedule schedule;
    FirebaseDatabase fDatabase;
    DatabaseReference uRef;


    public ScheduleMgr() {
        schedule = Schedule.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        DatabaseReference sRef = fDatabase.getReference("Schedules");
        uRef = sRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }


    public void initialize() {
        uRef.keepSynced(true);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setSchedule(dataSnapshot);


                /*///////////vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv//////////*/
                setMedicationSchedule(dataSnapshot);
                /*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/


                Log.d("loading", "Loading data");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Err", "loadPost:onCancelled", databaseError.toException());
            }
        };
        uRef.addValueEventListener(postListener);
    }

    private void setSchedule(DataSnapshot dataSnapshot) {
        if (dataSnapshot.child("checkup").exists()) {
            Map<String, ArrayList<CheckUpEntry>> checkup = new HashMap<>();
            for (DataSnapshot yearMonth : dataSnapshot.child("checkup").getChildren()) {
                checkup.put(yearMonth.getKey(), new ArrayList<>());
                for (DataSnapshot entry : yearMonth.getChildren()) {
                    checkup.get(yearMonth.getKey()).add(toEntry(entry));
                }
            }
            schedule.setCheckup(checkup);
            Log.d("tostring!", schedule.toString());
        }
        else {
            schedule.setCheckup(new HashMap<>());
        }
    }

    private CheckUpEntry toEntry(DataSnapshot entry) {
        CheckUpEntry checkUpEntry = new CheckUpEntry();
        checkUpEntry.setClinic((String) entry.child("clinic").getValue());
        checkUpEntry.setComment((String) entry.child("comment").getValue());
        checkUpEntry.setName((String) entry.child("name").getValue());
        checkUpEntry.setTitle((String) entry.child("title").getValue());
        checkUpEntry.setType((String) entry.child("type").getValue());
        checkUpEntry.setTime(entry.child("time").getValue(Time.class));
        return checkUpEntry;
    }



/*///////////vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv//////////*/
    private void setMedicationSchedule(DataSnapshot dataSnapshot) {
        if(dataSnapshot.child("medication").exists()) {
            ArrayList<MedicationEntry> medication = new ArrayList<>();
            for(DataSnapshot entry : dataSnapshot.child("medication").getChildren()) {
                medication.add(toMedicationEntry(entry));
            }
        }
    }

    private MedicationEntry toMedicationEntry(DataSnapshot entry) {
        MedicationEntry medicationEntry = new MedicationEntry();
        medicationEntry.setDosage(Integer.parseInt((String)entry.child("dosage").getValue()));
        medicationEntry.setUnit((String) entry.child("unit").getValue());
        medicationEntry.setComment((String) entry.child("comment").getValue());
        medicationEntry.setName((String) entry.child("name").getValue());
        medicationEntry.setType((String) entry.child("type").getValue());

        for(DataSnapshot timeEntry : )

        medicationEntry.setTime();

    }
}
/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/