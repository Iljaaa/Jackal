package com.a530games.jackal.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.a530games.framework.AndroidGraphics;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.objects.Player;

public class Map
{
    public static final int SPRITE_HEIGHT = 64;
    public static final int SPRITE_WIDTH = 64;

    // map position
    public float x = 0;
    public float y = 0;

    // min ,ap position
    public int minX = 0;
    public int minY = 0;

    // map size in blocks
    public int mapRows;
    public int mapCols;

    // player start position
    public int playerStartX = 0;
    public int playerStartY = 0;

    // rect for test intersection
    public FloatRect testRect;

    Rect drawRect;
    Canvas testCanvas;
    Paint testPaint;
    public Bitmap testBitmap;

    Graphics g;

    /**
     * fiels [row][col]
     */
    public MapCell[][] fields; // = new MapCell[1][1];


    public Map()
    {
        // this.fields = new MapCell[mapRows][mapCols];
        // this.fields[3][2] = new MapCell();

//        MapCell c =this.fields[3][2];
//        c.isRock = true;

        this.testRect = new FloatRect(400, 400, 500, 500);
    }

    public static boolean isIntersectsTwoRect(FloatRect r1, FloatRect r2)
    {
        //
        if (r1.bottom < r2.top){
            return false;
        }

        if (r1.top > r2.bottom){
            return false;
        }

        if (r1.right < r2.left){
            return false;
        }

        if (r1.left > r2.right) {
            return false;
        }

        return true;
    }

    /**
     * Loading map sprite
     */
    public void loadMapAssets () {

    }

    /**
     * Prepare map
     */
    public void init (int collums, int rows, AssetManager assets, Player player)
    {
        // map size in cols
        this.mapCols = collums;
        this.mapRows = rows;

        // calculate map map position
        // fixme: magic numbers
        // 640 its screenSize
        this.minX = -1 * ((collums * Map.SPRITE_WIDTH) - 640);
        this.minY = -1 * ((rows * Map.SPRITE_HEIGHT) - 640);

        // this.playerStartX = 400;
        // this.playerStartY = 1100;
        // move player on start position
        player.hitBox.moveTo(400, 1100);

        // calculate start map position
        this.x = -1 * (player.hitBox.left - 320 - 20);
        this.y = -1 * (player.hitBox.top - 320 - 20);
        if (this.x < this.minX) this.x = this.minX;
        if (this.y < this.minY) this.y = this.minY;

        // init fields
        this.fields = new MapCell[this.mapRows][this.mapCols];


        // fixme: fix magic constants
        this.addRock(3, 2, Rock.MOVE_ROCK_1);

        this.addRock(0, 0, Rock.MOVE_ROCK_3);
        this.addRock(0, 1, Rock.MOVE_ROCK_1);
        this.addRock(0, 2, Rock.MOVE_ROCK_2);
        this.addRock(0, 3, Rock.MOVE_ROCK_3);
        this.addRock(0, 4, Rock.MOVE_ROCK_1);
        this.addRock(0, 5, Rock.MOVE_ROCK_2);
        this.addRock(0, 6, Rock.MOVE_ROCK_3);
        this.addRock(0, 7, Rock.MOVE_ROCK_1);
        this.addRock(0, 8, Rock.MOVE_ROCK_2);
        this.addRock(0, 9, Rock.MOVE_ROCK_2);
        this.addRock(0, 10, Rock.MOVE_ROCK_2);
        this.addRock(0, 11, Rock.MOVE_ROCK_2);
        this.addRock(0, 12, Rock.MOVE_ROCK_2);
        this.addRock(0, 13, Rock.MOVE_ROCK_2);
        this.addRock(0, 14, Rock.MOVE_ROCK_2);
        this.addRock(0, 15, Rock.MOVE_ROCK_2);
        this.addRock(0, 16, Rock.MOVE_ROCK_2);
        this.addRock(0, 17, Rock.MOVE_ROCK_2);
        this.addRock(0, 18, Rock.MOVE_ROCK_2);
        this.addRock(0, 19, Rock.MOVE_ROCK_2);

        this.addRock(1, 0, Rock.MOVE_BUSH_1);
        this.addRock(2, 0, Rock.MOVE_BUSH_2);
        this.addRock(3, 0, Rock.MOVE_BUSH_1);
        this.addRock(4, 0, Rock.MOVE_BUSH_2);
        this.addRock(5, 0, Rock.MOVE_BUSH_1);
        this.addRock(6, 0, Rock.MOVE_BUSH_2);
        this.addRock(7, 0, Rock.MOVE_BUSH_1);
        this.addRock(8, 0, Rock.MOVE_BUSH_2);
        this.addRock(9, 0, Rock.MOVE_BUSH_2);
        this.addRock(10, 0, Rock.MOVE_BUSH_2);
        this.addRock(11, 0, Rock.MOVE_BUSH_2);
        this.addRock(12, 0, Rock.MOVE_BUSH_2);
        this.addRock(13, 0, Rock.MOVE_BUSH_2);
        this.addRock(14, 0, Rock.MOVE_BUSH_2);
        this.addRock(15, 0, Rock.MOVE_BUSH_2);
        this.addRock(16, 0, Rock.MOVE_BUSH_2);
        this.addRock(17, 0, Rock.MOVE_BUSH_2);
        this.addRock(18, 0, Rock.MOVE_BUSH_2);

        this.addRock(9, 3, Rock.MOVE_ROCK_3);
        this.addRock(9, 4, Rock.MOVE_ROCK_1);

        /*this.addRock(19, 0, Rock.MOVE_ROCK_3);
        this.addRock(19, 1, Rock.MOVE_ROCK_1);
        this.addRock(19, 2, Rock.MOVE_ROCK_2);
        this.addRock(19, 3, Rock.MOVE_ROCK_3);
        this.addRock(19, 4, Rock.MOVE_ROCK_1);
        this.addRock(19, 5, Rock.MOVE_ROCK_2);
        this.addRock(19, 6, Rock.MOVE_ROCK_3);
        this.addRock(19, 7, Rock.MOVE_ROCK_1);
        this.addRock(19, 8, Rock.MOVE_ROCK_2);
        this.addRock(19, 9, Rock.MOVE_ROCK_2);
        this.addRock(19, 10, Rock.MOVE_ROCK_2);
        this.addRock(19, 11, Rock.MOVE_ROCK_2);
        this.addRock(19, 12, Rock.MOVE_ROCK_2);
        this.addRock(19, 13, Rock.MOVE_ROCK_2);
        this.addRock(19, 14, Rock.MOVE_ROCK_2);
        this.addRock(19, 15, Rock.MOVE_ROCK_2);
        this.addRock(19, 16, Rock.MOVE_ROCK_2);
        this.addRock(19, 17, Rock.MOVE_ROCK_2);
        this.addRock(19, 18, Rock.MOVE_ROCK_2);
        this.addRock(19, 19, Rock.MOVE_ROCK_2);*/

        this.addBeach(19, 0);
        this.addBeach(19, 1);
        this.addBeach(19, 2);
        this.addBeach(19, 3);
        this.addBeach(19, 4);
        this.addBeach(19, 5);
        this.addBeach(19, 6);
        this.addBeach(19, 7);
        this.addBeach(19, 8);
        this.addBeach(19, 9);
        this.addBeach(19, 10);
        this.addBeach(19, 11);
        this.addBeach(19, 12);
        this.addBeach(19, 13);
        this.addBeach(19, 14);
        this.addBeach(19, 15);
        this.addBeach(19, 16);
        this.addBeach(19, 17);
        this.addBeach(19, 18);
        this.addBeach(19, 19);


        this.addRock(1, 19, Rock.MOVE_BUSH_3);
        this.addRock(2, 19, Rock.MOVE_BUSH_4);
        this.addRock(3, 19, Rock.MOVE_BUSH_3);
        this.addRock(4, 19, Rock.MOVE_BUSH_4);
        this.addRock(5, 19, Rock.MOVE_BUSH_3);
        this.addRock(6, 19, Rock.MOVE_BUSH_4);
        this.addRock(7, 19, Rock.MOVE_BUSH_3);
        this.addRock(8, 19, Rock.MOVE_BUSH_1);
        //  = new MapCell(9, 19, MapCell.MOVE_BUSH_1);
        this.addRock(10, 19, Rock.MOVE_BUSH_1);
        this.addRock(11, 19, Rock.MOVE_BUSH_1);
        this.addRock(12, 19, Rock.MOVE_BUSH_1);
        this.addRock(13, 19, Rock.MOVE_BUSH_1);
        this.addRock(14, 19, Rock.MOVE_BUSH_1);
        this.addRock(15, 19, Rock.MOVE_BUSH_1);
        this.addRock(16, 19, Rock.MOVE_BUSH_1);
        this.addRock(17, 19, Rock.MOVE_BUSH_1);
        this.addRock(18, 19, Rock.MOVE_BUSH_1);

        this.addRock(1, 9, Rock.MOVE_BUSH_3);
        this.addRock(2, 9, Rock.MOVE_BUSH_4);
        this.addRock(3, 9, Rock.MOVE_BUSH_3);
        this.addRock(4, 9, Rock.MOVE_BUSH_4);
        this.addRock(5, 9, Rock.MOVE_BUSH_3);
        this.addRock(6, 9, Rock.MOVE_BUSH_4);
        this.addRock(7, 9, Rock.MOVE_BUSH_3);
        this.addRock(14, 9, Rock.MOVE_BUSH_1);
        this.addRock(15, 9, Rock.MOVE_BUSH_1);
        this.addRock(16, 9, Rock.MOVE_BUSH_1);
        this.addRock(17, 9, Rock.MOVE_BUSH_1);
        this.addRock(18, 9, Rock.MOVE_BUSH_1);

        /*MapCell c = this.fields[3][2];
        c.isRock = true;*/

        // big map rect for a drawing
        this.drawRect = new Rect(0, 0, collums * Map.SPRITE_WIDTH, rows * Map.SPRITE_HEIGHT);

        this.testCanvas = new Canvas();
        this.testPaint = new Paint();

        this.testBitmap = Bitmap.createBitmap(this.drawRect.width(), this.drawRect.height(), Bitmap.Config.ARGB_8888);
        this.testCanvas.setBitmap(this.testBitmap);
        // this.testCanvas.getClipBounds(this.mapRect);

        this.g = new AndroidGraphics(assets, this.testBitmap);

        // draw objects
        // this.b = new BitmapFactory();
        this.testPaint.setStyle(Paint.Style.FILL);
        this.testPaint.setColor(Color.RED);

    }

    private void addRock (int row, int col, int type)
    {
        this.fields[row][col] = new Rock(row, col, type);
    }

    private void addBeach (int row, int col)
    {
        this.fields[row][col] = new Beach(row, col);
    }

    public void draw()
    {
        // this.g.drawRect(100, 100, 200, 200, Color.RED);

        // draw backend
        for (int row = 0; row < this.mapRows; row++) {
            for (int col = 0; col < this.mapCols; col++) {
                // fixme: wtf? 65? not 64
                this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 0, 65, 65);
            }
        }

        // draw objects
        for (int row = 0; row < this.mapRows; row++) {
            for (int col = 0; col < this.mapCols; col++)
            {

                MapCell c = this.fields[row][col];
                if (c == null) continue;

                // draw block on background
                c.drawOnBackground(this.g);

                // this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 0, 64, 64);
            }
        }

        // draw bush

        this.g.drawPixmap(Assets.mapSprite, 5 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 0, 64, 64, 64);
        this.g.drawPixmap(Assets.mapSprite, 5 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 0, 128, 64, 64);

        this.g.drawPixmap(Assets.mapSprite, 7 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 128, 64, 64, 64);
        this.g.drawPixmap(Assets.mapSprite, 7 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 128, 128, 64, 64);

        this.g.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 64, 128, 64, 64);
        this.g.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 64, 0, 64, 64);
        this.g.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 3 * Map.SPRITE_HEIGHT, 128, 0, 64, 64);

        this.g.drawPixmap(Assets.bigStone, 4 * Map.SPRITE_WIDTH, 5 * Map.SPRITE_HEIGHT, 0, 0, 192, 255);

    }

    public void update(Player player, float deltaTime)
    {
        // update animated blocks
        // todo: do not update cells outside draw
        int minColIndex = 0;
        int maxCols = this.mapCols;

        int minRowIndex = 0;
        int maxRowIndex = this.mapRows;

        for (int row = minRowIndex; row < maxRowIndex; row++) {
            for (int col = minColIndex; col < maxCols; col++) {

                MapCell c = this.fields[row][col];
                if (c == null) continue;

                c.update(deltaTime);
            }
        }

        // move map
        this.updateMapPosition(player);
    }

    private void updateMapPosition (Player player)
    {
        // on top
        int topOnScreen = this.screenTopPotion(player.hitBox.top);
        if (topOnScreen < 200)
        // if (player.hitBox.top < 200)
        {
            // int delta = (int) Math.floor(200 - topOnScreen);

            // move map on left
            this.y = this.y + (200 - topOnScreen);

            if (this.y > 0) {
                // delta = delta - this.y;
                this.y = 0;
            }

            // player.hitBox.move(0, delta);*/
        }

        // on the right border
        int rightOnScreen = this.screenLeftPotion(player.hitBox.right);
        if (rightOnScreen > 440)
        {
            // int delta = (int) Math.floor(rightOnScreen - 440);

            // move map on left
            this.x = this.x - (rightOnScreen - 440);

            if (this.x < this.minX) {
                //delta = delta - (this.y - this.minY);
                this.x = this.minX;
            }
            /*else {
                // player.hitBox.move(-1 * delta, 0);
            }*/
        }


        // on down
        int bottomScreen = this.screenTopPotion(player.hitBox.bottom);
        if (bottomScreen > 440)
        {
            // int delta = bottomScreen - 440;
            // move map on left
            this.y = this.y - (bottomScreen - 440);

            if (this.y < this.minY) {
                //delta = delta - (this.y - this.minY);
                this.y = this.minY;
            }
            /*else {
                // player.hitBox.move(0, -1 * delta);
            }*/
        }

        // on the left border
        int leftOnScreen = this.screenLeftPotion(player.hitBox.left);
        if (leftOnScreen < 200)
        {
            // int delta = (int) Math.floor(200 - leftOnScreen);

            // move map on left
            this.x = this.x + (200 - leftOnScreen);

            if (this.x > 0) {
                // delta = delta - this.x;
                this.x = 0;
            }

            // player.hitBox.move(delta, 0);*/
        }
    }

    /**
     * Intersect for rect
     */
    public boolean isIntersect (FloatRect rectOnMap)
    {
        /*int rectTopOnMap = (int) Math.floor(rectOnMap.top - this.y);
        int rectRightOnMap = (int) Math.floor(rectOnMap.right - this.x);
        int rectBottomOnMap = (int) Math.floor(rectOnMap.bottom - this.y);
        int rectLeftOnMap = (int) Math.floor(rectOnMap.left - this.x);*/

        // for left top take 3 top
        int row = (int) Math.floor(rectOnMap.top / Map.SPRITE_HEIGHT);
        int col = (int) Math.floor(rectOnMap.left / Map.SPRITE_WIDTH);

        // take nine sqars
        for (int forCol = col - 1; forCol <= col + 1; forCol++) {
            for (int forRow = row - 1; forRow <= row + 1; forRow++)
            {
                if (forCol < 0) continue;
                if (forRow < 0) continue;
                if (forCol >= this.mapCols) continue;
                if (forRow >= this.mapRows) continue;

                MapCell cell = this.fields[forRow][forCol];
                if (cell == null) continue;

                // todo: make intersect inside rect
                if (cell.hitBox.bottom < rectOnMap.top){
                    continue;
                }
                if (cell.hitBox.top > rectOnMap.bottom){
                    continue;
                }
                if (cell.hitBox.right < rectOnMap.left){
                    continue;
                }
                if (cell.hitBox.left > rectOnMap.right){
                    continue;
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Intersect for point
     */
    public boolean isIntersectPoint (float left, float top)
    {
        int row = this.getRowByTop(top);
        int col = this.getColByLeft(left);

        if (col < 0) {
            return false;
        }

        if (row < 0) {
            return false;
        }

        if (row >= this.mapRows) {
            return false;
        }

        if (col >= this.mapCols) {
            return false;
        }

        // take nine sqars

        MapCell cell = this.fields[row][col];
        if (cell == null) return false;

        return cell.isIntersectPointInsideRect(left, top);
    }

    public int getRowByTop(float top) {
        return (int) Math.floor(top / Map.SPRITE_HEIGHT);
    }

    public int getColByLeft(float left) {
        return (int) Math.floor(left / Map.SPRITE_WIDTH);
    }

    public int screenTopPotion (float globalTop){
        return (int) Math.floor(globalTop + this.y);
    }

    public int screenLeftPotion (float globalLeft){
        return (int) Math.floor(globalLeft + this.x);
    }
}
