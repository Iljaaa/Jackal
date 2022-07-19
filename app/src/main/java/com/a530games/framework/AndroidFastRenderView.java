package com.a530games.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable
{
    AndroidGame game;

    SurfaceHolder holder;

    Thread renderThread;

    Bitmap frameBuffer;

    volatile boolean running = false;

    public int fps = 0;

    public AndroidFastRenderView(AndroidGame game, Bitmap frameBuffer) {
        super(game);
        this.game = game;
        this.frameBuffer = frameBuffer;
        this.holder = this.getHolder();
        // this.setOnTouchListener(this);
    }

    public void resume()
    {
        Log.d("AndroidFastRenderView", "resume");
        this.running = true;
        this.renderThread = new Thread(this);
        this.renderThread.start();
    }

    public void run()
    {
        Rect dstRect = new Rect();
        // long startTime = System.nanoTime();
        long tickTime = System.nanoTime();
        while(this.running)
        {
            if(!this.holder.getSurface().isValid()) {
                continue;
            }

            // float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;

            // delta time in seconds
            float deltaTime = (System.nanoTime()-tickTime) / 1000000000.0f;
            tickTime = System.nanoTime();

            this.fps = Math.round(1 / deltaTime);

            this.game.getCurrentScreen().update(deltaTime);
            this.game.getCurrentScreen().present(deltaTime);

            Canvas canvas = holder.lockCanvas();
            // Log.d("tag", "width: " + canvas.getWidth() + " x " + canvas.getHeight());

            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(this.frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause()
    {
        this.running = false;
        /*while(true) {
            try {
                renderThread.join();
            } catch (InterruptedException e) {
                // повтор
                Log.d("AndroidFastRenderView", "Exception on join process");
            }
        }*/
        this.renderThread.interrupt();
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("onTouchEvent", "event");
        return super.onTouchEvent(event);
    }*/

    /**
     * Ну ебаный врот это же одно и тоже что я перехватываю события в Controller
     *
     * @param keyCode
     * @param event
     * @return

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean p = super.onKeyDown(keyCode, event);
        Log.d("AndroidFastRenderView", "onKeyDown");
        return p;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean p =  super.onKeyUp(keyCode, event);
        Log.d("AndroidFastRenderView", "onKeyUp");
        return p;
    } */

    public void setFrameBufferSize (int width, int height){
        this.frameBuffer.setWidth(width);
        this.frameBuffer.setHeight(height);
    }
}
