package com.ntu.medcheck.controller;

import android.content.Context;
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
 */

public class RegisterMgr {

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
        if (maleInput.isChecked()) {
            gender = "male";
        }
        else if (femaleInput.isChecked()) {
            gender = "female";
        }
        else {
            gender = null;
        }

        int day = birthdayInput.getDayOfMonth();
        int month = birthdayInput.getMonth() + 1;
        int year = birthdayInput.getYear();

        String birthdayStr = String.format("%04d%02d%02d", year, month, day);

        String phoneNo = phoneNoInput.getText().toString().trim();
        boolean valid = checkInputValid(userName, emailAddress, password, rePassword, gender, birthdayStr, phoneNo, aca);

        if (!valid) {
            return;
        }

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(task -> {
            //if successful register
            if (task.isSuccessful()) {
                User user = User.getInstance();

                user.setUserName(userName);
                user.getBirthday().setTime(birthdayStr);
                user.setEmailAddress(emailAddress);
                user.setGender(gender);
                user.setPhoneNo(phoneNo);

                FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
                DatabaseReference uRef = fDatabase.getReference("Users");
                uRef.child(fAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(aca, R.string.regiSuccess, Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println(R.string.regiFail);
                        Toast.makeText(aca, R.string.regiFail, Toast.LENGTH_SHORT).show();
                    }
                });

                fAuth.getCurrentUser().sendEmailVerification();
            }
            else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    Toast.makeText(aca, R.string.regiFailWeakPw, Toast.LENGTH_SHORT).show();
                }
                catch (FirebaseAuthUserCollisionException e) {
                    Toast.makeText(aca, R.string.regiFailEmailExist, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
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
