package com.doublecrabs.musicapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

class ContinueGame {

    private SharedPreferences sPref;

    private final String CURRENT_GAME = "current_game";

    String loadSavedData(View view) {
        Context mContext = view.getContext();
        sPref = mContext.getSharedPreferences("SaveData", MODE_PRIVATE);
        return sPref.getString(CURRENT_GAME, "0");
    }

    void loadGame(View view, String level) {
        Context mContext = view.getContext();
        Intent intent = new Intent();
        switch (level){
            case "0":
                Toast.makeText(mContext, "Сохранения не найдены", Toast.LENGTH_SHORT).show();
                return;
            case "1":
                intent.setClass(mContext, GameTestActivity.class);
                break;
            case "2":
                intent.setClass(mContext, PianoActivity.class);
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
