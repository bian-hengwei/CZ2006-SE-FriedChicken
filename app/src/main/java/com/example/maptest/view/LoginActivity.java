package com.example.maptest.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maptest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{
    private Button loginbutton;
    private EditText mEmail, mPassword;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance(); // get instance of firebase
        loginbutton = findViewById(R.id.Loginbtn);
        mEmail = findViewById(R.id.Email_login);
        mPassword = findViewById(R.id.Passwordlogin);

        //check if user is already logged in. If user is logged in, send user to homepage
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            finish();
        }
    }

    //function for register text to go to register page
    public void goToRegisterPage(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }

    //function for forget password text to go to forget password page
    public void forgetPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
        finish();
    }

    //function to login
    public void login(View view) {
        String email = mEmail.getText().toString().trim(); //get email from EditText
        String password = mPassword.getText().toString().trim(); //get password from EditText

        //check if email is empty
        if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is required.");
            return;
        }
        //check if password is empty
        if(TextUtils.isEmpty(password)){
            mPassword.setError("Password is required.");
            return;
        }

        //authenticate the user
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //check if account is verified
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                        Toast.makeText(getApplicationContext(),"Logged in Successfully",Toast.LENGTH_SHORT).show();
                    }else{
                        //user.sendEmailVerification(); //email already sent during registration
                        Toast.makeText(getApplicationContext(), "Check ur email to verify your account", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid email/password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}