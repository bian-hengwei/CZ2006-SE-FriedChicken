package com.ntu.medcheck.controller;

import com.ntu.medcheck.model.User;

public class UserProfileMgr {

    public boolean checkRegister(String usr, String pwd, String confirmpwd){ return true; } // called by reg activity and communicates with fAuth


    /**
     *
     * @param pwd
     * @return
     */
    // check if a password is in acceptable format
    // e.g. Upper case + lower case + numbers + 6 or more
    public boolean passwordFormat(String pwd) { return true; }

    public boolean reset() {return true;}

    public User getUser(){return null;}

    public void updateProfile(){} // to be overridden

    public void logout(){} // called by UserHomeFragment

}

