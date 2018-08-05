package com.instigatemobile.todolist.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.instigatemobile.todolist.R;
import com.instigatemobile.todolist.activities.MainActivity;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPass;
    private Button btnSignIn;
    private TextView tvSignUp;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        findViews(view);
        setOnClickListeners();
        return view;
    }

    @Override
    public void onClick(View view) {
        int btn_id = view.getId();
        switch (btn_id) {
            case R.id.sign_up:
                openRegisterFragment();
                break;
            case R.id.btn_login:
                login(String.valueOf(etEmail.getText()), String.valueOf(etPass.getText()));
        }
    }

    private void findViews(View view) {
        etEmail = view.findViewById(R.id.input_email);
        etPass = view.findViewById(R.id.input_password);
        btnSignIn = view.findViewById(R.id.btn_login);
        tvSignUp = view.findViewById(R.id.sign_up);
    }

    private void setOnClickListeners() {
        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    public void login(String email, String password) {
        if (!isValid()) {
            loginFail();
            return;
        }
        btnSignIn.setEnabled(false);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    Objects.requireNonNull(getActivity()).finish();
                } else {
                    loginFail();
                }
            }
        });
        showProgressDialog();
    }

    private void showProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.please_wait));
        progressDialog.setMessage(getString(R.string.authentication));
        progressDialog.show();
        progressDialog.onAttachedToWindow();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 1500);
    }

    private void openRegisterFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        assert fragmentManager != null;
        fragmentManager.beginTransaction()
                .replace(R.id.login_container, registerFragment)
                .commit();
    }


    public void loginFail() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();
        btnSignIn.setEnabled(true);
    }

    public boolean isValid() {
        boolean valid = true;
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email address is not correct");
            valid = false;
        } else {
            etPass.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
            etPass.setError("between 6 and 15 alphanumeric characters");
            valid = false;
        } else {
            etPass.setError(null);
        }
        return valid;
    }
}