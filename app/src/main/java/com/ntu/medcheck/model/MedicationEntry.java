package com.ntu.medcheck.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Medication entry is a subclass of entry
 * type of entry that stores medication details
 */
public class MedicationEntry extends Entry{
    // not really sure here
    String dosage; // what is dosage here? i thought should be int
    ArrayList<LocalTime> time = new ArrayList<LocalTime>();
    String repeat; // notification repeat for the day, should be array of enum

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDosage() {
        return dosage;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTime(int hour, int minute) {
        LocalTime addedTime = LocalTime.of(hour, minute);
        time.add(addedTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean removeTime(int hour, int minute) {
        LocalTime removedTime = LocalTime.of(hour, minute);
        for(LocalTime lt : time) {
            if(lt.compareTo(removedTime) == 0) {
                time.remove(lt);
                return true; // if item is successfully removed
            }
        }
        return false; // if item is not found
    }

    // modify an existing time
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean modifyTime(int oldHour, int oldMinute, int newHour, int newMinute) {
        boolean successful = removeTime(oldHour, oldMinute);
        if(successful) {
            addTime(newHour, newMinute);
            return true;
        }
        else {
            return false;
        }
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getRepeat() {
        return repeat;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType() {
        this.entryType = "medication";
    }
}
