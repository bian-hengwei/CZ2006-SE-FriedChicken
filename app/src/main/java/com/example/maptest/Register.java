package com.example.maptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Register extends AppCompatActivity{
    private static final String TAG = "RegisterActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText mFullName, mEmail, mPassword, mPhoneNumber;
    TextView mDateOfBirth;
    Button mRegisterBtn;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDisplayDate = (TextView) findViewById(R.id.DateOfBirth); //date of birth TextView object
        //open dialog box to select date of birth
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR); //get current year
                int month = cal.get(Calendar.MONTH); //get current month
                int day = cal.get(Calendar.DAY_OF_MONTH); //get current day

                DatePickerDialog dialog = new DatePickerDialog(
                        Register.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day); //set the default date to current date
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //set the date on the textview
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1; // month starts from 0
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        mFullName = (EditText) findViewById(R.id.FullName);
        mEmail = (EditText) findViewById(R.id.Email_register);
        mPassword = (EditText) findViewById(R.id.Password_register);
        mPhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        mDateOfBirth= (TextView) findViewById(R.id.DateOfBirth);
        fAuth = FirebaseAuth.getInstance(); //get instance of firebase
        mRegisterBtn = (Button) findViewById(R.id.Registerbtn);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim(); //get email from EditText
                String password = mPassword.getText().toString().trim(); //get password from EditText
                String fullName = mFullName.getText().toString().trim();
                String phoneNumber = mPhoneNumber.getText().toString().trim();
                String dateOfBirth = mDateOfBirth.getText().toString().trim();

                //check if fullName is empty
                if(TextUtils.isEmpty(fullName)){
                    mFullName.setError("Name is required.");
                    mFullName.requestFocus();
                    return;
                }

                //check if phoneNumber is empty
                if(TextUtils.isEmpty(phoneNumber)){
                    mPhoneNumber.setError("Phone Number is required.");
                    mPhoneNumber.requestFocus();
                    return;
                }

                //check if email is empty
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    mEmail.requestFocus();
                    return;
                }

                //check if email is valid
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmail.setError("Please provide valid email");
                    mEmail.requestFocus();
                    return;
                }

                //check if password is empty
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    mPassword.requestFocus();
                    return;
                }
                //check if password is more than 8 characters
                if(password.length() < 8){
                    mPassword.setError("Password must be 8 characters or more");
                    mPassword.requestFocus();
                    return;
                }

                //check if date of birth is empty
                if(TextUtils.isEmpty(dateOfBirth)){
                    mDateOfBirth.setError("Birth date is required.");
                    mDateOfBirth.requestFocus();
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if successful register
                        if(task.isSuccessful()){
                            User user = new User(fullName, dateOfBirth, email, phoneNumber);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "User has been registered successfully! Please Check your email to verify your account.", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            //get instance of current user
                            FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                            //send verification email
                            User.sendEmailVerification();

                        }else{
                            Toast.makeText(getApplicationContext(), "Registration failed" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    //function for back button onclick to go back to login activity
    public void BacktoLoginpage(View view) {
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
