package com.example.graphicdictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    EditText settingMinutesEditText;
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = getSharedPreferences("Account", MODE_PRIVATE);
        settingMinutesEditText = (EditText)findViewById(R.id.settingMinutesEditText);
        int minutesSetting = settings.getInt("Minutes", 15);
        settingMinutesEditText.setText(String.valueOf(minutesSetting));
    }

    public void saveMinutesSetting(View view) {
        settingMinutesEditText = (EditText)findViewById(R.id.settingMinutesEditText);
        int minutesSetting = Integer.parseInt(settingMinutesEditText.getText().toString());
        prefEditor = settings.edit();
        prefEditor.putInt ("Minutes", minutesSetting);
        prefEditor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}