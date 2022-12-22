package com.example.tuseats.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tuseats.R;
import com.example.tuseats.activity.MainActivity;
import com.example.tuseats.activity.Registration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private EditText email_login, password_login;
    private TextView signup_link;
    private Button btn_login;
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        email_login = view.findViewById(R.id.email_login_edit_text);
        password_login = view.findViewById(R.id.password_login_edit_text);
        btn_login = view.findViewById(R.id.button_login);
        signup_link = view.findViewById(R.id.signup_link);

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
                Intent intent = new Intent(getActivity(), Registration.class);
                startActivity(intent);
            }
        });

        return view;
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
                            Toast.makeText(getActivity().getApplicationContext(),
                                            "Login successful!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // if sign-in is successful
                            // intent to home activity
                            Intent intent
                                    = new Intent(getActivity(),
                                    MainActivity.class);
                            startActivity(intent);
                        } else {

                            // sign-in failed
                            Toast.makeText(getActivity().getApplicationContext(),
                                            "Error occurred. Please try again! ",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
}