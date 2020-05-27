package com.doublecrabs.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PassResultActivity extends AppCompatActivity {

    int[] img = new int[]{R.id.imageViewForStar1, R.id.imageViewForStar2, R.id.imageViewForStar3};
    ContinueGame mContinueGame = new ContinueGame();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_result);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        Button mButton = findViewById(R.id.buttonNextGame);

        assert result != null;
        for (int i = 0; i < 3; i++){
            ImageView mImageView = findViewById(img[i]);
            if(i < Integer.parseInt(result)){
                mImageView.setImageResource(R.drawable.star);
            }else
                mImageView.setImageResource(R.drawable.hollow_star);
        }

        TextView mTextView = findViewById(R.id.textViewPassResult);
        switch (result){
            case "0":
                mTextView.setText(R.string.bad);
                break;
            case "1":
                mTextView.setText(R.string.not_bad);
                break;
            case "2":
                mTextView.setText(R.string.good);
                break;
            case "3":
                mTextView.setText(R.string.perfect);
                break;
        }

        mButton.setOnClickListener(this::nextGame);
    }

    public void nextGame(View view){

       String mCurrentLevel = mContinueGame.loadSavedData(view);
       mContinueGame.loadGame(view, mCurrentLevel);
    }
}
