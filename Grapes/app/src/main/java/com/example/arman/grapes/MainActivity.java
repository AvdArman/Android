package com.example.arman.grapes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<PreviewObjects> previewObjectsList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setObjectList();
        CustomPagerAdapter adapter = new CustomPagerAdapter(previewObjectsList);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        Button btn = findViewById(R.id.btn_skip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Bla bla bla", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setObjectList() {
        previewObjectsList.add(new PreviewObjects(R.drawable.grapes_logo,  getString(R.string.first_page_text), "Grapes"));
        previewObjectsList.add(new PreviewObjects(R.drawable.second_page_logo, getString(R.string.second_page_text), null));
        previewObjectsList.add(new PreviewObjects(R.drawable.third_page_logo, getString(R.string.third_page_text), null));
    }
}
