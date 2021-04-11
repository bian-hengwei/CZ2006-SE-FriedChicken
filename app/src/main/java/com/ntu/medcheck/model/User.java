package com.ntu.medcheck.model;

/**
 * User object.
 * keeps track of current user details.
 * Uses Singleton design pattern.
 * @author He Yinan
 */
public class User {

    private String userName;
    private String gender;
    private Time birthday = new Time();
    private String phoneNo;
    private String emailAddress;

    /**
     * The one instance of User that exists in the application.
     * Uses Singleton design pattern.
     */
    private static User userInstance = new User();

    /**
     * Constructor of User.
     * Made private.
     */
    private User() {
    }

    /**
     * Gets instance of User
     * @return instance of User
     */
    public static User getInstance() {
        return userInstance;
    }

    /**
     * Getter for userName of User class.
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for username of User class
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for gender of User class.
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setter for gender of User class
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter for phone number of User class.
     * @return phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Setter for phone number of User class
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Getter for emailAddress of User class.
     * @return email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Setter for email address of User class
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Getter for birthday of User class.
     * @return birthday
     */
    public Time getBirthday() {
        return birthday;
    }

    /**
     * Setter for birthday of User class
     * @param birthday
     */
    public void setBirthday(Time birthday) {
        this.birthday = birthday;
    }

    /**
     * Setter for instance of user
     * @param user
     */
    public static void setInstance(User user) {
        userInstance = user;
    }
}
