package com.example.graphicdictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.graphics.*;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;
import java.util.zip.Inflater;

public class DrawActivity extends AppCompatActivity {

    TextView instructionField;
    Integer instructionNumber;

    TextToSpeech tTS;

    String[] pictureName;
    int pictureNameLength;
    ImageButton nextInstructionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        instructionField = (TextView) findViewById(R.id.myTextView);
        instructionNumber = 0;
        nextInstructionBtn = (ImageButton) findViewById(R.id.nextInstructionBtn);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            pictureName = arguments.getStringArray("pictureName");
        }
        pictureNameLength = pictureName.length;
        instructionField.setText(pictureName[instructionNumber]);

        tTS=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tTS.setLanguage(new Locale("ru"));
                    tTS.setPitch(1.3f);
                    tTS.setSpeechRate(0.7f);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.settings :
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.mainMenu:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void nextInstruction(View view){
        instructionNumber++;
        if(instructionNumber < pictureNameLength) {
            instructionField.setText(pictureName[instructionNumber]);
        }
        else {
            if (instructionNumber == pictureNameLength){
                Intent intent = new Intent(this, LevelsActivity.class);
                startActivity(intent);
            }
            else {
                nextInstructionBtn.setEnabled(false);
            }
        }


    }
    public void voiceInstruction(View view){
        instructionField = (TextView) findViewById(R.id.myTextView);
        String toSpeak = instructionField.getText().toString();
        tTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void cancelLast(View view) {
    }
}