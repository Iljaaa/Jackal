package com.a530games.framework;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

public abstract class AndroidGame extends Activity implements Game
{
    /**
     * Штука которая рендерит
     */
    public AndroidFastRenderView renderView;

    /**
     * Ориентация экрана
     */
    int screenOrientation = Configuration.ORIENTATION_LANDSCAPE;

    // размеры экрана для рисования
    private static final int landscapeScreenWidth = 800;
    private static final int landscapeScreenHeight = 640;

    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // определяем ориентацию экрана
        this.screenOrientation = this.getResources().getConfiguration().orientation;

        /*int frameBufferWidth = this.screenOrientation == Configuration.ORIENTATION_LANDSCAPE
                ? AndroidGame.landscapeScreenWidth
                : AndroidGame.landscapeScreenHeight;*/

        // only landsxape
        /*int frameBufferHeight = this.screenOrientation == Configuration.ORIENTATION_LANDSCAPE
                ? AndroidGame.landscapeScreenHeight
                : AndroidGame.landscapeScreenWidth;*/
        int frameBufferHeight = AndroidGame.landscapeScreenHeight;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        Log.d("AndroidGame", "Screen size: "+String.valueOf(screenWidth)+"x"+String.valueOf(screenHeight));

        // screen ration
        double screenRatio = (double) screenWidth / screenHeight;

        // расчитываем ширину фрембуфера по отношению сторон
        int frameBufferWidth = (int) Math.floor(AndroidGame.landscapeScreenHeight * screenRatio);

        // как же как же смне собрайть фрейм буфер
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        // screen scales
        float scaleX = (float) frameBufferWidth / screenWidth;
        float scaleY = (float) frameBufferHeight / screenHeight;

        //
        this.renderView = new AndroidFastRenderView(this, frameBuffer);

        this.graphics = new AndroidGraphics(this.getAssets(), frameBuffer);


        // fileIO = new AndroidFileIO(getAssets());
        this.audio = new AndroidAudio(this);

        this.input = new AndroidInput(this, this.renderView, scaleX, scaleY);

        this.screen = this.getStartScreen();
        this.setContentView(this.renderView);

        /*PowerManager powerManager = (PowerManager)
                getSystemService(context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "GLGame");*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("AndroidGame", "onStart2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("AndroidGame", "onRestart");
    }

    @Override
    public void onResume()
    {
        Log.d("AndroidGame", "onResume0");
        super.onResume();
        Log.d("AndroidGame", "onResume1");
        //this.wakeLock.acquire();

        this.screen.resume();
        this.renderView.resume();
        Log.d("AndroidGame", "onResume3");
    }

    @Override
    public void onPause()
    {
        super.onPause();

        Log.d("AndroidGame", "onPause2");
        // wakeLock.release();
        this.screen.pause();
        Log.d("AndroidGame", "onPause21");

        this.renderView.pause();
        Log.d("AndroidGame", "onPause3");


        if (this.isFinishing()) {
            Log.d("AndroidGame", "this.screen.dispose()");
            this.screen.dispose();
        }
        Log.d("AndroidGame", "onPause4");
    }

    @Override
    protected void onStop() {
        Log.d("AndroidGame", "onStop");
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        Log.d("AndroidGame", "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // перехватываем событие выхода
        if (keyCode == KeyEvent.KEYCODE_BUTTON_B) return false;
        if (keyCode == KeyEvent.KEYCODE_BACK) return false;

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setScreen(Screen screen) {
        if (this.screen == null) throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();

        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return this.screen;
    }

    @Override
    public Input getInput() {
        return this.input;
    }

    @Override
    public FileIO getFileIO() {
        return this.fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return this.graphics;
    }

    @Override
    public Audio getAudio() {
        return this.audio;
    }

    @Override
    public AndroidFastRenderView getRenderView() {
        return this.renderView;
    }

    /*@Override
    public int getScreenOrientation() {
        return this.screenOrientation;
    }*/

    @Override
    public boolean isLandscape() {
        return this.screenOrientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        // todo: fix orientation
        /*super.onConfigurationChanged(newConfig);
        this.screenOrientation = newConfig.orientation;

        // устанавливаем новые параметры frameBuffer
        if (this.isLandscape()) {
            this.renderView.frameBuffer.setWidth(AndroidGame.landscapeScreenWidth);
            this.renderView.frameBuffer.setHeight(AndroidGame.landscapeScreenHeight);

            // this.graphics = new AndroidGraphics(this);
        }
        else {
            this.renderView.frameBuffer.setWidth(AndroidGame.landscapeScreenHeight);
            this.renderView.frameBuffer.setHeight(AndroidGame.landscapeScreenWidth);
        }*/
    }
}
