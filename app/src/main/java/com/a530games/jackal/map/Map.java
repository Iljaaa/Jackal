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
        this.fields = new MapCell[mapRows][mapCols];
        // fixme: fix magic constsa
        this.fields[3][2] = new MapCell(3, 2, 64, MapCell.MOVE_ROCK_1);

        this.fields[0][0] = new MapCell(0, 0, 64, MapCell.MOVE_ROCK_3);
        this.fields[0][1] = new MapCell(0, 1, 64, MapCell.MOVE_ROCK_1);
        this.fields[0][2] = new MapCell(0, 2, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][3] = new MapCell(0, 3, 64, MapCell.MOVE_ROCK_3);
        this.fields[0][4] = new MapCell(0, 4, 64, MapCell.MOVE_ROCK_1);
        this.fields[0][5] = new MapCell(0, 5, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][6] = new MapCell(0, 6, 64, MapCell.MOVE_ROCK_3);
        this.fields[0][7] = new MapCell(0, 7, 64, MapCell.MOVE_ROCK_1);
        this.fields[0][8] = new MapCell(0, 8, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][9] = new MapCell(0, 9, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][10] = new MapCell(0, 10, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][11] = new MapCell(0, 11, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][12] = new MapCell(0, 12, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][13] = new MapCell(0, 13, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][14] = new MapCell(0, 14, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][15] = new MapCell(0, 15, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][16] = new MapCell(0, 16, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][17] = new MapCell(0, 17, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][18] = new MapCell(0, 18, 64, MapCell.MOVE_ROCK_2);
        this.fields[0][19] = new MapCell(0, 19, 64, MapCell.MOVE_ROCK_2);

        this.fields[1][0] = new MapCell(1, 0, 64, MapCell.MOVE_BUSH_1);
        this.fields[2][0] = new MapCell(2, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[3][0] = new MapCell(3, 0, 64, MapCell.MOVE_BUSH_1);
        this.fields[4][0] = new MapCell(4, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[5][0] = new MapCell(5, 0, 64, MapCell.MOVE_BUSH_1);
        this.fields[6][0] = new MapCell(6, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[7][0] = new MapCell(7, 0, 64, MapCell.MOVE_BUSH_1);
        this.fields[8][0] = new MapCell(8, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[9][0] = new MapCell(9, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[10][0] = new MapCell(10, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[11][0] = new MapCell(11, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[12][0] = new MapCell(12, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[13][0] = new MapCell(13, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[14][0] = new MapCell(14, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[15][0] = new MapCell(15, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[16][0] = new MapCell(16, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[17][0] = new MapCell(17, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[18][0] = new MapCell(17, 0, 64, MapCell.MOVE_BUSH_2);

        this.fields[9][3] = new MapCell(9, 3, 64, MapCell.MOVE_ROCK_3);
        this.fields[9][4] = new MapCell(9, 4, 64, MapCell.MOVE_ROCK_1);

        this.fields[19][0] = new MapCell(19, 0, 64, MapCell.MOVE_ROCK_3);
        this.fields[19][1] = new MapCell(19, 1, 64, MapCell.MOVE_ROCK_1);
        this.fields[19][2] = new MapCell(19, 2, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][3] = new MapCell(19, 3, 64, MapCell.MOVE_ROCK_3);
        this.fields[19][4] = new MapCell(19, 4, 64, MapCell.MOVE_ROCK_1);
        this.fields[19][5] = new MapCell(19, 5, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][6] = new MapCell(19, 6, 64, MapCell.MOVE_ROCK_3);
        this.fields[19][7] = new MapCell(19, 7, 64, MapCell.MOVE_ROCK_1);
        this.fields[19][8] = new MapCell(19, 8, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][9] = new MapCell(19, 9, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][10] = new MapCell(19, 10, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][11] = new MapCell(19, 11, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][12] = new MapCell(19, 12, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][13] = new MapCell(19, 13, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][14] = new MapCell(19, 14, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][15] = new MapCell(19, 15, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][16] = new MapCell(19, 16, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][17] = new MapCell(19, 17, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][18] = new MapCell(19, 18, 64, MapCell.MOVE_ROCK_2);
        this.fields[19][19] = new MapCell(19, 19, 64, MapCell.MOVE_ROCK_2);

        this.fields[1][19] = new MapCell(1, 19, 64, MapCell.MOVE_BUSH_3);
        this.fields[2][19] = new MapCell(2, 19, 64, MapCell.MOVE_BUSH_4);
        this.fields[3][19] = new MapCell(3, 19, 64, MapCell.MOVE_BUSH_3);
        this.fields[4][19] = new MapCell(4, 19, 64, MapCell.MOVE_BUSH_4);
        this.fields[5][19] = new MapCell(5, 19, 64, MapCell.MOVE_BUSH_3);
        this.fields[6][19] = new MapCell(6, 19, 64, MapCell.MOVE_BUSH_4);
        this.fields[7][19] = new MapCell(7, 19, 64, MapCell.MOVE_BUSH_3);
        this.fields[8][19] = new MapCell(8, 19, 64, MapCell.MOVE_BUSH_1);
        // this.fields[9][19] = new MapCell(9, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[10][19] = new MapCell(10, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[11][19] = new MapCell(11, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[12][19] = new MapCell(12, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[13][19] = new MapCell(13, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[14][19] = new MapCell(14, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[15][19] = new MapCell(15, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[16][19] = new MapCell(16, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[17][19] = new MapCell(17, 19, 64, MapCell.MOVE_BUSH_1);
        this.fields[18][19] = new MapCell(18, 19, 64, MapCell.MOVE_BUSH_1);

        this.fields[1][9] = new MapCell(1, 9, 64, MapCell.MOVE_BUSH_3);
        this.fields[2][9] = new MapCell(2, 9, 64, MapCell.MOVE_BUSH_4);
        this.fields[3][9] = new MapCell(3, 9, 64, MapCell.MOVE_BUSH_3);
        this.fields[4][9] = new MapCell(4, 9, 64, MapCell.MOVE_BUSH_4);
        this.fields[5][9] = new MapCell(5, 9, 64, MapCell.MOVE_BUSH_3);
        this.fields[6][9] = new MapCell(6, 9, 64, MapCell.MOVE_BUSH_4);
        this.fields[7][9] = new MapCell(7, 9, 64, MapCell.MOVE_BUSH_3);
        this.fields[14][9] = new MapCell(14, 9, 64, MapCell.MOVE_BUSH_1);
        this.fields[15][9] = new MapCell(15, 9, 64, MapCell.MOVE_BUSH_1);
        this.fields[16][9] = new MapCell(16, 9, 64, MapCell.MOVE_BUSH_1);
        this.fields[17][9] = new MapCell(17, 9, 64, MapCell.MOVE_BUSH_1);
        this.fields[18][9] = new MapCell(18, 9, 64, MapCell.MOVE_BUSH_1);

        MapCell c = this.fields[3][2];
        c.isRock = true;

        //
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

                switch (c.type) {
                    case MapCell.MOVE_ROCK_1:
                        this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 64, 0, 64, 64);
                        break;
                    case MapCell.MOVE_ROCK_2:
                        this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 0, 64, 64);
                        break;
                    case MapCell.MOVE_ROCK_3:
                        this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 64, 128, 64, 64);
                        break;
                    case MapCell.MOVE_BUSH_1:
                        this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 64, 64, 64);
                        break;
                    case MapCell.MOVE_BUSH_2:
                        this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 128, 64, 64);
                        break;
                    case MapCell.MOVE_BUSH_3:
                        this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 64, 64, 64);
                        break;
                    case MapCell.MOVE_BUSH_4:
                        this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 128, 64, 64);
                        break;
                }

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

        return cell.isIntersectPintInsideRect(left, top);
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
