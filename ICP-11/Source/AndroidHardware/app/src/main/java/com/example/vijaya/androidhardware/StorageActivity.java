package com.example.vijaya.androidhardware;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class StorageActivity extends AppCompatActivity {
    EditText txt_content;
    EditText contenttoDisplay;
    String FILENAME = "MyAppStorage1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        txt_content = (EditText) findViewById(R.id.id_txt_mycontent);
        contenttoDisplay = (EditText) findViewById(R.id.id_txt_display);
    }

    public void saveTofile(View v) throws IOException {
        hideKeyboard(this);         // hide keyboard on button click
        String saveText = txt_content.getText().toString();     // get text from text field
        FileOutputStream out = openFileOutput(FILENAME, Context.MODE_APPEND);   // get output file
        out.write(saveText.getBytes());     // write to file
        out.write('\n');                // add new line
        out.close();                       // close file

    }

    public void retrieveFromFile(View v) throws IOException {
        hideKeyboard(this);         // hide keyboard
        FileInputStream in = openFileInput(FILENAME);   // get file to read from
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        StringBuilder stringBuilder = new StringBuilder();         // build string from file stream
        BufferedReader reader = new BufferedReader(inputStreamReader);  // using reader
        String line = "";
        while ((line = reader.readLine()) != null) {    // read each line from file
            stringBuilder.append(line).append(" ");    // add space between lines
        }
        in.close();                                     // close file
        String content = stringBuilder.toString();      // convert to string

        contenttoDisplay.setText(content);              // and assign to EditText View
        contenttoDisplay.setVisibility(View.VISIBLE);   // set it visible

    }

    public static void hideKeyboard(Activity activity) {  //this method is used to hide the
        InputMethodManager manager = (InputMethodManager) activity // keyboard on Save button click
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();          //gets current active view for context
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);  // hide keyboard command
    }
}
