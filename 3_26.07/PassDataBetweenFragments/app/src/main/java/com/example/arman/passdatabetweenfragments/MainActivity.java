package com.example.arman.passdatabetweenfragments;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener {

    private SecondFragment fragment2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        FirstFragment fragment1 = new FirstFragment();
        fragment2 = new SecondFragment();
        fm.beginTransaction().add(R.id.container_1, fragment1).commit();
        fm.beginTransaction().add(R.id.container_2, fragment2).commit();
    }

    @Override
    public void onFragmentInteraction(String data) {
        fragment2.updateTextField(data);
    }
}