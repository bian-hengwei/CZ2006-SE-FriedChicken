package com.ntu.medcheck.controller;

import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.R;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class CalendarMgr implements OnNavigationButtonClickedListener {

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
    DatabaseReference sRef = fDatabase.getReference("Schedules");
    DatabaseReference scheduleUserRef = sRef.child(fAuth.getCurrentUser().getUid());
    DatabaseReference cuRef = scheduleUserRef.child("checkup");

    public CalendarMgr() {
        CheckUpMgr checkUpMgr = new CheckUpMgr();
        checkUpMgr.init();
        checkUpMgr.save();
    }

    public Map<Integer, Object>[] getNewMap(Calendar newMonth) {
        Map<Integer, Object>[] maps = new HashMap[1];
        return maps;
    }

    public void getMonth(int year, int month, View view) {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        String y = Integer.toString(year);
        String m = String.format("%02d", month);
        Log.d("test", y+m);
        cuRef.child(y+m).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                DataSnapshot snapshot = task.getResult();
                Log.d("firebase", String.valueOf(snapshot.getValue()));
                for (DataSnapshot child : snapshot.getChildren()) {
                    map.put(Integer.parseInt((String) child.child("time").child("day").getValue()), "absent");
                    Log.d("test", (String) child.child("time").child("day").getValue());
                }
            }
            CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);
            customCalendar.setDate(Calendar.getInstance(), map);
        });
    }


    public void setListeners(View view) {
        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);
    }


    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        String y = Integer.toString(newMonth.get(Calendar.YEAR));
        String m = String.format("%02d", newMonth.get(Calendar.MONTH)+1);
        Log.d("test", y+m);
        final Semaphore semaphore = new Semaphore(0);
        final Map<Integer, Object>[] arr = new Map[2];
        arr[0] = new HashMap<>();
        cuRef.child(y+m).get().addOnCompleteListener(task -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                DataSnapshot snapshot = task.getResult();
                Log.d("firebase", String.valueOf(snapshot.getValue()));
                for (DataSnapshot child : snapshot.getChildren()) {
                    arr[0].put(Integer.parseInt((String) child.child("time").child("day").getValue()), "absent");
                    Log.d("test", (String) child.child("time").child("day").getValue());
                }
            }
            semaphore.release();
        });
        try {
            semaphore.acquire(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("obbc", "onNavigationButtonClicked: ");
        return arr;
    }
}
