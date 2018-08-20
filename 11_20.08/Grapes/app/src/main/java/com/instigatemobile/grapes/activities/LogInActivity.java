package com.instigatemobile.grapes.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.util.Util;

import static android.view.View.VISIBLE;

public class LogInActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setBackgroundImage();
        mPrefs = PreferenceManager.getDefaultSharedPreferences((getApplicationContext()));
        Util.mNickname = mPrefs.getString(Util.NICKNAME, null);
        if (Util.mNickname != null) {
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            finish();
        } else {
            click();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        ImageView image = findViewById(R.id.image);

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            image.setImageResource(R.drawable.horizon);
        } else {
            image.setImageResource(R.drawable.vertical);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public static boolean isValid(String str, int limit) {
        if (str.length() > 15 || str.length() <= limit) {
            return false;
        }
        for (int i = 0; i < str.length(); ++i) {
            if (!((str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') ||
                    (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') ||
                    (str.charAt(i) >= '0' && str.charAt(i) <= '9'))) {

                return false;
            }
        }
        return true;
    }


    private void click() {
        final EditText etNickname = findViewById(R.id.nickname);
        final Button submit = findViewById(R.id.submit);
        submit.setEnabled(false);
        clickListener(etNickname, submit);
        submitListener(etNickname, submit);
    }

    private void clickListener(final EditText etNickname, final Button submit) {
        etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (!isValid(String.valueOf(charSequence), 0)) {
                    Toast.makeText(LogInActivity.this, "Invalid etNickname, use only numbers and letters", Toast.LENGTH_LONG).show();
                    submit.setEnabled(false);
                } else if (isValid(String.valueOf(etNickname.getText()), 3)) {
                    submit.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });
    }

    private void submitListener(final EditText etNickname, final Button submit) {
        submit.setOnClickListener(
                new Button.OnClickListener() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onClick(View view) {
                        final String name = etNickname.getText().toString();
                        Util.mNickname = name;
                        mPrefs.edit().putString(Util.NICKNAME, name).apply();
                        final ProgressBar progress = findViewById(R.id.progress);
                        if (isValid(name, 3)) {
                            submit.setEnabled(false);
                            etNickname.setEnabled(false);
                            progress.setVisibility(VISIBLE);
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            startActivity(new Intent(LogInActivity.this,
                                                    MainActivity.class));
                                            finish();
                                        }
                                    },
                                    1500);
                            ;
                        } else {
                            submit.setEnabled(false);
                        }
                    }
                }
        );
    }

    private void setBackgroundImage() {
        final ImageView image = findViewById(R.id.image);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            image.setImageResource(R.drawable.horizon);
        } else {
            image.setImageResource(R.drawable.vertical);
        }
    }


}


