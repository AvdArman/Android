package com.instigatemobile.firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");
        final RecyclerView recyclerView = findViewById(R.id.list_data);
        final EditText editText = findViewById(R.id.et_data);
        final Button btnSave = findViewById(R.id.btn_save);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        final CustomAdapter adapter = new CustomAdapter(dataList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setSaveClickListener(myRef, editText, btnSave, adapter);
    }

    private void setSaveClickListener(final DatabaseReference myRef, final EditText editText, Button btnSave, final CustomAdapter adapter) {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = String.valueOf(editText.getText());
                if (!tmp.matches("")) {
                    myRef.setValue(String.valueOf(editText.getText()));
                }
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue(String.class);
                        dataList.add(data);
                        adapter.notifyDataSetChanged();
                        editText.setText(null);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}