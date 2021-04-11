package com.ntu.medcheck.model;

import java.util.ArrayList;

/**
 * Medication entry is a subclass of entry
 * type of entry that stores medication details
 * @author Fu Yongding
 */
public class MedicationEntry extends Entry{

    String dosage = "";
    String unit = "";
    String frequency = "";
    ArrayList<Time> time = new ArrayList<>();

    /**
     * Constructor of MedicationEntry
     */
    public MedicationEntry() {
    }

    /**
     * Constructor of MedicationEntry with name input
     * @param name
     */
    public MedicationEntry(String name) {
        super(name, "medication");
    }

    /**
     * Getter for dosage of medication
     * @return dosage of medication
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Setter for dosage of medication
     * @param dosage
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Getter for unit of medication
     * @return unit of dosage of medication
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Setter for unit of medication
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Getter for time of medication
     * @return
     */
    public ArrayList<Time> getTime() {
        return time;
    }

    /**
     * Setter for time of medication
     * @param time
     */
    public void setTime(ArrayList<Time> time) {
        this.time = time;
    }

    /**
     * Getter for frequency of medication
     * @return
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Setter for frequency of medication
     * @param frequency
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
