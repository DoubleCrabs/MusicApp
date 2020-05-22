package com.doublecrabs.musiclib.listener;

public interface LoadAudioMessage {
  void sendStartMessage();

  void sendFinishMessage();

  void sendErrorMessage(Exception e);

  void sendProgressMessage(int progress);
}
