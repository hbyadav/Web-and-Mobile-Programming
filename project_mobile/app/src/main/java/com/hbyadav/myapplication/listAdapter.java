package com.hbyadav.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class listAdapter extends ArrayAdapter {
    ArrayList<String> students;
    ArrayList<Boolean> attendanceList;
    Context context;
    Activity activity;
    Manage manage;

    public listAdapter(Context context, ArrayList<String> name) {
        super(context, R.layout.list_ele, name);
// TODO Auto-generated constructor stub
        this.context = context;
        this.students = name;
        attendanceList = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            attendanceList.add(new Boolean(true));
        }
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_ele, parent, false);
        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            convertView = vi.inflate(R.layout.list_ele, null);
        }
        final int pos = position;
        TextView textView = (TextView) convertView.findViewById(R.id.attendanceName);
        textView.setText(students.get(position));
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.attMarker);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceList.set(pos, cb.isChecked());
                Log.d("Attendance", students.get(position).toString() + " is absent " + attendanceList.get(position));
            }
        });
        return convertView;
    }
}
