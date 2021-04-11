package com.ntu.medcheck.model;

/**
 * Check up entry is the entry object
 * storing health screening time and other details of a checkup
 */
public class CheckUpEntry extends Entry {

    String title;
    String clinic;
    Time time;

    /**
     * Constructor of CheckUpEntry class
     */
    public CheckUpEntry() {
        time = new Time();
    }

    /**
     * Getter for title attribute in CheckUpEntry
     * @return title of checkup
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title attribute in CheckUpEntry
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for clinic attribute in CheckUpEntry
     * @return clinic of checkup
     */
    public String getClinic() {
        return clinic;
    }

    /**
     * Setter for clinic attribute in CheckUpEntry
     * @param clinic
     */
    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    /**
     * Getter for time attribute in CheckUpEntry
     * @return time of checkup
     */
    public Time getTime() {
        return time;
    }

    /**
     * Setter for time attribute in CheckUpEntry
     * @param time
     */
    public void setTime(Time time) {
        this.time = time;
    }
}
