package com.example.tuseats.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tuseats.R;
import com.example.tuseats.activity.Login;
import com.example.tuseats.utils.KeyboardUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private EditText email_register, password_register;
    private Button button_register;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        email_register = view.findViewById(R.id.email_register_edit_text);
        password_register = view.findViewById(R.id.password_register_edit_text);
        button_register = view.findViewById(R.id.button_register);
        progressbar = view.findViewById(R.id.progressbar);

        // Set on Click Listener on Registration button
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(getActivity());
                registerNewUser();
            }
        });

        return view;
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
                    Toast.makeText(getContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                            .show();

                    // if the user created intent to login activity
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                } else {
                    Log.e("Error", task.getException().toString());
                    // Registration failed
                    Toast.makeText(getContext(),
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