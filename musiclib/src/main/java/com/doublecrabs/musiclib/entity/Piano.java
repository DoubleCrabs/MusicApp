package com.doublecrabs.musiclib.entity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

public final class Piano {
  public static final int PIANO_COUNT = 25;
  private volatile boolean mHasInit;
  private List<PianoKey[]> mBlackPianoKeys = new ArrayList<>(8);
  private List<PianoKey[]> mWhitePianoKeys = new ArrayList<>(9);
  private int mBlackKeyWidth;
  private int mHalfBlackKeyWhite;
  private int mBlackKeyHeight;
  private int mWhiteKeyWidth;
  private int mWhiteKeyHeight;
  private int mPianoWith = 100;
  private float mScale;
  @NonNull
  private Context mContext;
  @DrawableRes
  private int mPianoBlackKeyResourceId;
  @DrawableRes
  private int mPianoWhiteKeyResourceId;
  private int mMaxPianoWhiteKeyHeight;

  public Piano(
      @NonNull Context context,
      @DrawableRes int pianoBlackKeyResourceId,
      @DrawableRes int pianoWhiteKeyResourceId) {
    mContext = context;
    mPianoBlackKeyResourceId = pianoBlackKeyResourceId;
    mPianoWhiteKeyResourceId = pianoWhiteKeyResourceId;
  }

  public List<PianoKey[]> getWhitePianoKeys() {
    return mWhitePianoKeys;
  }

  public List<PianoKey[]> getBlackPianoKeys() {
    return mBlackPianoKeys;
  }

  public int getPianoWith() {
    return mPianoWith;
  }


  public void initPiano(int maxPianoWhiteKeyHeight) {
    if (validateInit(maxPianoWhiteKeyHeight)) {
      return;
    }
    initPiano();
    mHasInit = true;
  }


  public boolean hasInit() {
    return mHasInit;
  }

  @SuppressWarnings("ConstantConditions")
  private boolean validateInit(int maxPianoWhiteKeyHeight) {
    if (maxPianoWhiteKeyHeight <= 0) {
      return false;
    }
    mHasInit = mMaxPianoWhiteKeyHeight == maxPianoWhiteKeyHeight;
    if (mHasInit) {
      return true;
    }
    mMaxPianoWhiteKeyHeight = maxPianoWhiteKeyHeight;
    mWhiteKeyHeight = maxPianoWhiteKeyHeight;
    Drawable whiteKey = ContextCompat.getDrawable(mContext, mPianoWhiteKeyResourceId);
    Drawable blackKey = ContextCompat.getDrawable(mContext, mPianoBlackKeyResourceId);
    mScale = (float) mWhiteKeyHeight / whiteKey.getIntrinsicHeight();
    mWhiteKeyWidth = (int) (mScale * whiteKey.getIntrinsicWidth());
    mBlackKeyHeight = (int) (mScale * blackKey.getIntrinsicHeight());
    mBlackKeyWidth = (int) (mScale * blackKey.getIntrinsicWidth());
    mHalfBlackKeyWhite = mBlackKeyWidth >> 1;
    return false;
  }

  @SuppressWarnings("ConstantConditions")
  private void initPiano() {
    mBlackPianoKeys.clear();
    mWhitePianoKeys.clear();
    mPianoWith = 0;
    //
    for (int i = 4; i < 6; i++) {
      PianoKey[] keys = new PianoKey[i == 0 ? 1 : 5];
      for (int j = 0; j < keys.length; j++) {
        keys[j] = new PianoKey();
        Rect[] areaOfKey = new Rect[1];
        keys[j].setIsBlackKey(true);
        keys[j].setGroup(i);
        keys[j].setPositionOfGroup(j);
        keys[j].setVoiceId(getVoiceFromResources("b" + i + j));
        keys[j].setIsPressed(false);
        keys[j].setKeyDrawable(new ScaleDrawable(
            ContextCompat.getDrawable(mContext, mPianoBlackKeyResourceId),
            Gravity.NO_GRAVITY,
            mScale,
            mScale
        ).getDrawable());
        setBlackKeyDrawableBounds(i, j, keys[j].getKeyDrawable());
        areaOfKey[0] = keys[j].getKeyDrawable().getBounds();
        keys[j].setAreaOfKey(areaOfKey);
        if (i == 0) {
          keys[j].setPianoKeyVoice(PianoKeyVoice.LA);
          break;
        }
        switch (j) {
          case 0:
            keys[j].setPianoKeyVoice(PianoKeyVoice.DO);
            break;
          case 1:
            keys[j].setPianoKeyVoice(PianoKeyVoice.RE);
            break;
          case 2:
            keys[j].setPianoKeyVoice(PianoKeyVoice.FA);
            break;
          case 3:
            keys[j].setPianoKeyVoice(PianoKeyVoice.SO);
            break;
          case 4:
            keys[j].setPianoKeyVoice(PianoKeyVoice.LA);
            break;
        }
      }
      mBlackPianoKeys.add(keys);
    }
    for (int i = 3; i < 7; i++) {
      PianoKey[] mKeys = new PianoKey[i == 0 ? 2 : i == 6 ? 1 : 7];
      for (int j = 0; j < mKeys.length; j++) {
        mKeys[j] = new PianoKey();
        mKeys[j].setIsBlackKey(false);
        mKeys[j].setGroup(i);
        mKeys[j].setPositionOfGroup(j);
        mKeys[j].setVoiceId(getVoiceFromResources("w" + i + j));
        mKeys[j].setIsPressed(false);
        mKeys[j].setKeyDrawable(new ScaleDrawable(
            ContextCompat.getDrawable(mContext, mPianoWhiteKeyResourceId),
            Gravity.NO_GRAVITY,
            mScale,
            mScale
        ).getDrawable());
        setWhiteKeyDrawableBounds(i, j - 2, mKeys[j].getKeyDrawable());
        mPianoWith += mWhiteKeyWidth;
        switch (j) {
            case 0:
              mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i - 3, j, BlackKeyPosition.RIGHT));
              mKeys[j].setPianoKeyVoice(PianoKeyVoice.DO);
              mKeys[j].setLetterName("C" + i);
              break;
            case 1:

              mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i - 3, j, BlackKeyPosition.BOTH));
              mKeys[j].setPianoKeyVoice(PianoKeyVoice.RE);
              mKeys[j].setLetterName("D" + i);
              break;
            case 2:
              mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i - 3, j, BlackKeyPosition.LEFT));
              mKeys[j].setPianoKeyVoice(PianoKeyVoice.MI);
              mKeys[j].setLetterName("E" + i);
              break;
            case 3:
              mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i - 3, j, BlackKeyPosition.RIGHT));
              mKeys[j].setPianoKeyVoice(PianoKeyVoice.FA);
              mKeys[j].setLetterName("F" + i);
              break;
            case 4:
              mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i - 3, j, BlackKeyPosition.BOTH));
              mKeys[j].setPianoKeyVoice(PianoKeyVoice.SO);
              mKeys[j].setLetterName("G" + i);
              break;
            case 5:
              mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i - 3, j, BlackKeyPosition.BOTH));
              mKeys[j].setPianoKeyVoice(PianoKeyVoice.LA);
              mKeys[j].setLetterName("A" + i);
              break;
            case 6:
              mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i - 3, j, BlackKeyPosition.LEFT));
              mKeys[j].setPianoKeyVoice(PianoKeyVoice.SI);
              mKeys[j].setLetterName("B" + i);
              break;
        }
      }
      mWhitePianoKeys.add(mKeys);
    }
  }

  private void setBlackKeyDrawableBounds(
      @IntRange(from = 0) int group,
      @IntRange(from = 0) int positionOfGroup,
      @NonNull Drawable drawable) {
    int whiteOffset = 0;
    if (group == 0) {
      whiteOffset = 5;
    }
    int blackOffset = 0;
    if (positionOfGroup == 2 || positionOfGroup == 3 || positionOfGroup == 4) {
      blackOffset = 1;
    }
    int whiteKeyOffsetCount = 7 * (group - 3) - 4 + whiteOffset + blackOffset + positionOfGroup - 2;
    int whiteKeyOffsetWidth = whiteKeyOffsetCount * mWhiteKeyWidth;
    drawable.setBounds(
        whiteKeyOffsetWidth - mHalfBlackKeyWhite,
        0,
        whiteKeyOffsetWidth + mHalfBlackKeyWhite,
        mBlackKeyHeight
    );
    Log.w("TAG", "white key bounds : " + drawable.getBounds());
  }

  private Rect[] getWhitePianoKeyArea(
      @IntRange(from = 0) int group,
      @IntRange(from = 0) int positionOfGroup,
      @NonNull BlackKeyPosition blackKeyPosition) {
    int offset = 0;
    if (group == 0) {
      offset = 5;
    }
    switch (blackKeyPosition) {
      case LEFT: {
        Rect[] left = new Rect[2];
        int whiteKeyStart = (7 * group - 7 + offset + positionOfGroup) * mWhiteKeyWidth;
        left[0] = new Rect(
            whiteKeyStart,
            mBlackKeyHeight,
            whiteKeyStart += mHalfBlackKeyWhite,
            mWhiteKeyHeight
        );
        left[1] = new Rect(
            whiteKeyStart,
            0,
            (7 * group - 6 + offset + positionOfGroup) * mWhiteKeyWidth,
            mWhiteKeyHeight
        );
        return left;
      }
      case BOTH: {
        Rect[] both = new Rect[3];
        int whiteStart = (7 * group - 7 + offset + positionOfGroup) * mWhiteKeyWidth;
        both[0] = new Rect(
            whiteStart,
            mBlackKeyHeight,
            whiteStart += mHalfBlackKeyWhite,
            mWhiteKeyHeight
        );
        both[1] = new Rect(
            whiteStart,
            0,
            whiteStart =
                (7 * group - 6 + offset + positionOfGroup) * mWhiteKeyWidth - mHalfBlackKeyWhite,
            mWhiteKeyHeight
        );
        both[2] = new Rect(
            whiteStart,
            mBlackKeyHeight,
            whiteStart + mHalfBlackKeyWhite,
            mWhiteKeyHeight
        );
        return both;
      }
      case RIGHT:
      default: {
        Rect[] right = new Rect[2];
        int whiteStart = (7 * group - 7 + offset + positionOfGroup) * mWhiteKeyWidth;
        right[0] = new Rect(
            whiteStart,
            0,
            whiteStart =
                (7 * group - 6 + offset + positionOfGroup) * mWhiteKeyWidth - mHalfBlackKeyWhite,
            mWhiteKeyHeight
        );
        right[1] = new Rect(
            whiteStart,
            mBlackKeyHeight,
            whiteStart + mHalfBlackKeyWhite,
            mWhiteKeyHeight
        );
        return right;
      }
    }
  }

  private void setWhiteKeyDrawableBounds(int group, int positionOfGroup, Drawable drawable) {
    int offset = 0;
    if (group == 0) {
      offset = 5;
    }
    drawable.setBounds((7 * (group - 3) - 5 + offset + positionOfGroup) * mWhiteKeyWidth, 0,
        (7 * (group - 3) - 4 + offset + positionOfGroup) * mWhiteKeyWidth, mWhiteKeyHeight);
  }

  private int getVoiceFromResources(String voiceName) {
    return mContext.getResources().getIdentifier(voiceName, "raw", mContext.getPackageName());
  }
}
