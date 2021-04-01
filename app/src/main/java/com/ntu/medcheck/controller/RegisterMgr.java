package com.ntu.medcheck.controller;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.model.User;

import java.util.Objects;

public class RegisterMgr {

    public void register(String userName, String emailAddress, String password, String rePassword, Context context) {
        // hard coded for testing
        String gender = "Female";
        int age = 20;
        String birthday = "12/12/2000";
        String phoneNo = "12345678";

        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        // additional checkings that firebase do not provide
        boolean valid = checkInputValid(userName, emailAddress, password, rePassword, gender, age, birthday, phoneNo, context);

        if(valid) {
            fAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(task -> {
                //if successful register
                if (task.isSuccessful()) {
                    User user1 = new User(userName, gender, age, birthday, phoneNo, emailAddress);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user1).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(context, "Registration successful, please verify email and login", Toast.LENGTH_LONG).show();
                        } else {
                            System.out.println("failed");
                            Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show();
                        }
                    });

                    //get instance of current user
                    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                    //send verification email
                    User.sendEmailVerification();
                }
                else {
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(context, "Registration unsuccessful, password too weak", Toast.LENGTH_LONG).show();
                    }
                    catch(FirebaseAuthUserCollisionException e) {
                        Toast.makeText(context, "Registration unsuccessful, email already used", Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e) {
                        System.out.println("Unknown Error");
                    }
                }
            });
        }
    }

    public boolean checkInputValid(String userName, String emailAddress, String password, String rePassword, String gender, int age, String birthday, String phoneNo, Context context) {
        // check password = rePassword
        if(!password.equals(rePassword)) {
            Toast.makeText(context, "Registration unsuccessful, re-entered password is different from password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
