package com.ntu.medcheck.controller;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.User;

import java.util.regex.Pattern;

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
     */
    public void resetPassword(AppCompatActivity aca, Context context) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        EditText emailInput = aca.findViewById(R.id.forgetPasswordEmailInput);
        String email = emailInput.getText().toString().trim();

        boolean valid = checkValid(email, context);
        if(!valid){
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>(){

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(context, "Check your email and reset password", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(context, "Try again", Toast.LENGTH_LONG).show();

                }
            }
        });

        // Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("emailAddress").equalTo(email);


    }

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

    public boolean checkValid(String email, Context context) {
        if(email.isEmpty()) {
            Toast.makeText(context, "Email is empty", Toast.LENGTH_LONG).show();
            return false;
        }
       /* else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Email is not valid", Toast.LENGTH_LONG).show();
            return false;
        }*/
        return true;
    }
}

