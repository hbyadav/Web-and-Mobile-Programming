package com.hbyadav.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Student_display extends AppCompatActivity {
    private static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_display);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("message");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SimpleFragmentAdapter adapter = new SimpleFragmentAdapter(this, getSupportFragmentManager());
// Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.View_Profile:
                Intent intent = new Intent(this, Student_display.class);
                startActivity(intent);
                return true;
            case R.id.Edit_Profile:
                Intent intent1 = new Intent(this, Profile.class);
                startActivity(intent1);
                return true;
            case R.id.logout:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
// Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String Username() {
        return name;
    }
}