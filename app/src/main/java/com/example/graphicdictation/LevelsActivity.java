package com.example.graphicdictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
    }

    public void startMushroom(View view) {
        String[] pictureName = getResources().getStringArray(R.array.mushroom);
        Intent intent = new Intent(this, DrawActivity.class);
        intent.putExtra("pictureName", pictureName);
        startActivity(intent);
    }
    public void startTurtle(View view) {
        String[] pictureName = getResources().getStringArray(R.array.turtle);
        Intent intent = new Intent(this, DrawActivity.class);
        intent.putExtra("pictureName", pictureName);
        startActivity(intent);
    }

    public void startElephant(View view) {
        String[] pictureName = getResources().getStringArray(R.array.elephant);
        Intent intent = new Intent(this, DrawActivity.class);
        intent.putExtra("pictureName", pictureName);
        startActivity(intent);
    }

    public void startShip(View view) {
        String[] pictureName = getResources().getStringArray(R.array.ship);
        Intent intent = new Intent(this, DrawActivity.class);
        intent.putExtra("pictureName", pictureName);
        startActivity(intent);
    }
}