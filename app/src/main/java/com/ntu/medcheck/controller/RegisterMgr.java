package com.ntu.medcheck.controller;

import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.User;

/**
 * Deals with all logic from RegisterActivity
 * @author He Yinan
 */

public class RegisterMgr {
    /**
     * Log tag for debugging.
     */
    private static final String TAG = "RegisterManager";

    /**
     * Try to register user using firebase
     * Gets input from RegisterActivity and verify and then register
     * @param aca RegisterActivity
     */
    public void register(AppCompatActivity aca) {

        EditText userNameInput = aca.findViewById(R.id.registerNameInput);
        EditText emailAddressInput = aca.findViewById(R.id.registerEmailInput);
        EditText passwordInput = aca.findViewById(R.id.registerPasswordInput);
        EditText rePasswordInput = aca.findViewById(R.id.registerRePasswordInput);
        RadioButton maleInput = aca.findViewById(R.id.maleRadioButton);
        RadioButton femaleInput = aca.findViewById(R.id.femaleRadioButton);
        EditText phoneNoInput = aca.findViewById(R.id.registerPhoneNo);
        DatePicker birthdayInput = aca.findViewById(R.id.registerDatePicker) ;

        String userName = userNameInput.getText().toString().trim();
        String emailAddress = emailAddressInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String rePassword = rePasswordInput.getText().toString().trim();

        String gender;
        Log.d(TAG, "register: created variables userName, emailAddress, password, rePassword etc.");

        if (maleInput.isChecked()) {
            gender = "male";
            Log.d(TAG, "register: gender is male");
        }
        else if (femaleInput.isChecked()) {
            gender = "female";
            Log.d(TAG, "register: gender is female");
        }
        else {
            gender = null;
            Log.d(TAG, "register: gender is NULL");
        }

        int day = birthdayInput.getDayOfMonth();
        int month = birthdayInput.getMonth() + 1;
        int year = birthdayInput.getYear();

        String birthdayStr = String.format("%04d%02d%02d", year, month, day);

        String phoneNo = phoneNoInput.getText().toString().trim();
        boolean valid = checkInputValid(userName, emailAddress, password, rePassword, gender, birthdayStr, phoneNo, aca);
        Log.d(TAG, "register: created variables day, month, year, birthdayStr, phoneNo, boolean valid");

        if (!valid) {
            Log.d(TAG, "register: input is not valid, return");
            return;
        }

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "register: created FirebaseAuth, fAuth, get its instance");

        fAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(task -> {
            //if successful register
            if (task.isSuccessful()) {
                User user = User.getInstance();

                user.setUserName(userName);
                user.getBirthday().setTime(birthdayStr);
                user.setEmailAddress(emailAddress);
                user.setGender(gender);
                user.setPhoneNo(phoneNo);
                Log.d(TAG, "register: set username, birthday, email, gender, phoneNo");

                FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
                DatabaseReference uRef = fDatabase.getReference("Users");
                Log.d(TAG, "register: get instance of FirebaseDatabase fDatabase");

                uRef.child(fAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Log.d(TAG, "register: registration successful, please verify email before login");
                        Toast.makeText(aca, R.string.regiSuccess, Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println(R.string.regiFail);
                        Log.d(TAG, "register: registration failed, unknown error");
                        Toast.makeText(aca, R.string.regiFail, Toast.LENGTH_SHORT).show();
                    }
                });

                fAuth.getCurrentUser().sendEmailVerification();
                Log.d(TAG, "register: please check email for verification");
            }
            else {
                try {
                    Log.d(TAG, "register: registration failed, handling error");
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    Log.d(TAG, "register: registration failed, please use a stronger password");
                    Toast.makeText(aca, R.string.regiFailWeakPw, Toast.LENGTH_SHORT).show();
                }
                catch (FirebaseAuthUserCollisionException e) {
                    Log.d(TAG, "register: registration failed, email is already used");
                    Toast.makeText(aca, R.string.regiFailEmailExist, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Log.d(TAG, "register: registration failed, unknown error");
                    Toast.makeText(aca,R.string.regiFail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Verify if user inputs are valid
     * All fields must not be empty
     * Password and re-entered password must be equal
     * @param userName
     * @param emailAddress
     * @param password
     * @param rePassword
     * @param gender
     * @param birthday
     * @param phoneNo
     * @param context
     * @return boolean representing whether the inputs are valid
     */
    public boolean checkInputValid(String userName, String emailAddress, String password, String rePassword, String gender, String birthday, String phoneNo, Context context) {

        if (userName.isEmpty()) {
            Toast.makeText(context, R.string.emptyUsername, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (emailAddress.isEmpty()) {
            Toast.makeText(context, R.string.emptyEmail, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (password.isEmpty()) {
            Toast.makeText(context, R.string.emptyPw, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!password.equals(rePassword)) {
            Toast.makeText(context, R.string.regiFailRePw, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (gender == null) {
            Toast.makeText(context, R.string.emptyGender, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (birthday == null) {
            Toast.makeText(context, R.string.emptyBd, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (phoneNo.isEmpty()) {
            Toast.makeText(context, R.string.emptyPhone, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
