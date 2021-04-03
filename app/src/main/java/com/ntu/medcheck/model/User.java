package com.ntu.medcheck.model;

import java.util.Calendar;

/**
 * User object
 * keeps track of current user details
 */
public class User {
    private String userName = "yinan";
    private String gender = "female";
    private Calendar birthday = Calendar.getInstance();
    private String phoneNo = "12345678";
    private String emailAddress = "heyi0003@e.ntu.edu.sg";


    private static User userInstance = new User();

    private User() {
    }

    public User(String userName, String gender, Calendar birthday, String phoneNo, String emailAddress) {
        this.userName = userName;
        this.gender = gender;
        this.birthday = Calendar.getInstance();
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
        birthday.set(Calendar.DAY_OF_MONTH, 8);
        birthday.set(Calendar.MONTH, 11);
        birthday.set(Calendar.YEAR, 2000);


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

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
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
