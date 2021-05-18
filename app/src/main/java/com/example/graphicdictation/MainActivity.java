package com.example.graphicdictation;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    long startTime;
    long playTime;
    long playTimeBefore;
    boolean isActive ;
    private static final String PREFS_FILE = "Account";
    private static final String PREF_ACTIVE = "Name";
    SharedPreferences settings;
    int minutesSetting;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        minutesSetting = settings.getInt("Minutes", 15);

        //isActive = settings.getBoolean(PREF_ACTIVE, true);
        playTimeBefore = settings.getLong("Time", 0);
        isActive = true;
        setContentView( isActive
                        ? R.layout.activity_main
                        : R.layout.activity_stop );

        startTime = System.currentTimeMillis();

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                playTime = System.currentTimeMillis();

                if ( (isActive) && ((playTime - startTime + playTimeBefore)/60000 >= minutesSetting) ) {
                    Intent intent = new Intent( MainActivity.this, StopActivity.class);
                    startActivity(intent);
                    isActive = false;
                    prefEditor = settings.edit();
                    prefEditor.putBoolean(PREF_ACTIVE, isActive);
                    prefEditor.apply();
                }

                handler.postDelayed(this, 1000);
            }
        };
        runnable.run();
    }

    @Override
    protected void onStop()
    {
        prefEditor = settings.edit();
        prefEditor.putLong("Time", playTimeBefore + ( playTime - startTime ));
        prefEditor.apply();

        super.onStop();
    }

    public void openLevels(View view) {
        Intent intent = new Intent(this, LevelsActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openInstruction(View view) {
        Intent intent = new Intent(this, InstructionActivity.class);
        startActivity(intent);
    }
}