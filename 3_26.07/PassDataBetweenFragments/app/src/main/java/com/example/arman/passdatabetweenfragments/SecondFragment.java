package com.example.arman.passdatabetweenfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SecondFragment extends Fragment {

    private TextView textView;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        textView = view.findViewById(R.id.text_view);
        return view;
    }

    public void updateTextField(String data) {
        if (data != null) {
            textView.setText(data);
        }
    }
}