package com.hbyadav.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Student extends AppCompatActivity {
    public String username;
    EditText editTextname, editTextpassword;
    StudentbaseAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        studentAdapter = new StudentbaseAdapter(this);
        studentAdapter = studentAdapter.open();
        editTextname = (EditText) findViewById(R.id.editname);
    }

    public void enter(View view) {
        // get the Refferences of views
        editTextpassword = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.enter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get The User name and Password
                username = editTextname.getText().toString();
                String password = editTextpassword.getText().toString();
                String storedPassword = studentAdapter.getpassword(username);

                if (password.equals(storedPassword)) {
                    Toast.makeText(Student.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Student.this, Student_display.class);
                    intent.putExtra("message", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(Student.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        studentAdapter.close();
    }

    public String Username() {
        username = editTextname.getText().toString();
        return username;
    }
}

