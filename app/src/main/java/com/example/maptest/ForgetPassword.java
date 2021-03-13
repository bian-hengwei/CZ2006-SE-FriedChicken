package com.example.maptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    FirebaseAuth fAuth;
    private EditText forgetpasswordemail;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetpasswordemail = (EditText) findViewById(R.id.ForgetpasswordEmail);
        resetPasswordButton = findViewById(R.id.resetpasswordbtn);
        fAuth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


    }

    //method to reset password
    private void resetPassword() {
        String email = forgetpasswordemail.getText().toString().trim();

        if(email.isEmpty()){
            forgetpasswordemail.setError("Email is required!");
            forgetpasswordemail.requestFocus();
            return;
        }

        //check if email is valid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgetpasswordemail.setError("Please provide valid email");
            forgetpasswordemail.requestFocus();
            return;
        }

        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgetPassword.this, "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void BacktoLoginpage(View view) {
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}