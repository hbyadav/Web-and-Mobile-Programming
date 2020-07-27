package com.hbyadav.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    EditText editTextUserName,editTextid,editTextfees,editschool,edityear,editphone; Button btnCreateAccount,btnlogout;
    StudentbaseAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
// get Instance of Database Adapter
        studentAdapter=new StudentbaseAdapter(this); studentAdapter=studentAdapter.open();
// Get Refferences of Views
    editTextUserName=(EditText)findViewById(R.id.edittextname); editTextid=(EditText)findViewById(R.id.edittextid); editTextfees=(EditText)findViewById(R.id.edittextfees); edityear=(EditText)findViewById(R.id.year); editschool=(EditText)findViewById(R.id.school); editphone=(EditText)findViewById(R.id.Phone);
        btnCreateAccount=(Button)findViewById(R.id.create); btnlogout=(Button)findViewById(R.id.logout); btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                String userName=editTextUserName.getText().toString(); String id=editTextid.getText().toString();
                String fees=editTextfees.getText().toString();
                String phone=editphone.getText().toString();
                String school=editschool.getText().toString();
                String year=edityear.getText().toString();
// check if any of the fields are vaccant
                if(userName.equals("")||fees.equals("")||id.equals("")||phone.equals("")||school.equals("") ||year.equals(" "))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant",
                            Toast.LENGTH_LONG).show(); }
                else
                {
                    // Save the Data in Database
                    studentAdapter.insertdetails(id,userName,fees,phone,school,year); Toast.makeText(getApplicationContext(), "Account Successfully Created ",
                    Toast.LENGTH_LONG).show(); }
            } });
    }
    public void update(View v)
    {
        Intent intent=new Intent(this,Profile.class); startActivity(intent);
    }
    public void logout(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent); }
    @Override
    protected void onDestroy() {
// TODO Auto-generated method stub
        super.onDestroy();
        studentAdapter.close(); }
}