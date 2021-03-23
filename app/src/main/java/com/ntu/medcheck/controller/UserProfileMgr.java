package com.ntu.medcheck.controller;

import com.ntu.medcheck.model.User;

public class UserProfileMgr {

    public boolean checkRegister(String usr, String pwd, String confirmpwd){ return true; } // called by reg activity and communicates with fAuth


    /**
     * check if password is in correct format
     * upper case and lower case and numbers and 6+ digits
     * @param pwd
     * @return
     */
    // check if a password is in acceptable format
    // e.g. Upper case + lower case + numbers + 6 or more
    public boolean passwordFormat(String pwd) { return true; }

    /**
     * reset password using firebase and email verification
     * @return result
     */
    public boolean reset() {return true;}

    /**
     * return current user object
     * @return
     */
    public User getUser(){return null;}

    /**
     * update user profile and save to local database
     */
    public void updateProfile(){} // to be overridden

    /**
     * logout of the account and return to login page
     */
    public void logout(){} // called by UserHomeFragment

}

