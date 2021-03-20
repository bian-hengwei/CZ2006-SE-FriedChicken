package com.example.test.controller;

import com.example.test.model.User;

public class UserProfileMgr {

    public void register(){} // called by reg activity and communicates with fAuth
    public User getUser(){return null;}
    public void updateProfile(){} // to be overridden
    public void logout(){} // called by EditProfile?
}
