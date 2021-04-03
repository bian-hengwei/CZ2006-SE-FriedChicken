package com.ntu.medcheck.model;

/**
 * Check up entry is the entry object
 * storing health screening time and other details
 */
public class CheckUpEntry extends Entry {

    String title;
    String clinic;
    Time time;

    public CheckUpEntry() {
        time = new Time();
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
