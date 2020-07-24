package com.vijaya.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private TextView mResponseTv;
    private ImageButton mSpeakBtn;
    private Speaker speaker;
    private String response, name, time;
    private final int CHECK_CODE = 0x1;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;



    private void checkTTS() {                               // checkTTS function from SMS
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("TTSFile",0);
        editor = preferences.edit();


        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mResponseTv = (TextView) findViewById(R.id.speakResponse);     // added text view to display app response
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        response = "";                                              // string to store app response
        name = preferences.getString("name", "");            // string to store user's name
        time = "";                                                  // string to store time
        checkTTS();                                                // check if TTS is available
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));       // pass parsed speech text to user input text view

                                                        // handle name extraction, if speech starts with "my name"
                    if ((result.get(0).length() > 8) && (result.get(0).substring(0, 7).equals("my name"))) {
                        name = result.get(0).substring(11);         // assign rest of string (after "my name is") to name
                        editor.putString("name", name).apply();  // add to sharedPreferences
                        Log.i("MainActivity", "Name is: " + name);
                        response = getResponse("nameRetrieved");       // display greeting to user, call fct to speak greeting
                    }
                    else {
                        response = getResponse(result.get(0));      // pass parsed speech text to response generator
                    }
                    mResponseTv.setText(response);              // assign generated response to response text view
                }
                break;
            }
            case CHECK_CODE: {                                  // added onResult check code for TTS
                if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    speaker = new Speaker(this);        // if TTS is available, initialize speaker
                    mResponseTv.setText("Hello!");             // update text view to greet user (first time)
                } else {
                    Intent install = new Intent();              // install TTS if needed
                    install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(install);
                }
                break;
            }
        }
    }

    protected String getResponse(String inputSpeech) {     // method to form response based on input
        String rString = "";
        Log.i("MainActivity", "Need response for: " + inputSpeech);
        speaker.allow(true);                        // enable speaker
        switch (inputSpeech) {
            case "hello": {                                 // hardcoded response for each given question
                speaker.speak(getString(R.string.greeting));
                rString = getString(R.string.greeting);
                break;
            }
            case "I'm not feeling good what should I do": {
                speaker.speak(getString(R.string.symptoms));
                rString = getString(R.string.symptoms);
                break;
            }
            case "thank you medical assistant": {
                speaker.speak(getString(R.string.thankYou) + " " + name + " " + getString(R.string.takeCare));
                rString = getString(R.string.thankYou) + " " + name + ". " + getString(R.string.takeCare);
                break;
            }
            case "what time is it": {
                time = getTime();
                speaker.speak(getString(R.string.time) + time);
                rString = getString(R.string.time) + " " + time + ".";
                break;
            }
            case "what medicines should I take": {
                speaker.speak(getString(R.string.fever));
                rString = getString(R.string.fever);
                break;
            }
            case "nameRetrieved": {         // special key for greeting user after assigning name
                speaker.speak("Hi " + name + "!");
                rString = "Hi " + name + "!";
                break;
            }
            default: {                      // if no matches
                rString = "Sorry, I am not sure I understand.";
                speaker.speak(rString);
                break;
            }
        }
        speaker.allow(false);
        return rString;                     // return string for updtaing response textView
    }
    protected String getTime() {            // method to get time
        SimpleDateFormat sdfDate = new SimpleDateFormat("hh:mm");
        Date now = new Date();
        String[] strDate = sdfDate.format(now).split(":");
        if (strDate[1].contains("00")) {
            strDate[1] = "o'clock";
        }
        return sdfDate.format(now);
    }

}
