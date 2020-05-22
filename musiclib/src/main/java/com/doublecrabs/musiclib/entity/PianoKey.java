package com.doublecrabs.musiclib.entity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;


public final class PianoKey {

  private boolean mIsBlackKey;
  //[ DO, RE, MI, FA, SO, LA, SI ]
  private PianoKeyVoice mPianoKeyVoice;
  @IntRange(from = 0)
  private int mGroup;
  @IntRange(from = 0)
  private int mPositionOfGroup;
  private Drawable mKeyDrawable;
  private int mVoiceId;
  private boolean mIsPressed;
  private Rect[] mAreaOfKey;
  private String mLetterName;
  private int mFingerId = -1;

  public void setIsBlackKey(boolean isBlackKey) {
    mIsBlackKey = isBlackKey;
  }

  public void setPianoKeyVoice(@NonNull PianoKeyVoice pianoKeyVoice) {
    mPianoKeyVoice = pianoKeyVoice;
  }

  public void setGroup(int group) {
    mGroup = group;
  }

  public void setPositionOfGroup(int positionOfGroup) {
    mPositionOfGroup = positionOfGroup;
  }

  public void setKeyDrawable(@NonNull Drawable keyDrawable) {
    mKeyDrawable = keyDrawable;
  }

  public void setVoiceId(int voiceId) {
    mVoiceId = voiceId;
  }

  public void setIsPressed(boolean isPressed) {
    mIsPressed = isPressed;
  }

  public void setAreaOfKey(@NonNull Rect[] areaOfKey) {
    mAreaOfKey = areaOfKey;
  }

  public void setLetterName(@NonNull String letterName) {
    mLetterName = letterName;
  }

  public void setFingerId(int fingerId) {
    mFingerId = fingerId;
  }


  public boolean isBlackKey() {
    return mIsBlackKey;
  }

  public PianoKeyVoice getPianoKeyVoice() {
    return mPianoKeyVoice;
  }

  @IntRange(from = 0)
  public int getGroup() {
    return mGroup;
  }

  @IntRange(from = 0)
  public int getPositionOfGroup() {
    return mPositionOfGroup;
  }

  @NonNull
  public Drawable getKeyDrawable() {
    if (mKeyDrawable == null) {
      throw new NullPointerException("please call PianoKey#setKeyDrawable first !!!");
    }
    return mKeyDrawable;
  }

  public int getVoiceId() {
    return mVoiceId;
  }

  public boolean isPressed() {
    return mIsPressed;
  }

  @NonNull
  public Rect[] getAreaOfKey() {
    if (mAreaOfKey == null) {
      throw new NullPointerException("please call PianoKey#setAreaOfKey first !!!");
    }
    return mAreaOfKey;
  }

  @NonNull
  public String getLetterName() {
    if (mLetterName == null) {
      throw new NullPointerException("please call PianoKey#setLetterName first !!!");
    }
    return mLetterName;
  }

  public int getFingerId() {
    return mFingerId;
  }

  public void resetFingerId() {
    mFingerId = -1;
  }

  public boolean contain(int x, int y) {
    for (Rect rect : mAreaOfKey) {
      if (rect.contains(x, y)) {
        return true;
      }
    }
    return false;
  }
}