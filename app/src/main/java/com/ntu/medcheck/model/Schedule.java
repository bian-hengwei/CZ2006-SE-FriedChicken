package com.ntu.medcheck.model;

import java.util.ArrayList;

/**
 * Schedule class
 * stores a list of two types of entries
 * Singleton method
 */
public class Schedule {
    ArrayList<Entry> scheduleList= new ArrayList<Entry>();
    // singleton pattern
    private static Schedule scheduleInstance = new Schedule();

    // read schedule in from local database

    // constructor is made private
    private Schedule() {}

    public static Schedule getInstance() {
        return scheduleInstance;
    }

    // setter and getter
    public ArrayList<Entry> getSchedule() {
        return scheduleList;
    }

    public void addEntry(Entry entry) {
        scheduleList.add(entry);
    }

    // remove a schedule entry using entry name
    // if entry is found and successfully removed, return true
    // if entry is not found, return false
    public boolean removeEntry(String name) {
        for(Entry entry : scheduleList) {
            if(entry.getName().equals(name)) {
                scheduleList.remove(entry);
                return true;
            }
        }
        return false;
    }

    // should put these into schedule manager, used to separate medication and checkup schedule
    public ArrayList<CheckUpEntry> getCheckUpSchedule() {
        ArrayList<CheckUpEntry> checkUpSchedule = new ArrayList<CheckUpEntry>();
        for(Entry e : scheduleList) {
            if(e.getType().equals("checkup")) {
                checkUpSchedule.add((CheckUpEntry) e);
            }
        }
        return checkUpSchedule;
    }

    public ArrayList<MedicationEntry> getMedicationSchedule() {
        ArrayList<MedicationEntry> medicationSchedule = new ArrayList<MedicationEntry>();
        for(Entry e : scheduleList) {
            if(e.getType().equals("medication")) {
                medicationSchedule.add((MedicationEntry) e);
            }
        }
        return medicationSchedule;
    }
}
