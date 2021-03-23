package com.ntu.medcheck.model;

/**
 * User object
 * keeps track of current user details
 */
public class User {
    private String userName;
    private char gender;
    private int age;
    private String birthday;
    private String phoneNo;
    private String emailAddress;

    public void User(String userName, char gender, int age, String birthday, String phoneNo, String emailAddress) {
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public char getGender() {
        return gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
