package com.doublecrabs.musiclib.listener;



public interface OnLoadAudioListener {
  void loadPianoAudioStart();

  void loadPianoAudioFinish();

  void loadPianoAudioError(Exception e);

  void loadPianoAudioProgress(int progress);
}
