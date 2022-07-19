package com.a530games.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.logging.Logger;

public class FastRenderView extends SurfaceView implements Runnable
{
    SurfaceHolder holder;

    Thread renderThread;

    volatile boolean running = false;

    public FastRenderView(Context context) {
        super(context);
        this.holder = this.getHolder();
    }

    public void resume()
    {
        this.running = true;
        this.renderThread = new Thread(this);
        this.renderThread.start();
    }

    public void run()
    {
        while(running)
        {
            if(!this.holder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = holder.lockCanvas();
            Log.d("tag", "width: " + canvas.getWidth() + " x " + canvas.getHeight());

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause()
    {
        running = false;
        while(true) {
            try {
                renderThread.join();
            } catch (InterruptedException e) {
                // повтор
            }
        }
    }
}
