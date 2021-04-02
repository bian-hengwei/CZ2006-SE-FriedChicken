package com.ntu.medcheck.controller;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.User;

import java.util.Calendar;

public class RegisterMgr {

    public void register(AppCompatActivity aca) {

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
        Calendar birthday = null;
        birthday.set(Calendar.YEAR, 2000);
        birthday.set(Calendar.MONTH, 10);
        birthday.set(Calendar.DAY_OF_MONTH, 8);
        String phoneNo = "12345678";

        boolean valid = checkInputValid(userName, emailAddress, password, rePassword, gender, age, birthday, phoneNo, aca);

        if (!valid) {
            return;
        }

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(task -> {
            //if successful register
            if (task.isSuccessful()) {
                User user = new User(userName, gender, age, birthday, phoneNo, emailAddress);
                FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
                DatabaseReference uRef = fDatabase.getReference("Users");
                uRef.child(fAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(aca, "Registration successful, please verify email and login", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println("failed");
                        Toast.makeText(aca, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                });

                fAuth.getCurrentUser().sendEmailVerification();
            }
            else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    Toast.makeText(aca, "Registration unsuccessful, password too weak", Toast.LENGTH_LONG).show();
                }
                catch (FirebaseAuthUserCollisionException e) {
                    Toast.makeText(aca, "Registration unsuccessful, email already used", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    System.out.println("Unknown Error");
                }
            }
        });
    }

    public boolean checkInputValid(String userName, String emailAddress, String password, String rePassword, String gender, int age, Calendar birthday, String phoneNo, Context context) {

        if (userName.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, username is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (emailAddress.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, email address is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (password.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, password is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!password.equals(rePassword)) {
            Toast.makeText(context, "Registration unsuccessful, re-entered password is different from password", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (gender.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, gender is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (age == 0) {
            Toast.makeText(context, "Registration unsuccessful, age cannot be 0", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (birthday == null) {
            Toast.makeText(context, "Registration unsuccessful, birthday is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (phoneNo.isEmpty()) {
            Toast.makeText(context, "Registration unsuccessful, phone number is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
