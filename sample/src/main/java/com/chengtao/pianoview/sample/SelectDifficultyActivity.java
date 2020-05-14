package com.chengtao.pianoview.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectDifficultyActivity extends AppCompatActivity {

    SharedPreferences sPref;
    final String CURRENT_GAME = "current_game";
    final String CURRENT_LVL = "current_lvl";
    final String DIFFICULTY = "difficulty";

    int difficulty = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);
        setTitle("Уровень сложности");

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
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

    public void saveDifficulty(View view) {
        sPref = getSharedPreferences("SaveData", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CURRENT_GAME, "1");
        ed.putString(CURRENT_LVL, "1");
        ed.commit();
        if(difficulty == 0) {
            Toast.makeText(this, "Выберете уровень сложности игры", Toast.LENGTH_SHORT).show();
            return;
        }else
        if(difficulty == 1)
            ed.putString(DIFFICULTY, "normal");
        else
            ed.putString(DIFFICULTY, "hard");
        ed.apply();
        Intent intent = new Intent(SelectDifficultyActivity.this, PreGameStoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
