package com.ntu.medcheck.model;

/**
 * Check up entry is the entry object
 * storing health screening time and other details
 */
public class CheckUpEntry extends Entry {

    String title;
    String clinic;
    CheckUpTime time;

    public CheckUpEntry() {
        time = new CheckUpTime();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public CheckUpTime getTime() {
        return time;
    }

    public void setTime(CheckUpTime time) {
        this.time = time;
    }
}
