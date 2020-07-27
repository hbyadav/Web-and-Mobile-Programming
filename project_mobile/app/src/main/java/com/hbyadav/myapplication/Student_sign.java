package com.hbyadav.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Student_sign extends AppCompatActivity {
    StudentbaseAdapter studentAdapter;
    Button btnCreateAccount;
    EditText editTextUserName, editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign);
        studentAdapter = new StudentbaseAdapter(this);
        studentAdapter = studentAdapter.open();
// Get Refferences of Views editTextUserName=(EditText)findViewById(R.id.editTextUserName); editTextPassword=(EditText)findViewById(R.id.editTextPassword); editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
        btnCreateAccount = (Button) findViewById(R.id.enter);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
// TODO Auto-generated method stub
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
// check if any of the fields are vaccant
                if (userName.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                }
// check if both password matches
                else if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password does not match",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Student_sign.this, Profile.class);
                    startActivity(intent);
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
}