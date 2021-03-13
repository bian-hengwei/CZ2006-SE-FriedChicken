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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {

    private EditText mFullName, mEmail, mPhoneNumber;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView mDateOfBirth;
    Button msavechangesbtn;

    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mDisplayDate = (TextView)findViewById(R.id.editprofileDateOfBirth);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR); //get current year
                int month = cal.get(Calendar.MONTH); //get current month
                int day = cal.get(Calendar.DAY_OF_MONTH); //get current day

                DatePickerDialog dialog = new DatePickerDialog(
                        EditProfileActivity.this,
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


        //getting instance of current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        //getting user ID of current user
        userID = user.getUid();

        final EditText emailTextView = (EditText) findViewById(R.id.editprofileEmail);
        final EditText fullnameTextView = (EditText) findViewById(R.id.editprofileName);
        final EditText phonenumberTextView = (EditText) findViewById(R.id.editprofilePhoneNumber);
        final TextView dateofbirthTextView = (TextView) findViewById(R.id.editprofileDateOfBirth);

        //getting values of user profile from database and displaying it on the edit profile page
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if(userprofile != null){
                    String email = userprofile.email;
                    String fullName = userprofile.fullName;
                    String phoneNumber = userprofile.phoneNumber;
                    String dateOfBirth = userprofile.dateOfBirth;

                    emailTextView.setText(email);
                    fullnameTextView.setText(fullName);
                    phonenumberTextView.setText(phoneNumber);
                    dateofbirthTextView.setText(dateOfBirth);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
            }
        });




        fAuth = FirebaseAuth.getInstance(); //get instance of firebase
        msavechangesbtn = (Button)findViewById(R.id.savechangesbtn);
        mFullName = (EditText) findViewById(R.id.editprofileName);
        mEmail = (EditText) findViewById(R.id.editprofileEmail);
        mPhoneNumber = (EditText) findViewById(R.id.editprofilePhoneNumber);
        mDateOfBirth= (TextView) findViewById(R.id.editprofileDateOfBirth);

        msavechangesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim(); //get email from EditText
                String fullName = mFullName.getText().toString().trim();
                String phoneNumber = mPhoneNumber.getText().toString().trim();
                String dateOfBirth = mDateOfBirth.getText().toString().trim();

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

                //check if date of birth is empty
                if(TextUtils.isEmpty(dateOfBirth)){
                    mDateOfBirth.setError("Birth date is required.");
                    mDateOfBirth.requestFocus();
                    return;
                }

                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(EditProfileActivity.this, "Email is changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




                //make the changes in realtime database
                reference.child(userID).child("fullName").setValue(fullName);
                reference.child(userID).child("dateOfBirth").setValue(dateOfBirth);
                reference.child(userID).child("email").setValue(email);
                reference.child(userID).child("phoneNumber").setValue(phoneNumber);

                Toast.makeText(EditProfileActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();



            }
        });




    }





    public void BacktoHomepage(View view) {
        startActivity(new Intent(getApplicationContext(),Homepage.class));
        finish();
    }
}