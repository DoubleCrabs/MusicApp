package com.doublecrabs.musiclib.entity;

import androidx.annotation.IntRange;

import com.google.gson.annotations.SerializedName;


public final class AutoPlayEntity {

  private boolean mIsBlackKey;
  private int mGroup;
  private int mPosition;
  @SerializedName("break")
  private long mCurrentBreakTime;

  public AutoPlayEntity() {
  }

  public AutoPlayEntity(
      boolean isBlackKey,
      @IntRange(from = 0) int group,
      @IntRange(from = 0) int position,
      long currentBreakTime) {
    mIsBlackKey = isBlackKey;
    mGroup = group;
    mPosition = position;
    mCurrentBreakTime = currentBreakTime;
  }

  public void setIsBlackKey(boolean isBlackKey) {
    mIsBlackKey = isBlackKey;
  }

  public void setGroup(int group) {
    mGroup = group;
  }

  public void setPosition(int position) {
    mPosition = position;
  }

  public void setCurrentBreakTime(long currentBreakTime) {
    mCurrentBreakTime = currentBreakTime;
  }

  public boolean isBlackKey() {
    return mIsBlackKey;
  }

  public int getGroup() {
    return mGroup;
  }

  public int getPosition() {
    return mPosition;
  }

  public long getCurrentBreakTime() {
    return mCurrentBreakTime;
  }
}
