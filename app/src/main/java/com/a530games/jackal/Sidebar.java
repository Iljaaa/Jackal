package com.a530games.jackal;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.a530games.framework.AndroidGraphics;
import com.a530games.framework.Graphics;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.map.Map;

public class Sidebar
{
    /**
     * Sidebar position on screen
     */
    public int leftPosition;

    /**
     * Sidebar sizze
     */
    public int width, height;

    private DataClass data;

    public class DataClass {
        public int hp;
    }

    private boolean isNeedRedraw = true;

    public int fps = 0;

    public Vector2 playerAngle;

    public int playerX = 0;
    public int playerY = 0;

    public int mapX = 0;
    public int mapY = 0;

    // draw objects
    Rect drawRect;
    Canvas canvas;
    public Bitmap drawBitmap;

    Graphics graphics;

    public Sidebar(int leftPosition, int width, int height, AssetManager assets)
    {
        this.leftPosition = leftPosition;
        this.width = width;
        this.height = height;

        this.data = new DataClass();

        this.playerAngle = new Vector2();

        // big map rect for a drawing
        this.drawRect = new Rect(0, 0, width, height);

        this.canvas = new Canvas();

        this.drawBitmap = Bitmap.createBitmap(this.drawRect.width(), this.drawRect.height(), Bitmap.Config.ARGB_8888);
        this.canvas.setBitmap(this.drawBitmap);

        this.graphics = new AndroidGraphics(assets, this.drawBitmap);
    }

    /**
     * Надо ли обновлять сайдбар
     */
    public boolean isNeedRedraw(){
        return this.isNeedRedraw;
    }

    /**
     * Set player hp
     * @param hp player hp
     */
    public void setPlayerHp(int hp) {
        if (this.data.hp != hp) {
            this.isNeedRedraw = true;
            this.data.hp = hp;
        }
    }

    public void setFps(int fps){
        if (this.fps != fps) {
            this.isNeedRedraw = true;
            this.fps = fps;
        }
    }

    public void setPlayerPos(int x, int y) {
        if (this.playerX != x) {
            this.playerX = x;
            this.isNeedRedraw = true;
        }
        if (this.playerY != y) {
            this.playerY = y;
            this.isNeedRedraw = true;
        }
    }

    public void setMapPos(int x, int y) {
        if (this.mapX != x) {
            this.mapX = x;
            this.isNeedRedraw = true;
        }
        if (this.mapY != y) {
            this.mapY = y;
            this.isNeedRedraw = true;
        }
    }

    public void setPlayerAngle(Vector2 angle) {
        if (this.playerAngle.x != angle.x || this.playerAngle.y != angle.y) {
            this.playerAngle = angle;
            this.isNeedRedraw = true;
        }
    }

    public void setRedraw()
    {
        this.isNeedRedraw = false;
    }

    public void reDraw()
    {
        this.graphics.clear(Color.BLACK);

        for (int i = 0; i < this.data.hp; i++) {
            this.graphics.drawPixmap(Assets.hp, 30 + (i * 45), 30);
        }

        this.graphics.drawText("fps: " + this.fps, 30, 100, 20, Color.MAGENTA);
        this.graphics.drawText("player: " + this.playerX+ "x"+this.playerY, 30, 130, 20, Color.MAGENTA);
        this.graphics.drawText("angle: " + this.playerAngle.x + "x" + this.playerAngle.y, 30, 160, 20, Color.MAGENTA);
        this.graphics.drawText("map: " + this.mapX+ "x"+this.mapY, 30, 190, 20, Color.MAGENTA);

        Log.d("Sidebar", "redraw");

        this.setRedraw();
    }

    /*public void draw ()
    {
        this.testCanvas = new Canvas();
        this.testPaint = new Paint();

        this.drawBitmap = Bitmap.createBitmap(this.drawRect.width(), this.drawRect.height(), Bitmap.Config.ARGB_8888);
        this.testCanvas.setBitmap(this.drawBitmap);
        // this.testCanvas.getClipBounds(this.mapRect);

        this.g = new AndroidGraphics(assets, this.drawBitmap);
    }*/
}
