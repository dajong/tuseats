package com.example.tuseats;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tuseats.utils.KeyboardUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    private EditText email_register, password_register;
    private Button button_register;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        email_register = findViewById(R.id.email_register_edit_text);
        password_register = findViewById(R.id.password_register_edit_text);
        button_register = findViewById(R.id.button_register);
        progressbar = findViewById(R.id.progressbar);

        // Set on Click Listener on Registration button
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(Registration.this);
                registerNewUser();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setTitle("TUSeats");
        getSupportActionBar().setIcon(R.drawable.ic_baseline_food_bank_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent_home = new Intent(Registration.this, MainActivity.class);
                startActivity(intent_home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void registerNewUser() {
        // Take the value of two edit texts in Strings
        String email, password;
        email = email_register.getText().toString();
        password = password_register.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            email_register.setError("Please enter your email!");
            return;
        } else if (TextUtils.isEmpty(password)) {
            password_register.setError("Please enter your password!");
            return;
        } else if (password.length() < 8) {
            password_register.setError("Password must be at least 8 character!");
            return;
        }

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                            .show();

                    // if the user created intent to login activity
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                } else {
                    Log.e("Error", task.getException().toString());
                    // Registration failed
                    Toast.makeText(getApplicationContext(),
                            "Registration failed! Please try again later",
                            Toast.LENGTH_LONG).show();
                }
                // hide the progress bar
                progressbar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("exception", e.getMessage());
            }
        });
    }
}