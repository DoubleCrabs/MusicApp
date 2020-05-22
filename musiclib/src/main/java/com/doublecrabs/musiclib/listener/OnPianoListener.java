package com.doublecrabs.musiclib.listener;

import com.doublecrabs.musiclib.entity.PianoKeyVoice;


public interface OnPianoListener {
  void onPianoInitFinish();

  void onPianoClick(boolean isBlackKey, PianoKeyVoice voice, int group,
                    int positionOfGroup);
}
