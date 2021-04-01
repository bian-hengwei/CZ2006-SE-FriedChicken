package com.ntu.medcheck.model;

/**
 * User object
 * keeps track of current user details
 */
public class User {
    private String userName;
    private String gender;
    private int age;
    private String birthday;
    private String phoneNo;
    private String emailAddress;

    public User() {
    }

    public User(String userName, String gender, int age, String birthday, String phoneNo, String emailAddress) {
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
    }

    private Schedule userSchedule = Schedule.getInstance();

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public Schedule getUserSchedule() {
        return userSchedule;
    }

    public void setUserSchedule(Schedule userSchedule) {
        this.userSchedule = userSchedule;
    }
}
