package com.ntu.medcheck.model;

import java.util.ArrayList;

/**
 * Medication entry is a subclass of entry
 * type of entry that stores medication details
 */
public class MedicationEntry extends Entry{

    String dosage;
    String unit;
    ArrayList<Time> time = new ArrayList<>();

    public MedicationEntry() {
    }

    public MedicationEntry(String name) {
        super(name, "checkup");
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ArrayList<Time> getTime() {
        return time;
    }

    public void setTime(ArrayList<Time> time) {
        this.time = time;
    }

    /* public boolean addTime(Time t) {
        for (int i = 0; i < time.size(); i++) {
            if (time.get(i).getDay().equals(t.getDay()) && time.get(i).getMonth().equals(t.getMonth()) && time.get(i).getYear().equals(t.getYear()) && time.get(i).getHour().equals(t.getMinute())) {
                return false;
            }
        }
        time.add(t);
        return true;
    }*/
/*1
    public boolean removeTime(String t) {
        for (int i = 0; i < time.size(); i++) {
            if (time.get(i).equals(t)) {
                time.remove(i);
                return true;
            }
        }
        return false;
    } */
}
