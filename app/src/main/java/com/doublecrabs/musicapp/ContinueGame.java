package com.doublecrabs.musicapp;

import android.content.Intent;
import android.content.SharedPreferences;

public class ContinueGame {

    private SharedPreferences sPref;
    private final String CURRENT_GAME = "current_game";

    public String loadSavedData() {
        return sPref.getString(CURRENT_GAME, "1");
    }

}
