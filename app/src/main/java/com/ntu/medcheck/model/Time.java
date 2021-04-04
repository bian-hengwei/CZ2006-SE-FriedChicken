package com.ntu.medcheck.model;

import java.util.Calendar;

public class Time {

    String year, month, day;
    String hour, minute;

    public Time() {
    }

    public Time(String time) {
        if (time.length() == 12) {
            hour = time.substring(8, 10);
            minute = time.substring(10, 12);
            year = time.substring(0, 4);
            month = time.substring(4, 6);
            day = time.substring(6, 8);
        }
        else if(time.length() == 8) {
            year = time.substring(0, 4);
            month = time.substring(4, 6);
            day = time.substring(6, 8);
        }
        else if(time.length() == 4) {
            hour = time.substring(0, 2);
            minute = time.substring(2, 4);
        }
    }

    public void setTime(String time) {
        if (time.length() == 12) {
            hour = time.substring(8, 10);
            minute = time.substring(10, 12);
            year = time.substring(0, 4);
            month = time.substring(4, 6);
            day = time.substring(6, 8);
        }
        else if(time.length() == 8) {
            year = time.substring(0, 4);
            month = time.substring(4, 6);
            day = time.substring(6, 8);
        }
        else if(time.length() == 4) {
            hour = time.substring(0, 2);
            minute = time.substring(2, 4);
        }
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
        if (hour != null && hour != "")
            calendar.set(
                    Integer.parseInt(year),
                    Integer.parseInt(month) - 1,
                    Integer.parseInt(day),
                    Integer.parseInt(hour),
                    Integer.parseInt(minute)
            );
        else {
            if (hour != null && hour != "")
                calendar.set(
                        Integer.parseInt(year),
                        Integer.parseInt(month) - 1,
                        Integer.parseInt(day)
                );
        }
        return calendar;
    }
}
