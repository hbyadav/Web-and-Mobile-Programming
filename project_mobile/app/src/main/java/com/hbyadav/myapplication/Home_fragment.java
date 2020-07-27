package com.hbyadav.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Home_fragment extends Fragment {

    StudentbaseAdapter studentAdapter;
    Student_display student = new Student_display();
    public String username = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_student_home, container, false);

        studentAdapter = new StudentbaseAdapter(getActivity());
        studentAdapter = studentAdapter.open();
        //EditText editText=(EditText)container.findViewById(R.id.editname);
        // String username=editText.getText().toString();

        username = student.Username();
        if (username != null) {
            Cursor _cursor = studentAdapter.getSinlgeEntry(username);
            int id = _cursor.getInt(0);
            String fees = _cursor.getString(2);
            String School = _cursor.getString(3);
            String Session = _cursor.getString(4);
            String phone = _cursor.getString(5);
            TextView textView = (TextView) view.findViewById(R.id.name);
            textView.setText(username);
            TextView textView1 = (TextView) view.findViewById(R.id.school);
            textView1.setText(School);
            TextView textView2 = (TextView) view.findViewById(R.id.year);
            textView2.setText(Session);
            TextView textView3 = (TextView) view.findViewById(R.id.Phone);
            textView3.setText(phone);
            TextView textView4 = (TextView) view.findViewById(R.id.Fees);
            textView4.setText(fees);
        }
        return view;
    }
}
