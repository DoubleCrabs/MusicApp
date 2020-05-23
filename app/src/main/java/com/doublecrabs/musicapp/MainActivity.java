package com.doublecrabs.musicapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    public void newGame(View iew){
        Intent intent = new Intent(MainActivity.this, SelectDifficultyActivity.class);

        startActivity(intent);
    }

    public void continueGame(View view){
//        ContinueGame mContinueGame = new ContinueGame();
//        String lvl = mContinueGame.loadSavedData();
        Toast.makeText(this, "Не сделано", Toast.LENGTH_SHORT).show();
//        switch (lvl){
//            case "1":
//                Intent intent = new Intent();
//                intent.setClass(this, GameTestActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//        }
    }

}
