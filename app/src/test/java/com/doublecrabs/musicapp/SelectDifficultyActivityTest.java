package com.doublecrabs.musicapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SelectDifficultyActivityTest {
    SelectDifficultyActivity testActivity;

    @Before
    public void init() {
        testActivity = new SelectDifficultyActivity();
        testActivity.sPref = testActivity.getSharedPreferences("SaveData", Context.MODE_PRIVATE);
    }

    @Test
    public void saveDifficulty() {
        assertEquals(false, testActivity.saveDifficulty(0));
        assertEquals(false, testActivity.saveDifficulty(3));
        assertEquals("none", testActivity.sPref.getString("DIFFICULTY", "none"));
        assertEquals(true, testActivity.saveDifficulty(1));
        assertEquals("normal", testActivity.sPref.getString("DIFFICULTY", "none"));
        assertEquals(true, testActivity.saveDifficulty(2));
        assertEquals("hard", testActivity.sPref.getString("DIFFICULTY", "none"));
    }
}