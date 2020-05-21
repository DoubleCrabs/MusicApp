package com.doublecrabs.musicapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class GameOneActivity extends AppCompatActivity {

    String answer;
    int result = 0;
    int rating = 0;
    int lvl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one);
        setTitle("Мини-игра: Тест");

        answer = showQustion();
        Button mButton = (Button)findViewById(R.id.buttonSubmit);
        mButton.setOnClickListener(v -> checkAnswer(answer));
    }

    public String showQustion() {
        String jsonFile = loadTest();

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonFile);
            JSONArray jsonArray = jsonObject.getJSONArray("tests");

            TextView mTextView = findViewById(R.id.question);
            TextView mAnswer1 = findViewById(R.id.answer1);
            TextView mAnswer2 = findViewById(R.id.answer2);
            TextView mAnswer3 = findViewById(R.id.answer3);
            TextView mAnswer4 = findViewById(R.id.answer4);

            int i = new Random().nextInt(jsonArray.length());
            JSONObject jsObject = jsonArray.getJSONObject(i);

            String question = jsObject.getString("question");
            mTextView.setText(question);

            String[] answers;
            answers = new String[4];

            String answer1 = jsObject.getString("answer1");
            answers[0] = answer1;

            String answer2 = jsObject.getString("answer2");
            answers[1] = answer2;

            String answer3 = jsObject.getString("answer3");
            answers[2] = answer3;

            String answer4 = jsObject.getString("answer4");
            answers[3] = answer4;

            Collections.shuffle(Arrays.asList(answers));
            mAnswer1.setText(answers[0]);
            mAnswer2.setText(answers[1]);
            mAnswer3.setText(answers[2]);
            mAnswer4.setText(answers[3]);
            return answer1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String loadTest() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("listOfTests.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void checkAnswer(String answer){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup.clearCheck();

        int selectedId = radioGroup.getCheckedRadioButtonId();

        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        if(selectedId == -1) {
            Toast.makeText(this, "Выберете один вариант ответа", Toast.LENGTH_SHORT).show();
        }else{
            if(radioButton.getText() == answer)
                rating += 1;
            lvl++;
            if(lvl < 3){
                answer = showQustion();
            }else{
                Intent intent = new Intent(this, PianoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
