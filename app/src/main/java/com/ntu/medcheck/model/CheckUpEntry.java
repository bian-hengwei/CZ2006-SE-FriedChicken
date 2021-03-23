package com.ntu.medcheck.model;

import java.util.Calendar;

public class CheckUpEntry extends Entry {
    String clinic; // should we save a ScreeningCentre object?
    Calendar time = Calendar.getInstance(); // return current date and time

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getClinic() {
        return clinic;
    }

    public void setCalendarYear(int year) {
        this.time.set(Calendar.YEAR, year);
    }

    public void setCalendarMonth(int month) {
        this.time.set(Calendar.MONTH, month);
    }

    public void setCalendarDay(int day) {
        this.time.set(Calendar.DATE, day);
    }

    public void setCalendarHour(int hour) {
        this.time.set(Calendar.HOUR, hour);
    }

    public void setCalendarMinute(int minute) {
        this.time.set(Calendar.MINUTE, minute);
    }

}
