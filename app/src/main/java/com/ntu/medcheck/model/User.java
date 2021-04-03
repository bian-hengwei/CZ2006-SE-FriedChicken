package com.ntu.medcheck.model;

import java.util.Calendar;

/**
 * User object
 * keeps track of current user details
 */
public class User {
    private String userName;
    private String gender;
    private CheckUpTime birthday;
    private String phoneNo;
    private String emailAddress;


    private static User userInstance = new User();

    private User() {
    }

    public static User getInstance() {
        return userInstance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdayDay() {
        return birthday.getDay();
    }

    public void setBirthdayDay(String day) {
        this.birthday.setDay(day);
    }

    public String getBirthdayMonth() {
        return birthday.getMonth();
    }

    public void setBirthdayMonth(String month) {
        this.birthday.setMonth(month);
    }

    public String getBirthdayYear() {
        return birthday.getYear();
    }

    public void setBirthdayYear(String year) {
        this.birthday.setYear(year);
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
