package com.ntu.medcheck.controller;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.model.User;
import com.ntu.medcheck.view.RegisterActivity;

public class RegisterMgr {

    public boolean register(String userName, String emailAddress, String password, String rePassword) {
        // hard coded for testing
        userName = "yinan";
        password = "CZ2006";
        rePassword = "CZ2006";
        String gender = "Female";
        int age = 20;
        String birthday = "12/12/2000";
        String phoneNo = "12345678";
        emailAddress = "HEYI0003@e.ntu.edu.sg";

        User user = new User(userName, gender, age, birthday, phoneNo, emailAddress);

        String finalUserName = userName;
        String finalEmailAddress = emailAddress;

        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println("2");
                //if successful register
                if (task.isSuccessful()) {
                    User user = new User(finalUserName, gender, age, birthday, phoneNo, finalEmailAddress);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            System.out.println("3");
                            if (task.isSuccessful()) {
                                System.out.println("Success");
                            } else {
                                System.out.println("failed");
                                ;
                            }
                        }
                    });

                    //get instance of current user
                    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                    //send verification email
                    User.sendEmailVerification();

                } else {
                    System.out.println("failed");
                }
            }
        });

        return true;
    }
}
