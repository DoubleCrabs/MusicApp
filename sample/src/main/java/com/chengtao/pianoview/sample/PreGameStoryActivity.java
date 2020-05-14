package com.chengtao.pianoview.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PreGameStoryActivity extends AppCompatActivity {

    SharedPreferences sPref;
    final String CURRENT_GAME = "current_game";
    final String CURRENT_LVL = "current_lvl";
    final String DIFFICULTY = "difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game_story);

        sPref = getSharedPreferences("SaveData", MODE_PRIVATE);
        String mCurrentGame = sPref.getString(CURRENT_GAME, "");

        TextView mTextView = findViewById(R.id.textPreGame);

        //TODO: в res/values/strings нужно написать текст
        switch (mCurrentGame){
            case "1":
                mTextView.setText(R.string.textGame1);
                break;
            case "2":
                mTextView.setText(R.string.textGame2);
                break;
        }
    }

    public void startGame(View view){
        sPref = getSharedPreferences("SaveData", MODE_PRIVATE);
        String mCurrentGame = sPref.getString(CURRENT_GAME, "");
        Intent intent = new Intent();
        switch (mCurrentGame){
            case "1":
                intent = new Intent(PreGameStoryActivity.this, GameOneActivity.class);
                break;
            case "2":
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
