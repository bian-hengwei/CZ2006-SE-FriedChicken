package com.ntu.medcheck.controller;

import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.Entry;

public class ScheduleMgr {

    /**
     * add new entry to schedule
     * @param e entry to add
     */
    public void addEntry(Entry e) {}

    /**
     * delete one entry from table
     * @param e entry to delete
     */
    public void deleteEntry(Entry e){} //

    /**
     * set reminder for entries when modified or added
     */
    public void reminder(){} // sends notification || How to implement?

    /**
     * return current schedule object
     * @return
     */
    public Schedule getSchedule(){return null;}

    /**
     * modifies one entry and modifies the reminder
     */
    public void modifyEntry() {}
}
