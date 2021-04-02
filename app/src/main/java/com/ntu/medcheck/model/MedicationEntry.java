package com.ntu.medcheck.model;

import java.util.ArrayList;

/**
 * Medication entry is a subclass of entry
 * type of entry that stores medication details
 */
public class MedicationEntry extends Entry{

    int dosage;
    String unit;
    ArrayList<String> time = new ArrayList<>();

    public MedicationEntry() {
    }

    public MedicationEntry(String name) {
        super(name, "checkup");
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    public boolean addTime(String t) {
        for (int i = 0; i < time.size(); i++) {
            if (time.get(i).equals(t)) {
                return false;
            }
        }
        time.add(t);
        return true;
    }

    public boolean removeTime(String t) {
        for (int i = 0; i < time.size(); i++) {
            if (time.get(i).equals(t)) {
                time.remove(i);
                return true;
            }
        }
        return false;
    }
}
