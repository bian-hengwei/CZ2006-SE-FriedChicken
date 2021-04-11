package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.model.User;
import com.ntu.medcheck.utils.SafeOnClickListener;
import com.ntu.medcheck.view.HomeActivity;
import com.ntu.medcheck.view.LoginActivity;

/**
 * UserProfile class deals with all logic regarding editing user profile and logout.
 * Used by UserHomeFragment.
 * @author Fu Yongding
 */
public class UserProfileMgr {

    User user;
    FirebaseDatabase fDatabase;
    DatabaseReference uRef;

    /**
     * Constructor for UserProfileMgr class.
     * Gets instance of the user and firebase database.
     */
    public UserProfileMgr() {
        user = User.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
    }

    /**
     * set up to keep information in User class in sync with information on firebase database.
     * @param aca HomeActivity
     */
    public void initialize(HomeActivity aca) {
        DatabaseReference sRef = fDatabase.getReference("Users");
        uRef = sRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        uRef.keepSynced(true);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User.setInstance(dataSnapshot.getValue(User.class));
                user = User.getInstance();
                Log.d("user", user.getUserName());
                aca.initFragments();
                Log.d("user", user.getUserName());
                Log.d("user", user.getBirthday().toCalendar().toString());
                Log.d("loading", "Loading data");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Err", "loadPost:onCancelled", databaseError.toException());
            }
        };
        uRef.addValueEventListener(postListener);
    }

    /**
     * Reset password using firebase and email verification.
     * Checks if email is valid before sending.
     */
    public void resetPassword(AppCompatActivity aca, Context context) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        EditText emailInput = aca.findViewById(R.id.forgetPasswordEmailInput);
        String email = emailInput.getText().toString().trim();

        boolean valid = checkValid(email, context);
        if (!valid) {
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(aca, R.string.ResetPwEmail, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(aca, R.string.retry, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Checks if user submitted an empty email.
     * Display a toast if email is empty.
     * @param email user entered email
     * @param context
     * @return
     */
    public boolean checkValid(String email, Context context) {
        if(email.isEmpty()) {
            Toast.makeText(context, R.string.emptyEmail, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Display user profile on UserHomeFragment.
     * Logout user if user clicks logout button.
     * Allow user to edit if user clicks edit button
     * @param aca
     * @param view
     */
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

        confirmBtn.setText(aca.getResources().getString(R.string.logout));
        String userNameStr = user.getUserName();
        userNameView.setText(userNameStr);

        userNameInput.setText(userNameStr);

        emailInput.setText(user.getEmailAddress());

        String day = user.getBirthday().getDay();
        String month = user.getBirthday().getMonth();
        String year = user.getBirthday().getYear();

        try {
            birthdayInput.init(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), null);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String gender = user.getGender();

        if (gender == null) {
            male.setChecked(true);
        }
        else if (gender.equals("male")) {
            male.setChecked(true);
            female.setChecked(false);
        }
        else {
            female.setChecked(true);
            male.setChecked(false);
        }

        confirmBtn.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                if(confirmBtn.getText().equals(aca.getResources().getString(R.string.logout))) {
                    logout(aca);
                }
            }
        });

        editBtn.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                editProfile(aca, view);
            }
        });
    }

    /**
     * Retrieve user's updated profile from UserHomeFragment.
     * Save information onto firebase database useing save() method.
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

        confirmBtn.setText(aca.getResources().getString(R.string.confirmButton));

        confirmBtn.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {

                if (confirmBtn.getText().equals(aca.getResources().getString(R.string.logout))) {
                    logout(aca);
                }
                else {
                    // save and update user profile
                    System.out.println("clicked confirm button");

                    // save name
                    String userName = userNameInput.getText().toString();
                    user.setUserName(userName);

                    // save email
                    /*
                    String email = emailInput.getText().toString();
                    user.setEmailAddress(email);

                     */

                    // save birthday
                    int day = birthdayInput.getDayOfMonth();
                    int month = birthdayInput.getMonth();
                    int year = birthdayInput.getYear();

                    user.setBirthday(new Time(String.format("%04d%02d%02d", year, month + 1, day)));

                    // gender
                    String gender;
                    if(male.isChecked()) {
                        gender = "male";
                    }
                    else {
                        gender = "female";
                    }
                    user.setGender(gender);

                    Toast.makeText(aca, R.string.saveSuccess, Toast.LENGTH_SHORT).show();

                    // disable
                    displayInfoOnUserHome(aca, view);

                    save();
                }
            }
        });
    }

    /**
     * Called by editProfile method to save user's edited profile onto firebase database.
     */
    private void save() {
        DatabaseReference sRef = fDatabase.getReference("Users");
        uRef = sRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        uRef.setValue(user);
    }

    /**
     * Logout user
     * Returns back to login page
     * @param aca
     */
    public void logout(AppCompatActivity aca) {
        new ScheduleMgr().cancelNotifications();

        FirebaseAuth mFirebaseAuth;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signOut();

        Toast.makeText(aca, R.string.logoutMessage, Toast.LENGTH_SHORT).show();
        aca.startActivity(new Intent(aca, LoginActivity.class));
        aca.finish();
    }
}
