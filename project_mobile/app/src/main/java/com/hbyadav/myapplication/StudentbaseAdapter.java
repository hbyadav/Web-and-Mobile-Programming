package com.hbyadav.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentbaseAdapter {
    public static final String DATABASE_NAME = "login.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "LOGIN";
    public static final String TABLE_DETAILS = "SIGN";
    public static final String TABLE_ATT = "ATTENDENCE";
    public static final String TABLE_SCHED = "SCHEDULE";
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
// SQL Statement to create a new database.
    public static final String DATABASE_CREATE = "create table " + TABLE_NAME +
            "( " + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "USERNAME text,FEES text,SCHOOL text,YEAR text,PHONE text);";
    public static final String DATABASE_SIGN = "create table " + TABLE_DETAILS +
            "( " + "ID" + " integer primary key autoincrement," + "USERNAME text, PASSWORD text); ";
    public static final String DATABASE_ATT = "create table " + TABLE_ATT + "( " + "USERNAME text,isPresent int);";
    public static final String DATABASE_SCHED = "create table " + TABLE_SCHED + "(" + "USERNAME text,COURSE text,subject text,hour text, day_week text);";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private Helper dbHelper;

    public StudentbaseAdapter(Context _context) {
        context = _context;
        dbHelper = new Helper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public StudentbaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertEntry(String userName, String password) {
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();

        // Assign values for each row. newValues.put("ID", id);
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD", password);
        // Insert the row into your table
        db.insert(TABLE_DETAILS, null, newValues);
        Toast.makeText(context, "Reminder Is Successfully Saved",
                Toast.LENGTH_LONG).show();
    }

    public void Attendence(String Username, int ispresent) {
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", Username);
        contentValues.put("isPresent", ispresent);
        db.insert(TABLE_ATT, null, contentValues);
    }

    public void Schedule(String course, String subject, String hour, String week, String Username) {
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", Username);
        contentValues.put("COURSE", course);
        contentValues.put("subject", subject);
        contentValues.put("hour", hour);
        contentValues.put("day_week", week);
        db.insert(TABLE_SCHED,null,contentValues);
    }

    public void insertdetails(String id, String userName, String fees, String phone, String school, String year) {
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues(); // Assign values for each row.
        int _id = Integer.parseInt(id);
        newValues.put("ID", _id);
        newValues.put("USERNAME", userName);
        newValues.put("FEES", fees);
        newValues.put("SCHOOL", school);
        newValues.put("YEAR", year);
        newValues.put("PHONE", phone);
        // Insert the row into your table
        db.insert(TABLE_NAME, null, newValues);
        Toast.makeText(context, "Reminder Is Successfully Saved",
        Toast.LENGTH_LONG).show();
    }

    public String getpassword(String username) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DETAILS, null, "USERNAME=?", new String[]{username}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        return password;
    }

    public Cursor getSinlgeEntry(String username) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from LOGIN where USERNAME = ?", new String[]{username});
        cursor.moveToFirst();

        return cursor;
    }

    public ArrayList<String> getAllStringValues() {
        ArrayList<String> yourStringValues = new ArrayList<String>();
        Cursor result = db.query(true, TABLE_NAME,
                new String[]{"USERNAME"}, null, null, null, null, null, null);
        if (result.moveToFirst()) {
            do {
                yourStringValues.add(result.getString(result.getColumnIndex("USERNAME")));
            } while (result.moveToNext());
        } else {
            return null;
        }
        return yourStringValues;
    }

    public Cursor schedule(String username) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from SCHEDULE where USERNAME = ?", new String[]{username});
        return cursor;
    }

    public Cursor attendence(String query) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void close() {
        db.close();
    }
}
