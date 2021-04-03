package com.ntu.medcheck.model;

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

    public Map<String, ArrayList<CheckUpEntry>> getCheckup() {
        return checkup;
    }

    public void setCheckup(Map<String, ArrayList<CheckUpEntry>> checkup) {
        this.checkup = checkup;
    }

    public String toString() {
        return checkup.get("202104").get(0).getTitle();
    }
}
