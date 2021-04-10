package com.ntu.medcheck.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.MedicationEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.view.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ScheduleMgr {

    Schedule schedule;
    DatabaseReference uRef;

    public ScheduleMgr() {
        schedule = Schedule.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        uRef = FirebaseDatabase.getInstance().getReference("Schedules").child(uid);
    }

    public void initialize(HomeActivity aca) {
        uRef.keepSynced(true);
        uRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                setSchedule(dataSnapshot);
                aca.initFragments();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setNotifications() throws ParseException {
        schedule = Schedule.getInstance();
        for (String key : schedule.getCheckup().keySet()) {
            ArrayList<CheckUpEntry> arr = schedule.getCheckup().get(key);
            for (CheckUpEntry entry : arr) {
                if (entry.getTime().toCalendar().after(Calendar.getInstance())) {
                    new CheckUpMgr().setNotification(entry);
                }
            }
        }
        for (MedicationEntry entry : schedule.getMedication()) {
            for (Time t : entry.getTime()) {
                MedicationMgr.getInstance().setNotification(entry, t);
            }
        }
    }

    public void cancelNotifications() {
        NotificationScheduler scheduler = new NotificationScheduler();
        schedule = Schedule.getInstance();
        for (String key : schedule.getCheckup().keySet()) {
            ArrayList<CheckUpEntry> arr = schedule.getCheckup().get(key);
            for (CheckUpEntry entry : arr) {
                if (entry.getTime().toCalendar().after(Calendar.getInstance())) {
                    scheduler.cancelNotification(CheckUpMgr.getContext(), entry.getTime().getId());
                }
            }
        }
        for (MedicationEntry entry : schedule.getMedication()) {
            for (Time t : entry.getTime()) {
                scheduler.cancelNotification(MedicationMgr.getContext(), t.getId());
            }
        }
    }

    public void save() {
        uRef.setValue(schedule);
    }

    private void setSchedule(DataSnapshot dataSnapshot) {
        if (dataSnapshot.child("checkup").exists()) {
            Map<String, ArrayList<CheckUpEntry>> checkup = new HashMap<>();
            for (DataSnapshot yearMonth: dataSnapshot.child("checkup").getChildren()) {
                checkup.put(yearMonth.getKey(), new ArrayList<>());
                for (DataSnapshot entry : yearMonth.getChildren()) {
                    checkup.get(yearMonth.getKey()).add(toCheckUpEntry(entry));
                }
            }
            schedule.setCheckup(checkup);
        }
        else {
            schedule.setCheckup(new HashMap<>());
        }

        if (dataSnapshot.child("medication").exists()) {
            ArrayList<MedicationEntry> medication = new ArrayList<>();
            for (DataSnapshot entry: dataSnapshot.child("medication").getChildren()) {
                medication.add(toMedicationEntry(entry));
            }
            schedule.setMedication(medication);
        }
        else {
            schedule.setMedication(new ArrayList<>());
        }
    }

    private CheckUpEntry toCheckUpEntry(DataSnapshot entry) {
        CheckUpEntry checkUpEntry = new CheckUpEntry();
        checkUpEntry.setClinic((String) entry.child("clinic").getValue());
        checkUpEntry.setComment((String) entry.child("comment").getValue());
        checkUpEntry.setName((String) entry.child("name").getValue());
        checkUpEntry.setTitle((String) entry.child("title").getValue());
        checkUpEntry.setType((String) entry.child("type").getValue());
        checkUpEntry.setTime(toTime(entry.child("time")));
        return checkUpEntry;
    }

    private MedicationEntry toMedicationEntry(DataSnapshot entry) {
        MedicationEntry medicationEntry = new MedicationEntry();
        medicationEntry.setDosage((String)entry.child("dosage").getValue());
        medicationEntry.setUnit((String) entry.child("unit").getValue());
        medicationEntry.setComment((String) entry.child("comment").getValue());
        medicationEntry.setName((String) entry.child("name").getValue());
        medicationEntry.setType((String) entry.child("type").getValue());
        medicationEntry.setFrequency((String) entry.child("frequency").getValue());

        DataSnapshot timeEntry = entry.child("time");
        ArrayList<Time> timeArrayList = new ArrayList<>();
        for (DataSnapshot time : timeEntry.getChildren()) {
            timeArrayList.add(toTime(time));
        }
        medicationEntry.setTime(timeArrayList);
        return medicationEntry;
    }

    private Time toTime(DataSnapshot timeSnapshot) {
        String hour = (String) timeSnapshot.child("hour").getValue();
        String minute = (String) timeSnapshot.child("minute").getValue();
        Time t = new Time(hour + minute);
        if (timeSnapshot.hasChild("year")) {
            t.setYear((String) timeSnapshot.child("year").getValue());
            t.setMonth((String) timeSnapshot.child("month").getValue());
            t.setDay((String) timeSnapshot.child("day").getValue());
        }
        t.setId(((Long) timeSnapshot.child("id").getValue()).intValue());
        return t;
    }
}
