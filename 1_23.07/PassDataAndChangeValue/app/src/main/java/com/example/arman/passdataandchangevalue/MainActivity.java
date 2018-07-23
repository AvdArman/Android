package com.example.arman.passdataandchangevalue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etData;
    private Button btnSend;
    private TextView tvData;
    private Button btnCopy;
    public static final String DATA_KEY = "key1";
    private static final int DATA_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        btnCopy.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    private void findViews() {
        etData = findViewById(R.id.et_data);
        btnSend = findViewById(R.id.btn_send);
        tvData = findViewById(R.id.tv_show);
        btnCopy = findViewById(R.id.btn_copy);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        tvData.setText(data.getStringExtra(DATA_KEY));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra(DATA_KEY, etData.getText().toString());
                startActivityForResult(intent, DATA_CODE);
                break;
            case R.id.btn_copy:
                etData.setText(tvData.getText());
                break;
        }
    }
}