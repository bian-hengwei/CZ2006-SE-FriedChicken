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

    // constructor is made private
    private Schedule() {
        checkup = new HashMap<>();
        medication = new ArrayList<>();
    }

    public static Schedule getInstance() {
        return scheduleInstance;
    }

    public Map<String, ArrayList<CheckUpEntry>> getCheckup() {
        return checkup;
    }

    public void setCheckup(Map<String, ArrayList<CheckUpEntry>> checkup) {
        this.checkup = checkup;
    }

    public ArrayList<MedicationEntry> getMedication() {
        return medication;
    }

    public void setMedication(ArrayList<MedicationEntry> medication) {
        Log.d("medication", "setMedication: ");
        this.medication = medication;
    }

    public void remove(CheckUpEntry delEntry) {
        for (ArrayList<CheckUpEntry> arr : checkup.values()) {
            for (CheckUpEntry entry : arr) {
                if (entry == delEntry) {
                    arr.remove(entry);
                    return;
                }
            }
        }
    }

    public void remove(MedicationEntry delEntry) {
        medication.remove(delEntry);
    }
}
