package com.instigatemobile.grapes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.fragments.AboutFragment;
import com.instigatemobile.grapes.fragments.ActiveNodesFragment;


public class DropdownActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown);

        final Intent intent = getIntent();
        int id = intent.getIntExtra(MainActivity.KEY_DROPDOWN, 0);

        if (id == R.id.action_about) {
            showAboutFragment();
        } else if (id == R.id.action_active_nodes) {
            showActiveNodesFragment();
        } else {
            Toast.makeText(DropdownActivity.this, "Something went wrong!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void showAboutFragment() {
        final AboutFragment aboutFragment = new AboutFragment();
        mFragmentManager.beginTransaction().add(R.id.dropdown_container, aboutFragment).commit();
    }

    public void showActiveNodesFragment() {
        final ActiveNodesFragment activeNodesFragment = new ActiveNodesFragment();
        mFragmentManager.beginTransaction().add(R.id.dropdown_container, activeNodesFragment).commit();
    }
}
