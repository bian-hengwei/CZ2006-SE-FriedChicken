package com.ntu.medcheck.model;

import java.util.Calendar;

/**
 * Used as Time object stored in all scheduled checkups and medications.
 * Contains an id used to identify the notification linked to this time.
 * @author He Yinan
 */
public class Time {

    String year, month, day;
    String hour, minute;
    int id;

    /**
     * Constructor of Time class.
     */
    public Time() {
    }

    /**
     * Constructor of Time class with a string input.
     * @param time
     */
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

    /**
     * Setter of Time class with a string input.
     * @param time
     */
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

    /**
     * Getter for year of Time class.
     * @return year attribute of the time
     */
    public String getYear() {
        return year;
    }

    /**
     * Setter for year of Time class.
     * @param year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Getter for month of Time class.
     * @return month attribute of the time
     */
    public String getMonth() {
        return month;
    }

    /**
     * Setter for month of Time class.
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Getter for day of Time class.
     * @return day attribute of the time
     */
    public String getDay() {
        return day;
    }

    /**
     * Setter for day of Time class.
     * @param day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Getter for hour of Time class.
     * @return hour attribute of the time
     */
    public String getHour() {
        return hour;
    }

    /**
     * Setter for hour of Time class.
     * @param hour
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * Getter for minute of Time class.
     * @return minute attribute of the time
     */
    public String getMinute() {
        return minute;
    }

    /**
     * Setter for minute of Time class.
     * @param minute
     */
    public void setMinute(String minute) {
        this.minute = minute;
    }

    /**
     * Converts Time to Calendar
     * @return
     */
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

    /**
     * Getter for id of Time class.
     * @return id attribute of the time
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id of Time class.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}
