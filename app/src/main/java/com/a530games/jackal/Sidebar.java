package com.a530games.jackal;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.a530games.framework.Graphics;
import com.a530games.framework.math.Vector2;

public class Sidebar
{
    private final World world;

    /**
     * Sidebar position on screen
     */
    public Point position;

    /**
     * Sidebar sizze
     *
    public int width, height;*/

    private final Paint font;

    private final DataClass data;

    public static class DataClass {
        public int fps;
        public Vector2 playerAngle;

        public DataClass() {
            this.fps = 0;
        }
    }

    private boolean isNeedRedraw = true;

    //public int fps = 0;

    // public Vector2 playerAngle;

    /*public int playerX = 0;
    public int playerY = 0;

    public int mapX = 0;
    public int mapY = 0;*/

    // draw objects
    /*Rect drawRect;
    Canvas canvas;
    public Bitmap drawBitmap;

    Graphics graphics;*/

    public Sidebar(World world, int screenWidth, int screenHeight)
    {
        this.world = world;
        this.position = new Point(screenWidth - 240, 10);

        this.font = new Paint();
        this.font.setTextSize(20);
        this.font.setColor(Color.RED);
        this.font.setStrokeWidth(1);
        this.font.setStyle(Paint.Style.FILL_AND_STROKE);

        this.data = new DataClass();

        // this.playerAngle = new Vector2();

        // big map rect for a drawing
        /*this.drawRect = new Rect(0, 0, width, height);

        this.canvas = new Canvas();

        this.drawBitmap = Bitmap.createBitmap(this.drawRect.width(), this.drawRect.height(), Bitmap.Config.ARGB_8888);
        this.canvas.setBitmap(this.drawBitmap);

        this.graphics = new AndroidGraphics(assets, this.drawBitmap);*/
    }

    /**
     * Надо ли обновлять сайдбар
     */
    public boolean isNeedRedraw(){
        return this.isNeedRedraw;
    }

    /**
     * Set player hp
    public void setPlayerHp(int hp) {
        if (this.data.hp != hp) {
            this.isNeedRedraw = true;
            this.data.hp = hp;
        }
    }*/

    public void setFps(int fps) {
        if (this.data.fps != fps) {
            this.isNeedRedraw = true;
            this.data.fps = fps;
        }
    }

    /*public void setPlayerPos(int x, int y) {
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
    }*/

    public void setRedraw()
    {
        this.isNeedRedraw = false;
    }

    public void draw(Graphics g) {
        this.d(g);
    }

    public void reDraw()
    {
        // this.graphics.clear(Color.BLACK);
        // this.d(this.graphics);

    }
    
    private void d(Graphics g)
    {
        for (int i = 0; i < this.world.player.hp; i++) {
            g.drawPixmap(Assets.hp, this.position.x + 120 - (i * 45), this.position.y + 30);
        }

        g.drawText(String.format("fps: %d", this.data.fps), this.position.x, this.position.y + 100, this.font);
        g.drawText(String.format("player: %.2fx%.2f", this.world.player.hitBox.left, this.world.player.hitBox.top), this.position.x, this.position.y + 130, this.font);
        g.drawText(String.format("angle: %.2fx%.2f", this.world.player.direction.x, this.world.player.direction.y), this.position.x, this.position.y + 160, this.font);
        g.drawText(String.format("turret: %.2fx%.2f", this.world.player.turret.x, this.world.player.turret.y), this.position.x, this.position.y + 190, this.font);
        g.drawText(String.format("map: %.2fx%.2f", this.world.map.position.x, this.world.map.position.y), this.position.x, this.position.y + 220, this.font);
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
