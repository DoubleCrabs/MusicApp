package com.chengtao.pianoview.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public void continueGame(){

    }
}
