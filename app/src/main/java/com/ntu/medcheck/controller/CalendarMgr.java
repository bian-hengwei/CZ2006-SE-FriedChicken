package com.ntu.medcheck.controller;

import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.Schedule;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;

import java.util.ArrayList;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getMonth(int year, int month, View view) {
        Map<Integer, Object> map = new HashMap<>();
        String y = Integer.toString(year);
        String m = String.format("%02d", month);
        Log.d("test", y+m);
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(y+m, new ArrayList<>());
        for (CheckUpEntry child : monthArray) {
            map.put(Integer.parseInt((child.getTime().getDay())), "absent");
        }
        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);
        customCalendar.setDate(Calendar.getInstance(), map);
    }


    public void setListeners(View view) {
        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        String y = Integer.toString(newMonth.get(Calendar.YEAR));
        String m = String.format("%02d", newMonth.get(Calendar.MONTH)+1);
        Log.d("test", y+m);
        Map<Integer, Object>[] arr = new Map[2];
        arr[0] = new HashMap<>();
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(y+m, new ArrayList<>());
        for (CheckUpEntry child : monthArray) {
            arr[0].put(Integer.parseInt((child.getTime().getDay())), "absent");
        }
        Log.d("obbc", "onNavigationButtonClicked: ");
        return arr;
    }
}
