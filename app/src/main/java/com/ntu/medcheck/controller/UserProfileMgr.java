package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.CheckUpTime;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.User;
import com.ntu.medcheck.utils.SafeOnClickListener;
import com.ntu.medcheck.view.LoginActivity;
import com.ntu.medcheck.view.fragment.UserHomeFragment;

import java.time.Year;
import java.util.Calendar;
import java.util.regex.Pattern;

public class UserProfileMgr {

    User user;
    FirebaseDatabase fDatabase;
    DatabaseReference uRef;

    public void init(String name, String birthday, String email, String gender, String phoneno) {
        User user = User.getInstance();
        user.setUserName(name);
        user.getBirthday().setTime("20000101");
        user.setEmailAddress(email);
        user.setGender(gender);
        user.setPhoneNo(phoneno);
    }

    //////////////////new////////////////////////////////////////////////////////////////////////////////
/*
    public UserProfileMgr() {
        user = User.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        DatabaseReference sRef = fDatabase.getReference("Users");
        uRef = sRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void initialize() {
        System.out.println("1");
        uRef.keepSynced(true);
        System.out.println("2");


        DatabaseReference sRef = fDatabase.getReference("Users");
        user.setUserName("He yinan");
        user.getBirthday().setTime("20121230");
        user.setEmailAddress("yinan.he.688@gmail.com");
        user.setGender("female");
        user.setPhoneNo("87654321");
        uRef.setValue(user);



        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("3");
                // load data from database into object
                if (dataSnapshot.exists()) {
                    System.out.println("Snapshot exists");
                }
                else {
                    System.out.println("Snapshot DO NOT exist");
                }
                User.setInstance((User) dataSnapshot.getValue());
                Log.d("loading", "Loading data");
                Log.d("user", "abcde");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Err", "loadPost:onCancelled", databaseError.toException());
            }
        };

        uRef.addValueEventListener(postListener);

    }























*/






    //////////////////new/////////////////////////////////////////////////////////////////////////////////



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
    public User getUser(){
        return User.getInstance();
    }


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

    public void displayInfoOnUserHome(AppCompatActivity aca, View view) {
        TextView userNameView = view.findViewById(R.id.welcomeMessageUserName);
        EditText userNameInput = view.findViewById(R.id.userProfileUsernameInput);
        EditText emailInput = view.findViewById(R.id.userProfileEmailInput);
        DatePicker birthdayInput = view.findViewById(R.id.datePicker) ;
        RadioButton male = view.findViewById(R.id.maleRadioButton);
        RadioButton female = view.findViewById(R.id.femaleRadioButton);
        Button confirmBtn = view.findViewById(R.id.confirmButton);
        Button editBtn = view.findViewById(R.id.editProfileButton);

        userNameInput.setEnabled(false);
        emailInput.setEnabled(false);
        birthdayInput.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);

        confirmBtn.setText("Logout");
        String userNameStr = user.getUserName();
        userNameView.setText(userNameStr);

        userNameInput.setText(userNameStr);

        emailInput.setText(user.getEmailAddress());

        String day = user.getBirthdayDay();
        String month = user.getBirthdayMonth();
        String year = user.getBirthdayYear();

        //System.out.println(birthday.get(Calendar.YEAR));

        birthdayInput.init(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), null);
        //birthdayInput.init(2021, 12, 13, null);


        String gender = user.getGender();

        if(gender == null) {
            male.setChecked(true);
        }
        else if(gender.equals("male")) {
            male.setChecked(true);
            female.setChecked(false);
        }
        else {
            female.setChecked(true);
            male.setChecked(false);
        }

        editBtn.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                System.out.println("1111111111111111111111111111111111111111111");
                editProfile(aca, view);
            }
        });
    }

    /**
     * update user profile and save to local database
     */
    public void editProfile(AppCompatActivity aca, View view){
        EditText userNameInput = view.findViewById(R.id.userProfileUsernameInput);
        EditText emailInput = view.findViewById(R.id.userProfileEmailInput);
        DatePicker birthdayInput = view.findViewById(R.id.datePicker) ;
        RadioButton male = view.findViewById(R.id.maleRadioButton);
        RadioButton female = view.findViewById(R.id.femaleRadioButton);
        Button confirmBtn = view.findViewById(R.id.confirmButton);


        userNameInput.setEnabled(true);
        emailInput.setEnabled(true);
        birthdayInput.setEnabled(true);
        male.setEnabled(true);
        female.setEnabled(true);



        confirmBtn.setText("Confirm");

        confirmBtn.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {

                if(confirmBtn.getText().equals("Logout")) {
                    logout(aca);
                }
                else {
                    // save and update user profile
                    System.out.println("clicked confirm button");

                    // save name
                    String userName = userNameInput.getText().toString();
                    user.setUserName(userName);

                    // save email
                    String email = emailInput.getText().toString();
                    user.setEmailAddress(email);

                    // save birthday
                    int day = birthdayInput.getDayOfMonth();
                    int month = birthdayInput.getMonth();
                    int year = birthdayInput.getYear();

                    user.setBirthday(new CheckUpTime(String.format("%04d%02d%02d", year, month, day)));

                    // gender
                    String gender;
                    if(male.isChecked()) {
                        gender = "male";
                    }
                    else {
                        gender = "female";
                    }
                    user.setGender(gender);

                    Toast.makeText(aca, "User profile saved", Toast.LENGTH_LONG).show();

                    // disable
                    displayInfoOnUserHome(aca, view);
                }
            }
        });

    } // to be overridden

    public void logout(AppCompatActivity aca) {
        FirebaseAuth mFirebaseAuth;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signOut();

        Toast.makeText(aca, "Logging out", Toast.LENGTH_LONG).show();
        aca.startActivity(new Intent(aca, LoginActivity.class));
        aca.finish();

    }


}

