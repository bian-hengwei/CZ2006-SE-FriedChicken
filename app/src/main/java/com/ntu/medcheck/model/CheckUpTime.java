package com.ntu.medcheck.model;

import com.ntu.medcheck.BuildConfig;

import java.util.Calendar;

public class CheckUpTime {

    String year, month, day;
    String hour, minute;

    public CheckUpTime() {
    }

    public CheckUpTime(String time) {
        if (BuildConfig.DEBUG && time.length() != 12) {
            throw new AssertionError("Assertion failed");
        }
        year = time.substring(0, 4);
        month = time.substring(4, 6);
        day = time.substring(6, 8);
        hour = time.substring(8, 10);
        minute = time.substring(10, 12);
    }

    public void setTime(String time) {
        if (BuildConfig.DEBUG && time.length() != 12) {
            throw new AssertionError("Assertion failed");
        }
        year = time.substring(0, 4);
        month = time.substring(4, 6);
        day = time.substring(6, 8);
        hour = time.substring(8, 10);
        minute = time.substring(10, 12);
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Integer.parseInt(year),
                Integer.parseInt(month) - 1,
                Integer.parseInt(day),
                Integer.parseInt(hour),
                Integer.parseInt(minute)
        );
        return calendar;
    }
}
