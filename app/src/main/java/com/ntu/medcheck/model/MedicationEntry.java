package com.ntu.medcheck.model;

import java.util.ArrayList;

/**
 * Medication entry is a subclass of entry
 * type of entry that stores medication details
 */
public class MedicationEntry extends Entry{

    String dosage = "";
    String unit = "";
    String frequency = "";
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
