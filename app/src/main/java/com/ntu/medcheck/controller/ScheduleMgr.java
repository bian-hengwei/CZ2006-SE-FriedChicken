package com.ntu.medcheck.controller;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ntu.medcheck.model.Schedule;

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
                schedule = dataSnapshot.getValue(Schedule.class);
                Log.d("loading", "Loading data");
                Log.d("schedule", "abcde");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Err", "loadPost:onCancelled", databaseError.toException());
            }
        };

        uRef.addValueEventListener(postListener);

    }

}
