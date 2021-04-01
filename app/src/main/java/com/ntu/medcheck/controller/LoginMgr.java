package com.ntu.medcheck.controller;

import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginMgr {


    FirebaseAuth fAuth;

    /**
     * use firebase to verify if login is successful
     * @param v Login view
     * @return true if successful
     */
    // takes in the information from LoginActivity and verifies with fAuth
    // try to use context or bundle??
    public boolean verifyLogin(String email, String password) {
        fAuth = FirebaseAuth.getInstance();
        return true;
    }

}
