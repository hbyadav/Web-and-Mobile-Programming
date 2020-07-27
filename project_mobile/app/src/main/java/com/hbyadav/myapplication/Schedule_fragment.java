package com.hbyadav.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Schedule_fragment extends Fragment {
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> subs;
    ArrayList<String> subx;
    ArrayList<String> times;
    StudentbaseAdapter studentAdapter;
    Student_display student_display = new Student_display();
    public String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_student_schedule,
                container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_sch);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(getActivity(), Make_schedule.class);
                startActivity(launchIntent);
            }
        });
        studentAdapter = new StudentbaseAdapter(getActivity());
        studentAdapter = studentAdapter.open();
        username = student_display.Username();
        subs = new ArrayList<>();
        times = new ArrayList<>();
        subx = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.schedulerList);
        // String qu = "SELECT * FROM SCHEDULE WHERE USERNAME="+username;
        Cursor cursor = studentAdapter.schedule(username);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No Schedules Available",
                    Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                subx.add(cursor.getString(1));
                subs.add(cursor.getString(1) + "\nfor " + cursor.getString(0) + "\nat " +
                        cursor.getString(2) + " : " + cursor.getString(3));
                times.add(cursor.getString(2));
                cursor.moveToNext();
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, subs);
        listView.setAdapter(adapter);
        return view;
    }
}