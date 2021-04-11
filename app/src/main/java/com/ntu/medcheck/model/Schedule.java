package com.ntu.medcheck.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Schedule class.
 * stores a list of two types of entries.
 * Uses singleton design pattern.
 */
public class Schedule {

    Map<String, ArrayList<CheckUpEntry>> checkup;
    ArrayList<MedicationEntry> medication;

    /**
     * Creates only one instance of Schedule.
     * Uses singleton design pattern.
     */
    private static Schedule scheduleInstance = new Schedule();

    /**
     * Constructor for Schedule class.
     * Made private.
     */
    private Schedule() {
        checkup = new HashMap<>();
        medication = new ArrayList<>();
    }

    /**
     * Used to get the instance of schedule.
     * @return the one schedule instance in the application
     */
    public static Schedule getInstance() {
        return scheduleInstance;
    }

    /**
     * Getter for checkup of Schedule.
     * @return all scheduled checkup
     */
    public Map<String, ArrayList<CheckUpEntry>> getCheckup() {
        return checkup;
    }

    /**
     * Setter for checkup in schedule.
     */
    public void setCheckup(Map<String, ArrayList<CheckUpEntry>> checkup) {
        this.checkup = checkup;
    }

    /**
     * Getter for medication of Schedule.
     * @return all scheduled medication
     */
    public ArrayList<MedicationEntry> getMedication() {
        return medication;
    }

    /**
     * Setter for medication in schedule.
     */
    public void setMedication(ArrayList<MedicationEntry> medication) {
        Log.d("medication", "setMedication: ");
        this.medication = medication;
    }

    /**
     * used to remove a particular CheckUpEntry from schedule.
     * @param delEntry
     */
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

    /**
     * used to remove a particular MedicationEntry from schedule.
     * @param delEntry
     */
    public void remove(MedicationEntry delEntry) {
        medication.remove(delEntry);
    }
}
