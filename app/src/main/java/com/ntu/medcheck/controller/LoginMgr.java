package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.ntu.medcheck.view.HomeActivity;

public class LoginMgr {


    FirebaseAuth fAuth;

    /**
     * use firebase to verify if login is successful
     * @param password, email
     * @return true if successful
     */
    // takes in the information from LoginActivity and verifies with fAuth
    // try to use context or bundle??
    public void verifyLogin(String email, String password, Context context) {
        fAuth = FirebaseAuth.getInstance();
        System.out.println(email);
        System.out.println(password);
        System.out.println("saf sdaf asdfsdfasdfsgw2336435737151hereeeeeeeee");
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                System.out.println("success");
                //check if account is verified
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println("1");
                if(user.isEmailVerified()){
                    System.out.println("2");
                    Intent i = new Intent(context, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    System.out.println("3");
                    Toast.makeText(context.getApplicationContext(),"Logged in Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    //user.sendEmailVerification(); //email already sent during registration
                    Toast.makeText(context.getApplicationContext(), "Check ur email to verify your account", Toast.LENGTH_LONG).show();
                }
            }else {
                try {
                    System.out.println(task.getException());
                    throw task.getException();
                } catch(FirebaseAuthWeakPasswordException e) {
                    Toast.makeText(context, "Registration unsuccessful, password too weak", Toast.LENGTH_LONG).show();
                }
                catch(FirebaseAuthUserCollisionException e) {
                    Toast.makeText(context, "Registration unsuccessful, email already used", Toast.LENGTH_LONG).show();
                }
                catch(Exception e) {
                    System.out.println("Unknown Error");
                }
            }
        });
    }

}
