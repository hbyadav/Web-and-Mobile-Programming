package com.vijaya.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
                                                    // retrieve items from intent to update text fields with current info
        String fNameVal = getIntent().getStringExtra("fName");
        String lNameVal = getIntent().getStringExtra("lName");
        String jDescVal = getIntent().getStringExtra("jDesc");
        String dobVal = getIntent().getStringExtra("dob");

        EditText fName = (EditText) findViewById(R.id.firstnameEditText);
        EditText lName = (EditText) findViewById(R.id.lastnameEditText);
        EditText jDesc = (EditText) findViewById(R.id.jobDescEditText);
        EditText dob = (EditText) findViewById(R.id.dobEditText);
                                                    // update text fields with current values
        fName.setText(fNameVal);
        lName.setText(lNameVal);
        jDesc.setText(jDescVal);
        dob.setText(dobVal);

        Log.i("UpdateActivity", "Items loaded");

        Button updateBtn = findViewById(R.id.update_btn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override                           // set click listener for update button
            public void onClick(View view) {
                updateRecord();
            }
        });
    }

    private void updateRecord() {
        EditText fName = (EditText) findViewById(R.id.firstnameEditText);
        EditText lName = (EditText) findViewById(R.id.lastnameEditText);
        EditText jDesc = (EditText) findViewById(R.id.jobDescEditText);
        EditText dob = (EditText) findViewById(R.id.dobEditText);
                                                                // add edited values to SQL query
        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(SampleDBContract.Employee.COLUMN_FIRSTNAME, fName.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_LASTNAME, lName.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION, jDesc.getText().toString());

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(
                    dob.getText().toString()));
            long date = calendar.getTimeInMillis();
            values.put(SampleDBContract.Employee.COLUMN_DATE_OF_BIRTH, date);

        } catch (Exception e) {
            Log.e("UpdateActivity", "Error", e);
            Toast.makeText(this, "Date is in the wrong format", Toast.LENGTH_LONG).show();
            return;
        }                                                           // update database with new values
        long newRowId = database.update(SampleDBContract.Employee.TABLE_NAME, values, SampleDBContract
                .Employee.COLUMN_FIRSTNAME + "=?", new String[] {fName.getText().toString()});

        Toast.makeText(this, "Record " + fName.getText().toString() + " updated", Toast.LENGTH_LONG).show();

        Intent toEmployee = new Intent(UpdateActivity.this, EmployeeActivity.class);
        startActivity(toEmployee);                          // redirect to Employee activity after updating
    }
}