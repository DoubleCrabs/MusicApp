package com.doublecrabs.musicapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private final String CURRENT_GAME = "current_game";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    public void newGame(View view){
        Intent intent = new Intent(MainActivity.this, SelectDifficultyActivity.class);

        startActivity(intent);
    }

    public void continueGame(View view){
        ContinueGame mContinueGame = new ContinueGame();
        String lvl = mContinueGame.loadSavedData(view);

        mContinueGame.loadGame(view, lvl);
    }

}
