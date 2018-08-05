package com.instigatemobile.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.instigatemobile.todolist.R;
import com.instigatemobile.todolist.modules.SliderObjects;
import com.instigatemobile.todolist.adapters.SliderPagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class SliderActivity extends AppCompatActivity {

    private List<SliderObjects> sliderObjectsList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        setObjectList();
        final SliderPagerAdapter adapter = new SliderPagerAdapter(sliderObjectsList);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        final TabLayout tabLayout = findViewById(R.id.slider_tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        final Button btnSkip = findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SliderActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public void setObjectList() {
        sliderObjectsList.add(new SliderObjects(R.drawable.to_do_icon, getString(R.string.first_page_text)));
        sliderObjectsList.add(new SliderObjects(R.drawable.to_do_icon, getString(R.string.second_page_text)));
        sliderObjectsList.add(new SliderObjects(R.drawable.to_do_icon, getString(R.string.third_page_text)));
    }
}