package com.a530games.jackal.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.a530games.framework.AndroidGraphics;
import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;

public class Map
{
    public static final int SPRITE_HEIGHT = 64;
    public static final int SPRITE_WIDTH = 64;

    // положение карты
    public int x = 0;
    public int y = 0;

    // размер карты в блоках
    public int MapRows = 50;
    public int MapCols = 100;

    // rect for test intersection
    public FloatRect testRect;

    Rect mapRect;
    Canvas testCanvas;
    Paint testPaint;
    public Bitmap testBitmap;

    Graphics g;

    /**
     * массив полей
     */
    public MapCell[][] fields; // = new MapCell[1][1];



    public Map()
    {
        this.fields = new MapCell[MapRows][MapCols];
        this.fields[3][2] = new MapCell();

        MapCell c =this.fields[3][2];
        c.isRock = true;

        this.testRect = new FloatRect(400, 400, 500, 500);
    }

    /**
     * Loading map sprite
     */
    public void loadMapAssets () {

    }

    /**
     * Prepare map
     */
    public void init (int collums, int rows, AssetManager assets)
    {
        this.mapRect = new Rect(0, 0, collums * Map.SPRITE_WIDTH, rows * Map.SPRITE_HEIGHT);

        this.testCanvas = new Canvas();
        this.testPaint = new Paint();

        this.testBitmap = Bitmap.createBitmap(this.mapRect.width(), this.mapRect.height(), Bitmap.Config.ARGB_8888);
        this.testCanvas.setBitmap(this.testBitmap);
        // this.testCanvas.getClipBounds(this.mapRect);

        this.g = new AndroidGraphics(assets, this.testBitmap);

        // draw objects
        // this.b = new BitmapFactory();
        this.testPaint.setStyle(Paint.Style.FILL);
        this.testPaint.setColor(Color.RED);

    }

    public void draw()
    {
        this.g.drawRect(100, 100, 200, 200, Color.RED);
    }

    public int getRowByTop(float top) {
        return (int) Math.floor(top / Map.SPRITE_HEIGHT);
    }

    public int getColByLeft(float left) {
        return (int) Math.floor(left / Map.SPRITE_WIDTH);
    }
}
