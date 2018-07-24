package com.example.arman.counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_INDEX = "key1";
    private TextView tvCounter;
    private Integer count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCounter = findViewById(R.id.tv_count);
        final Button btnInc = findViewById(R.id.btn_inc);
        final Button btnDec = findViewById(R.id.btn_dec);
        btnInc.setOnClickListener(this);
        btnDec.setOnClickListener(this);
        if (savedInstanceState != null) {
            count = Integer.parseInt(savedInstanceState.getString(KEY_INDEX));
        }
        tvCounter.setText(count.toString());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_inc:
                ++count;
                break;
            case R.id.btn_dec:
                --count;
                break;
        }
        tvCounter.setText(count.toString());
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_INDEX, tvCounter.getText().toString());
    }
}