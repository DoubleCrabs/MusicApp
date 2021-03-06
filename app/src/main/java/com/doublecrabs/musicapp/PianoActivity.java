package com.doublecrabs.musicapp;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.doublecrabs.musiclib.entity.AutoPlayEntity;
import com.doublecrabs.musiclib.entity.PianoKeyVoice;
import com.doublecrabs.musiclib.listener.OnLoadAudioListener;
import com.doublecrabs.musiclib.listener.OnPianoAutoPlayListener;
import com.doublecrabs.musiclib.listener.OnPianoListener;
import com.doublecrabs.musiclib.utils.AutoPlayUtils;
import com.doublecrabs.musiclib.view.PianoView;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

@SuppressWarnings("FieldCanBeLocal")
public final class PianoActivity extends Activity
    implements OnPianoListener, OnLoadAudioListener,
    View.OnClickListener, OnPianoAutoPlayListener {

  private static final String CONFIG_FILE_NAME = "simple_little_star_config";
  private static final boolean USE_CONFIG_FILE = true;
  private PianoView pianoView;
  private Button btnMusic;
  private final static float SEEKBAR_OFFSET_SIZE = -12;
  //
  private boolean isPlay = false;
  private ArrayList<AutoPlayEntity> litterStarList = null;
  private static final long LITTER_STAR_BREAK_SHORT_TIME = 500;
  private static final long LITTER_STAR_BREAK_LONG_TIME = 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_piano);
    //view
    hideSystemUI();
    pianoView = findViewById(R.id.pv);
    pianoView.setSoundPollMaxStream(10);
    btnMusic = findViewById(R.id.iv_music);
    //listener
    pianoView.setPianoListener(this);
    pianoView.setAutoPlayListener(this);
    pianoView.setLoadAudioListener(this);
    btnMusic.setOnClickListener(this);
    //init
    if (USE_CONFIG_FILE) {
      AssetManager assetManager = getAssets();
      try {
        litterStarList = AutoPlayUtils.getAutoPlayEntityListByCustomConfigInputStream(
            assetManager.open(CONFIG_FILE_NAME));
      } catch (IOException e) {
        Log.e("TAG", e.getMessage());
      }
    } else {
      initLitterStarList();
    }
  }

  private void hideSystemUI() {
    // Set the IMMERSIVE flag.
    // Set the content to appear under the system bars so that the content
    // doesn't resize when the system bars hide and show.
    this.getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
  }

  // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
  private void showSystemUI() {
    this.getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }


  private void initLitterStarList() {
    litterStarList = new ArrayList<>();
    litterStarList.add(
        new AutoPlayEntity(false, 4, 0, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 0, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 5, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 5, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_LONG_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 3, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 3, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 2, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 2, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 1, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 1, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 0, LITTER_STAR_BREAK_LONG_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 3, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 3, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 2, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 2, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 1, LITTER_STAR_BREAK_LONG_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 3, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 3, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 2, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 2, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 1, LITTER_STAR_BREAK_LONG_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 0, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 0, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 5, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 5, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 4, LITTER_STAR_BREAK_LONG_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 3, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 3, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 2, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 2, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 1, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 1, LITTER_STAR_BREAK_SHORT_TIME));
    litterStarList.add(
        new AutoPlayEntity(false, 4, 0, LITTER_STAR_BREAK_LONG_TIME));
  }

  @Override
  public void onPianoInitFinish() {

  }

  @Override
  public void onPianoClick(boolean isBlackKey, PianoKeyVoice voice, int group,
      int positionOfGroup) {
    Toast.makeText(getApplicationContext(), "Click "+ isBlackKey + " "+voice +" " + group, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void loadPianoAudioStart() {
    Toast.makeText(getApplicationContext(), "loadPianoMusicStart", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void loadPianoAudioFinish() {
    Toast.makeText(getApplicationContext(), "loadPianoMusicFinish", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void loadPianoAudioError(Exception e) {
    Toast.makeText(getApplicationContext(), "loadPianoMusicError", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void loadPianoAudioProgress(int progress) {
    Log.e("TAG", "progress:" + progress);
  }

  @Override
  protected void onResume() {

    if (getRequestedOrientation() != SCREEN_ORIENTATION_LANDSCAPE) {
      setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
    }
    super.onResume();
  }

  @Override
  public void onClick(View view) {
    if (!isPlay) {
      pianoView.autoPlay(litterStarList);
    }
  }

  /**
   * Dp to px
   */
  private float convertDpToPixel(float dp) {
    Resources resources = this.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
  }

  @Override
  public void onPianoAutoPlayStart() {
    Toast.makeText(this, "onPianoAutoPlayStart", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onPianoAutoPlayEnd() {
    Toast.makeText(this, "onPianoAutoPlayEnd", Toast.LENGTH_SHORT).show();
    isPlay = false;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (pianoView != null) {
      pianoView.releaseAutoPlay();
    }
  }
}