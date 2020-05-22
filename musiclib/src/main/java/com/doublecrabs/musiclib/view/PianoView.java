package com.doublecrabs.musiclib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.doublecrabs.musiclib.R;
import com.doublecrabs.musiclib.entity.AutoPlayEntity;
import com.doublecrabs.musiclib.entity.Piano;
import com.doublecrabs.musiclib.entity.PianoKey;
import com.doublecrabs.musiclib.listener.OnLoadAudioListener;
import com.doublecrabs.musiclib.listener.OnPianoAutoPlayListener;
import com.doublecrabs.musiclib.listener.OnPianoListener;
import com.doublecrabs.musiclib.utils.AudioUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static android.content.ContentValues.TAG;


public final class PianoView extends View {
  @NonNull
  private Piano mPiano;
  private CopyOnWriteArrayList<PianoKey> pressedKeys = new CopyOnWriteArrayList<>();
  private Paint paint;
  private RectF square;
  private String[] pianoColors = {
      "#FF8C00", "#FFFF00", "#00FA9A", "#00CED1", "#4169E1", "#FFB6C1",
      "#FFEBCD"
  };
  private AudioUtils utils = null;
  private Context context;
  private int layoutWidth = 0;
  private OnLoadAudioListener loadAudioListener;
  private OnPianoAutoPlayListener autoPlayListener;
  private OnPianoListener pianoListener;
  private int progress = 0;
  private boolean canPress = true;
  private boolean isAutoPlaying = false;
  private int minRange = 0;
  private int maxRange = 0;
  //
  private int maxStream;
  private Handler autoPlayHandler = new Handler(Looper.myLooper()) {
    @Override
    public void handleMessage(Message msg) {
      handleAutoPlay(msg);
    }
  };
  private static final int HANDLE_AUTO_PLAY_START = 0;
  private static final int HANDLE_AUTO_PLAY_END = 1;
  private static final int HANDLE_AUTO_PLAY_BLACK_DOWN = 2;
  private static final int HANDLE_AUTO_PLAY_WHITE_DOWN = 3;
  private static final int HANDLE_AUTO_PLAY_KEY_UP = 4;


  public PianoView(Context context) {
    this(context, null);
  }

  public PianoView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PianoView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.FILL);
    square = new RectF();
    mPiano = new Piano(getContext(), R.drawable.black_piano_key, R.drawable.white_piano_key);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int width = getMeasuredWidth();
    int height = getMeasuredHeight();
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) context
            .getSystemService(Context.WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(metrics);
    int wid = metrics.widthPixels;
    int hei = metrics.heightPixels;
    mPiano.initPiano((int)(height*width/1000*0.36));
    Log.w("TAG", "piano width : " + mPiano.getPianoWith());
    setMeasuredDimension(wid, hei);
  }

  @Override
  protected void onDraw(@NonNull Canvas canvas) {

    if (utils == null) {
      if (maxStream > 0) {
        utils = AudioUtils.getInstance(getContext(), loadAudioListener, maxStream);
      } else {
        utils = AudioUtils.getInstance(getContext(), loadAudioListener);
      }
      try {
        utils.loadMusic(mPiano);
      } catch (Exception e) {
        Log.e(TAG, e.getMessage());
      }
    }
    if (mPiano.hasInit()) {
      List<PianoKey[]> whitePianoKeys = mPiano.getWhitePianoKeys();
      for (int i = 0; i < whitePianoKeys.size(); i++) {
        for (PianoKey key : whitePianoKeys.get(i)) {
          paint.setColor(Color.parseColor(pianoColors[i]));
          key.getKeyDrawable().draw(canvas);
          Rect r = key.getKeyDrawable().getBounds();
          int sideLength = (r.right - r.left) / 2;
          int left = r.left + sideLength / 2;
          int top = r.bottom - sideLength - sideLength / 3;
          int right = r.right - sideLength / 2;
          int bottom = r.bottom - sideLength / 3;
          square.set(left, top, right, bottom);
          canvas.drawRoundRect(square, 6f, 6f, paint);
          paint.setColor(Color.BLACK);
          paint.setTextSize(sideLength / 1.8f);
          Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
          int baseline =
              (int) ((square.bottom + square.top - fontMetrics.bottom - fontMetrics.top) / 2);
          paint.setTextAlign(Paint.Align.CENTER);
          canvas.drawText(key.getLetterName(), square.centerX(), baseline, paint);
        }
      }
      List<PianoKey[]> blackPianoKeys = mPiano.getBlackPianoKeys();
      for (int i = 0; i < blackPianoKeys.size(); i++) {
        for (PianoKey key : blackPianoKeys.get(i)) {
          key.getKeyDrawable().draw(canvas);
        }
      }
    }
/*    if (!isInitFinish && piano != null && pianoListener != null) {
      isInitFinish = true;
      pianoListener.onPianoInitFinish();
    }*/
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (canPress) {
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_POINTER_DOWN:
          handleDown(event.getActionIndex(), event);
          break;
        case MotionEvent.ACTION_MOVE:
          for (int i = 0; i < event.getPointerCount(); i++) {
            handleMove(i, event);
          }
          for (int i = 0; i < event.getPointerCount(); i++) {
            handleDown(i, event);
          }
          break;
        case MotionEvent.ACTION_POINTER_UP:
          handlePointerUp(event.getPointerId(event.getActionIndex()));
          break;
        case MotionEvent.ACTION_UP:
          handleUp();
          this.performClick();
          break;
        default:
          break;
      }
      return true;
    } else {
      return false;
    }
  }

  private void handleDown(int which, MotionEvent event) {
    int x = (int) event.getX(which) + this.getScrollX();
    int y = (int) event.getY(which);
    List<PianoKey[]> whitePianoKeys = mPiano.getWhitePianoKeys();
    for (int i = 0; i < whitePianoKeys.size(); i++) {
      for (PianoKey key : whitePianoKeys.get(i)) {
        if (!key.isPressed() && key.contain(x, y)) {
          handleWhiteKeyDown(which, event, key);
        }
      }
    }
    List<PianoKey[]> blackPianoKeys = mPiano.getBlackPianoKeys();
    for (int i = 0; i < blackPianoKeys.size(); i++) {
      for (PianoKey key : blackPianoKeys.get(i)) {
        if (!key.isPressed() && key.contain(x, y)) {
          handleBlackKeyDown(which, event, key);
        }
      }
    }
  }

  private void handleWhiteKeyDown(int which, MotionEvent event, PianoKey key) {
    key.getKeyDrawable().setState(new int[] { android.R.attr.state_pressed });
    key.setIsPressed(true);
    if (event != null) {
      key.setFingerId(event.getPointerId(which));
    }
    pressedKeys.add(key);
    invalidate();
    if (utils != null) {
      utils.playMusic(key);
    }
    if (pianoListener != null) {
      pianoListener.onPianoClick(
          key.isBlackKey(),
          key.getPianoKeyVoice(),
          key.getGroup(),
          key.getPositionOfGroup()
      );
    }
  }

  private void handleBlackKeyDown(int which, MotionEvent event, PianoKey key) {
    key.getKeyDrawable().setState(new int[] { android.R.attr.state_pressed });
    key.setIsPressed(true);
    if (event != null) {
      key.setFingerId(event.getPointerId(which));
    }
    pressedKeys.add(key);
    invalidate();
    if (utils != null) {
      utils.playMusic(key);
    }
    if (pianoListener != null) {
      pianoListener.onPianoClick(
          key.isBlackKey(),
          key.getPianoKeyVoice(),
          key.getGroup(),
          key.getPositionOfGroup()
      );
    }
  }

  private void handleMove(int which, MotionEvent event) {
    int x = (int) event.getX(which) + this.getScrollX();
    int y = (int) event.getY(which);
    for (PianoKey key : pressedKeys) {
      if (key.getFingerId() == event.getPointerId(which)) {
        if (!key.contain(x, y)) {
          key.getKeyDrawable().setState(new int[] { -android.R.attr.state_pressed });
          invalidate(key.getKeyDrawable().getBounds());
          key.setIsPressed(false);
          key.resetFingerId();
          pressedKeys.remove(key);
        }
      }
    }
  }

  private void handlePointerUp(int pointerId) {
    for (PianoKey key : pressedKeys) {
      if (key.getFingerId() == pointerId) {
        key.setIsPressed(false);
        key.resetFingerId();
        key.getKeyDrawable().setState(new int[] { -android.R.attr.state_pressed });
        invalidate(key.getKeyDrawable().getBounds());
        pressedKeys.remove(key);
        break;
      }
    }
  }

  private void handleUp() {
    if (pressedKeys.size() > 0) {
      for (PianoKey key : pressedKeys) {
        key.getKeyDrawable().setState(new int[] { -android.R.attr.state_pressed });
        key.setIsPressed(false);
        invalidate(key.getKeyDrawable().getBounds());
      }
      pressedKeys.clear();
    }
  }

  public void autoPlay(final List<AutoPlayEntity> autoPlayEntities) {
    if (isAutoPlaying) {
      return;
    }
    isAutoPlaying = true;
    setCanPress(false);
    new Thread() {
      @Override
      public void run() {
        if (autoPlayHandler != null) {
          autoPlayHandler.sendEmptyMessage(HANDLE_AUTO_PLAY_START);
        }
        try {
          if (autoPlayEntities != null) {
            for (AutoPlayEntity entity : autoPlayEntities) {
              if (entity != null) {
                if (entity.isBlackKey()) {
                  PianoKey blackKey = null;
                  if (entity.getGroup() == 0) {
                    if (entity.getPosition() == 0) {
                      blackKey = mPiano.getBlackPianoKeys().get(0)[0];
                    }
                  } else if (entity.getGroup() > 0 && entity.getGroup() <= 7) {
                    if (entity.getPosition() >= 0 && entity.getPosition() <= 4) {
                      blackKey =
                          mPiano.getBlackPianoKeys().get(entity.getGroup())[entity.getPosition()];
                    }
                  }
                  if (blackKey != null) {
                    Message msg = Message.obtain();
                    msg.what = HANDLE_AUTO_PLAY_BLACK_DOWN;
                    msg.obj = blackKey;
                    autoPlayHandler.sendMessage(msg);
                  }
                } else {
                  PianoKey whiteKey = null;
                  if (entity.getGroup() == 0) {
                    if (entity.getPosition() == 0) {
                      whiteKey = mPiano.getWhitePianoKeys().get(0)[0];
                    } else if (entity.getPosition() == 1) {
                      whiteKey = mPiano.getWhitePianoKeys().get(0)[1];
                    }
                  } else if (entity.getGroup() >= 0 && entity.getGroup() <= 7) {
                    if (entity.getPosition() >= 0 && entity.getPosition() <= 6) {
                      whiteKey =
                          mPiano.getWhitePianoKeys().get(entity.getGroup())[entity.getPosition()];
                    }
                  } else if (entity.getGroup() == 8) {
                    if (entity.getPosition() == 0) {
                      whiteKey = mPiano.getWhitePianoKeys().get(8)[0];
                    }
                  }
                  if (whiteKey != null) {
                    Message msg = Message.obtain();
                    msg.what = HANDLE_AUTO_PLAY_WHITE_DOWN;
                    msg.obj = whiteKey;
                    autoPlayHandler.sendMessage(msg);
                  }
                }
                Thread.sleep(entity.getCurrentBreakTime() / 2);
                autoPlayHandler.sendEmptyMessage(HANDLE_AUTO_PLAY_KEY_UP);
                Thread.sleep(entity.getCurrentBreakTime() / 2);
              }
            }
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (autoPlayHandler != null) {
          autoPlayHandler.sendEmptyMessage(HANDLE_AUTO_PLAY_END);
        }
      }
    }.start();
  }

  public void releaseAutoPlay() {
    if (utils != null) {
      utils.stop();
    }
  }

  public int getPianoWidth() {
    return mPiano.getPianoWith();
  }

  public int getLayoutWidth() {
    return layoutWidth;
  }

  public void setPianoColors(String[] pianoColors) {
    if (pianoColors.length == 9) {
      this.pianoColors = pianoColors;
    }
  }

  public void setCanPress(boolean canPress) {
    this.canPress = canPress;
  }

  public void scroll(int progress) {

  }

  public void setSoundPollMaxStream(int maxStream) {
    this.maxStream = maxStream;
  }

  public void setPianoListener(OnPianoListener pianoListener) {
    this.pianoListener = pianoListener;
  }

  public void setLoadAudioListener(OnLoadAudioListener loadAudioListener) {
    this.loadAudioListener = loadAudioListener;
  }

  public void setAutoPlayListener(OnPianoAutoPlayListener autoPlayListener) {
    this.autoPlayListener = autoPlayListener;
  }

  private void handleAutoPlay(Message msg) {
    switch (msg.what) {
      case HANDLE_AUTO_PLAY_BLACK_DOWN:
        if (msg.obj != null) {
          try {
            PianoKey key = (PianoKey) msg.obj;
            handleBlackKeyDown(-1, null, key);
          } catch (Exception e) {
            Log.e("TAG", "Добавить сообщение:" + e.getMessage());
          }
        }
        break;
      case HANDLE_AUTO_PLAY_WHITE_DOWN:
        if (msg.obj != null) {
          try {
            PianoKey key = (PianoKey) msg.obj;
            handleWhiteKeyDown(-1, null, key);
          } catch (Exception e) {
            Log.e("TAG", "Добавить сообщение:" + e.getMessage());
          }
        }
        break;
      case HANDLE_AUTO_PLAY_KEY_UP:
        handleUp();
        break;
      case HANDLE_AUTO_PLAY_START:
        if (autoPlayListener != null) {
          autoPlayListener.onPianoAutoPlayStart();
        }
        break;
      case HANDLE_AUTO_PLAY_END:
        isAutoPlaying = false;
        setCanPress(true);
        if (autoPlayListener != null) {
          autoPlayListener.onPianoAutoPlayEnd();
        }
        break;
    }
  }

}
