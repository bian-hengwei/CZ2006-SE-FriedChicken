package com.ntu.medcheck.model;

/**
 * User object
 * keeps track of current user details
 */
public class User {

    private String userName;
    private String gender;
    private Time birthday = new Time();
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

    public Time getBirthday() {
        return birthday;
    }

    public void setBirthday(Time birthday) {
        this.birthday = birthday;
    }

    public static void setInstance(User user) {
        userInstance = user;
    }
}
