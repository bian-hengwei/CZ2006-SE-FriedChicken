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

/**
 * ScheduleMgr class deals with all the logic of setting and cancelling notification and
 * syncing schedule to firebase database.
 */

public class ScheduleMgr {

    Schedule schedule;
    /**
     * Database reference for a particular user.
     */
    DatabaseReference uRef;

    /**
     * Constructor for ScheduleMgr class.
     * Get an instance of schedule and creates the user reference to firebase database.
     */
    public ScheduleMgr() {
        schedule = Schedule.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        uRef = FirebaseDatabase.getInstance().getReference("Schedules").child(uid);
    }

    /**
     * set up to keep information in classes in sync with information on firebase database.
     * @param aca HomeActivity
     */
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

    /**
     * Set notification for schedules saved in Schedule.
     * @throws ParseException
     */
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

    /**
     * Cancel a notification.
     * This method is called when user delete or edit a checkup or medication.
     */
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

    /**
     * Save any changes user made in the application.
     * Called when user adds, deletes or edit anything that needs to be saved.
     */
    public void save() {
        uRef.setValue(schedule);
    }

    /**
     * Retrieve schedule saved in firebase database to local objects.
     * Called when initializing.
     * @param dataSnapshot
     */
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

    /**
     * Set the values for CheckUpEntries which are retireved from firebase database.
     * Called in setSchedule method.
     * @param entry
     * @return
     */
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

    /**
     * Set the values for MedicationEntries which are retireved from firebase database.
     * Called in setSchedule method.
     * @param entry
     * @return
     */
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

    /**
     * Create a Time object using time retrieved from firebase database.
     * Called in toMedicationEntry and toCheckUpEntry.
     * @param timeSnapshot
     * @return
     */
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
