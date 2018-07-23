package com.example.arman.openwebpage;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl;
    private Button btnOpen;
    private CheckBox chkGoogle;
    private CheckBox chkYandex;
    private String searchEngine = "https://www.google.com/#q="; //have issue with this -> getString(R.string.google_link);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUrl = findViewById(R.id.et_url);
        btnOpen = findViewById(R.id.btn_open);
        chkGoogle = findViewById(R.id.chk_google);
        chkYandex = findViewById(R.id.chk_yandex);
        chkGoogle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chkYandex.setChecked(false);
                    searchEngine = getString(R.string.google_link);
                }
            }
        });
        chkYandex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chkGoogle.setChecked(false);
                searchEngine = getString(R.string.yandex_link);
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = etUrl.getText().toString();
                Intent browserIntent;
                if (isValidUrl(url)) {
                    url = "http://" + url;
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                } else {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchEngine + url));
                }
                startActivity(browserIntent);
            }
        });
    }

    private boolean isValidUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Pattern pattern = Patterns.WEB_URL;
        Matcher matcher = pattern.matcher(url.toLowerCase());
        return matcher.matches();
    }
}