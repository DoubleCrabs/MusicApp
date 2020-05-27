package com.doublecrabs.musicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectDifficultyActivity extends AppCompatActivity {

    SharedPreferences sPref;
    SharedPreferences.Editor ed;

    final String CURRENT_GAME = "current_game";
    final String CURRENT_LVL = "current_lvl";
    final String DIFFICULTY = "difficulty";
    final String RATING = "rating";

    int difficulty = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sPref = getSharedPreferences("SaveData", MODE_PRIVATE);
        ed = sPref.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setTitle("Уровень сложности");

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonNormal:
                        difficulty = 1;
                        break;
                    case R.id.radioButtonHard:
                        difficulty = 2;
                        break;
                }
            }
        });
    }

    public void onButtonClick(View view) {
        ed.putString(CURRENT_GAME, "1");
        ed.putString(CURRENT_LVL, "1");
        ed.putInt(RATING, 0);
        ed.apply();

        if (!saveDifficulty(difficulty)) {
            Toast.makeText(this, "Выберете уровень сложности игры", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(SelectDifficultyActivity.this, PreGameStoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean saveDifficulty(int difficulty) {
        switch (difficulty) {
            case 1:
                ed.putString(DIFFICULTY, "normal");
                ed.apply();
                return true;
            case 2:
                ed.putString(DIFFICULTY, "hard");
                ed.apply();
                return true;
            default:
                return false;
        }
    }
}
