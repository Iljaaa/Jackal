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

public class Map
{
    public static final int SPRITE_HEIGHT = 64;
    public static final int SPRITE_WIDTH = 64;

    // положение карты
    public int x = 0;
    public int y = 0;

    // map size in blocks
    public int mapRows;
    public int mapCols;

    // rect for test intersection
    public FloatRect testRect;

    Rect mapRect;
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
        this.mapCols = collums;
        this.mapRows = rows;

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

        this.fields[1][0] = new MapCell(1, 0, 64, MapCell.MOVE_BUSH_1);
        this.fields[2][0] = new MapCell(2, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[3][0] = new MapCell(3, 0, 64, MapCell.MOVE_BUSH_1);
        this.fields[4][0] = new MapCell(4, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[5][0] = new MapCell(5, 0, 64, MapCell.MOVE_BUSH_1);
        this.fields[6][0] = new MapCell(6, 0, 64, MapCell.MOVE_BUSH_2);
        this.fields[7][0] = new MapCell(7, 0, 64, MapCell.MOVE_BUSH_1);
        this.fields[8][0] = new MapCell(8, 0, 64, MapCell.MOVE_BUSH_2);

        this.fields[9][0] = new MapCell(9, 0, 64, MapCell.MOVE_ROCK_3);
        this.fields[9][1] = new MapCell(9, 1, 64, MapCell.MOVE_ROCK_1);
        this.fields[9][2] = new MapCell(9, 2, 64, MapCell.MOVE_ROCK_2);
        this.fields[9][3] = new MapCell(9, 3, 64, MapCell.MOVE_ROCK_3);
        this.fields[9][4] = new MapCell(9, 4, 64, MapCell.MOVE_ROCK_1);
        this.fields[9][5] = new MapCell(9, 5, 64, MapCell.MOVE_ROCK_2);
        this.fields[9][6] = new MapCell(9, 6, 64, MapCell.MOVE_ROCK_3);
        this.fields[9][7] = new MapCell(9, 7, 64, MapCell.MOVE_ROCK_1);
        this.fields[9][8] = new MapCell(9, 8, 64, MapCell.MOVE_ROCK_2);
        this.fields[9][9] = new MapCell(9, 9, 64, MapCell.MOVE_ROCK_2);

        this.fields[1][9] = new MapCell(1, 9, 64, MapCell.MOVE_BUSH_3);
        this.fields[2][9] = new MapCell(2, 9, 64, MapCell.MOVE_BUSH_4);
        this.fields[3][9] = new MapCell(3, 9, 64, MapCell.MOVE_BUSH_3);
        this.fields[4][9] = new MapCell(4, 9, 64, MapCell.MOVE_BUSH_4);
        this.fields[5][9] = new MapCell(5, 9, 64, MapCell.MOVE_BUSH_3);
        this.fields[6][9] = new MapCell(6, 9, 64, MapCell.MOVE_BUSH_4);
        this.fields[7][9] = new MapCell(7, 9, 64, MapCell.MOVE_BUSH_3);
        this.fields[8][9] = new MapCell(8, 9, 64, MapCell.MOVE_BUSH_1);

        MapCell c = this.fields[3][2];
        c.isRock = true;

        //
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
        // this.g.drawRect(100, 100, 200, 200, Color.RED);

        // draw backend
        for (int row = 0; row < this.mapRows; row++) {
            for (int col = 0; col < this.mapCols; col++) {
                this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 0, 64, 64);
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

    }

    public boolean isIntersect (FloatRect rect)
    {
        // for left top take 3 top
        int row = (int) Math.floor(rect.top / Map.SPRITE_HEIGHT);
        int col = (int) Math.floor(rect.left / Map.SPRITE_WIDTH);

        // take nine sqars
        for (int forCol = col - 1; forCol <= col + 1; forCol++) {
            for (int forRow = row - 1; forRow <= row + 1; forRow++)
            {
                if (forCol < 0) continue;
                if (forRow < 0) continue;
                if (forCol > 9) continue;
                if (forRow > 9) continue;

                MapCell c = this.fields[forRow][forCol];
                if (c == null) continue;

                if (c.hitBox.bottom < rect.top){
                    continue;
                }
                if (c.hitBox.top > rect.bottom){
                    continue;
                }
                if (c.hitBox.right < rect.left){
                    continue;
                }
                if (c.hitBox.left > rect.right){
                    continue;
                }

                return true;
            }
        }

        return false;
    }

    public int getRowByTop(float top) {
        return (int) Math.floor(top / Map.SPRITE_HEIGHT);
    }

    public int getColByLeft(float left) {
        return (int) Math.floor(left / Map.SPRITE_WIDTH);
    }
}
