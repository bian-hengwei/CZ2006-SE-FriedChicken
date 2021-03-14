package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void disable(View v) {
        v.setEnabled(false); // disable button
        Log.d("Success", "Button disabled"); // print log message

        Button b = (Button) v; // cast v to a button type
        b.setText("Disabled"); // change button text to disabled
    }

    public void handleText(View v) {
        EditText email = findViewById(R.id.emailInput); // create email input as a EditText using its id. Get the type of data (EditText) by hovering above the item in the component tree
        String emailString = email.getText().toString(); // get the input and convert it to a string
        EditText password = findViewById(R.id.passwordInput);
        String passwordString = password.getText().toString();

        Log.d("email", emailString); // print in log for debugging
        Log.d("password", passwordString);

        ((TextView)findViewById(R.id.displayEmail)).setText(emailString); // display the email on in a empty text view above welcome message

        Toast.makeText(this, "Logging in", Toast.LENGTH_LONG).show(); // shows an alert message that appears for a short time. "this" means this activity, Toast.LENGTH_LONG means the duration message is shown is long
    }
}