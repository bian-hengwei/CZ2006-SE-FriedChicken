package com.ntu.medcheck.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Schedule class
 * stores a list of two types of entries
 * Singleton method
 */
public class Schedule {

    Map<String, ArrayList<CheckUpEntry>> checkup;
    ArrayList<MedicationEntry> medication;

    // singleton pattern
    private static Schedule scheduleInstance = new Schedule();

    // read schedule in from local database

    // constructor is made private
    private Schedule() {
        checkup = new HashMap<>();
    }

    public static Schedule getInstance() {
        return scheduleInstance;
    }

    public static void setInstance(Schedule schedule) {
        scheduleInstance = schedule;
    }

    public Map<String, ArrayList<CheckUpEntry>> getCheckup() {
        return checkup;
    }

    public void setCheckup(Map<String, ArrayList<CheckUpEntry>> checkup) {
        Log.d("wuwuwu", "setCheckup: ");
        this.checkup = checkup;
    }

    public String toString() {
        String str = "";
        for (ArrayList<CheckUpEntry> list : checkup.values()) {
            str += "Hi";
            for (CheckUpEntry checkUpEntry : list) {
                str += checkUpEntry.getTitle();
                str += "\n";
            }
        }
        return str;
    }
}
