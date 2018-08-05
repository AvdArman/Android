package com.instigatemobile.todolist.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.instigatemobile.todolist.R;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private final String M_TAG = "RegisterTAG";
    private EditText mEtEmail;
    private EditText mEtPass;
    private EditText mEtConfirmPass;


    public RegisterFragment() {
    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        signUp(view);
        return view;
    }

    private void signUp(final View view) {
        mAuth = FirebaseAuth.getInstance();
        final Button signUp = view.findViewById(R.id.btn_register);
        final Button backSignIn = view.findViewById(R.id.btn_back_sign_in);
        mEtEmail = view.findViewById(R.id.register_input_email);
        mEtPass = view.findViewById(R.id.register_input_password);
        mEtConfirmPass = view.findViewById(R.id.register_confirm_password);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String emailAddr = String.valueOf(mEtEmail.getText());
                final String password = String.valueOf(mEtPass.getText());
                final String confirmPassword = String.valueOf(mEtConfirmPass.getText());
                register(emailAddr, password, confirmPassword);
            }
        });
        backSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                assert fragmentManager != null;
                fragmentManager.beginTransaction()
                        .replace(R.id.login_container, loginFragment)
                        .commit();
            }
        });
    }

    private void register(final String emailAddr, final String password, final String confirmPassword) {
        if (!isValid(emailAddr, password, confirmPassword)) {
            Toast.makeText(getContext(), "Register failed", Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(emailAddr, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(M_TAG, "createUserWithEmail:success");
                                showProgressDialog();
                                goLoginPage();
                            } else {
                                Log.w(M_TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
    }

    private void showProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.please_wait));
        progressDialog.setMessage(getString(R.string.authentication));
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    private void goLoginPage() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.login_container, loginFragment)
                .addToBackStack(null)
                .commit();
    }

    public boolean isValid(final String email, final String password, final String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            mEtConfirmPass.setError("Passwords are different");
            return false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEtEmail.setError("Email address is not correct");
            return false;
        } else {
            mEtPass.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
            mEtPass.setError("between 6 and 15 alphanumeric characters");
            return false;
        } else {
            mEtPass.setError(null);
        }
        return true;
    }
}
