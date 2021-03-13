package com.example.maptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.welcomebacktext);
        final TextView emailTextView = (TextView) findViewById(R.id.Email_Homepage);
        final TextView fullnameTextView = (TextView) findViewById(R.id.FullName_Homepage);
        final TextView phonenumberTextView = (TextView) findViewById(R.id.PhoneNumber_Homepage);
        final TextView dateofbirthTextView = (TextView) findViewById(R.id.DateOfBirth_Homepage);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if(userprofile != null){
                    String email = userprofile.email;
                    String fullName = userprofile.fullName;
                    String phoneNumber = userprofile.phoneNumber;
                    String dateOfBirth = userprofile.dateOfBirth;

                    greetingTextView.setText("Welcome, " + fullName + "!");
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


    }

    //method for logout button to logout
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    public void editUserProfileActivity(View view) {
        startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
        finish();
    }
}