package com.example.arman.passdataandchangevalue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DataActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        final EditText etData = findViewById(R.id.et_modify);

        final Button btnModify = findViewById(R.id.btn_modify);
        etData.setText(getIntent().getStringExtra(MainActivity.DATA_KEY));
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.DATA_KEY, etData.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
