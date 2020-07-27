package com.hbyadav.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Manage extends AppCompatActivity {
    StudentbaseAdapter studentAdapter;
    Helper help;
    listAdapter listadapter;
    ArrayList<String> students;
    ListView listView;
    ArrayList<Boolean> attendanceList;
    public static String time, period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        time = getIntent().getStringExtra("DATE");
        period = getIntent().getStringExtra("PERIOD");
        studentAdapter = new StudentbaseAdapter(this);
        studentAdapter = studentAdapter.open();
        listView = (ListView) findViewById(R.id.list_item);
    }

    public void load(View v) {
        students = studentAdapter.getAllStringValues();
        listAdapter listadapter = new listAdapter(this, students);
        listView.setAdapter(listadapter);
    }

    public void save(View v) {
        String[] student = new String[students.size()];
        student = students.toArray(student);
        attendanceList = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            attendanceList.add(new Boolean(true));
        }
    }
        /*for(
    int i = 0; i<students.size(); i++)

    {
        int sts = 1;
        if (attendanceList.get(i))
            sts = 1;
        else sts = 0;
        studentAdapter.Attendence(student[i], sts);
    }
  Toast.makeText(Manage.this,"Saving",Toast.LENGTH_SHORT).show();
    String x="http://lms.bml.edu.in/";
    Intent intent=new Intent(Intent.ACTION_VIEW); intent.setData(Uri.parse(x));
    startActivity(intent);*/
}