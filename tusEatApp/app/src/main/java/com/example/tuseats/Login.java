package com.example.tuseats;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText email_login, password_login;
    private TextView signup_link;
    private Button btn_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email_login = findViewById(R.id.email_login_edit_text);
        password_login = findViewById(R.id.password_login_edit_text);
        btn_login = findViewById(R.id.button_login);
        signup_link = findViewById(R.id.signup_link);

        // Set on Click Listener on Sign-in button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
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
                Intent intent_home = new Intent(Login.this, MainActivity.class);
                startActivity(intent_home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loginUserAccount() {
        // Take the value of two edit texts in Strings
        String email, password;
        email = email_login.getText().toString();
        password = password_login.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            email_login.setError("Please enter email!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            password_login.setError("Please enter password!");
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(
                            @NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // if sign-in is successful
                            // intent to home activity
                            Intent intent
                                    = new Intent(Login.this,
                                    MainActivity.class);
                            startActivity(intent);
                        } else {

                            // sign-in failed
                            Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
}