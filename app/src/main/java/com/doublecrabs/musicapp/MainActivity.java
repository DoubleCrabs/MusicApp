package com.doublecrabs.musicapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void newGame(View iew){
        Intent intent = new Intent(MainActivity.this, SelectDifficultyActivity.class);

        startActivity(intent);
    }

    public void continueGame(View view) {
    }
}
