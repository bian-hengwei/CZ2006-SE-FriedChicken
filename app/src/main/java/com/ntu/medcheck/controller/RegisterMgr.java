package com.ntu.medcheck.controller;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.User;

public class RegisterMgr {

    // TODO: aca stuff
    public void register(AppCompatActivity aca,  Context context) {

        EditText userNameInput = aca.findViewById(R.id.registerNameInput);
        EditText emailAddressInput = aca.findViewById(R.id.registerEmailInput);
        EditText passwordInput = aca.findViewById(R.id.registerPasswordInput);
        EditText rePasswordInput = aca.findViewById(R.id.registerRePasswordInput);

        String userName = userNameInput.getText().toString().trim();
        String emailAddress = emailAddressInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String rePassword = rePasswordInput.getText().toString().trim();

        // hard coded for testing
        String gender = "Female";
        int age = 20;
        String birthday = "12/12/2000";
        String phoneNo = "12345678";

        // additional checkings that firebase does not provide
        boolean valid = checkInputValid(userName, emailAddress, password, rePassword, gender, age, birthday, phoneNo, context);

        if(!valid) {
            return;
        }

        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

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

    public boolean checkInputValid(String userName, String emailAddress, String password, String rePassword, String gender, int age, String birthday, String phoneNo, Context context) {
        // check password = rePassword

        if(userName.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, username is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(emailAddress.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, email address is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(password.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, password is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!password.equals(rePassword)) {
            Toast.makeText(context, "Registration unsuccessful, re-entered password is different from password", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(gender.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, gender is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(age == 0) {
            Toast.makeText(context, "Registration unsuccessful, age cannot be 0", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(birthday.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, birthday is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(phoneNo.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, phone number is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
