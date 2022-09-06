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
import com.a530games.jackal.objects.enemies.Enemy;

public class Map implements CellEventCallbackHandler
{
    public static final int SPRITE_HEIGHT = 64;
    public static final int SPRITE_WIDTH = 64;

    // map position
    public float x = 0;
    public float y = 0;

    // min|max map position
    public int mapMinX = 0,  minMinY = 0;

    // object min max position
    private int objectMinX = 0, objectMaxX = 0, objectMinY = 0, objectMaxY = 0;

    // map size in blocks
    public int mapRows;
    public int mapCols;

    // player start position
    public int playerStartX = 0;
    public int playerStartY = 0;

    // map optimization min|max positions for draw
    public int drawMinCol = 0, drawMaxCol = 0, drawMinRow = 0, drawMaxRow = 0;

    Rect drawRect;
    Canvas testCanvas;
    Paint testPaint;

    public Bitmap drawBitmap;

    Graphics backgroundGraphic;

    private MapEventsHandler eventsHandler = null;

    /**
     * fiels [row][col]
     */
    public MapCell[][] fields; // = new MapCell[1][1];

    public Map()
    {
        // this.fields = new MapCell[mapRows][mapCols];
        // this.fields[3][2] = new MapCell();
    }

    public void setEventHandler(MapEventsHandler eventHandler) {
        this.eventsHandler = eventHandler;
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

        // calculate max map positions`
        // fixme: magic numbers
        // 640 its screenSize
        this.mapMinX = -1 * ((collums * Map.SPRITE_WIDTH) - 640);
        this.minMinY = -1 * ((rows * Map.SPRITE_HEIGHT) - 640);

        // calculate min|max objects position
        this.objectMinX = Map.SPRITE_WIDTH;
        this.objectMaxX = (this.mapCols * Map.SPRITE_WIDTH) - Map.SPRITE_WIDTH;
        this.objectMinY = Map.SPRITE_HEIGHT;
        this.objectMaxY = (this.mapRows * Map.SPRITE_HEIGHT) - Map.SPRITE_HEIGHT;

        // this.playerStartX = 400;
        // this.playerStartY = 1100;
        // move player on start position
        player.hitBox.moveTo(400, 1100);

        // calculate start map position
        // after move player
        this.x = -1 * (player.hitBox.left - 320 - 20);
        this.y = -1 * (player.hitBox.top - 320 - 20);
        if (this.x < this.mapMinX) this.x = this.mapMinX;
        if (this.y < this.minMinY) this.y = this.minMinY;

        // init fields
        this.fields = new MapCell[this.mapRows][this.mapCols];

        // update draw limits
        this.updateMapOptimizatonFields();

        //
        this.addObjectToMap();


        /*MapCell c = this.fields[3][2];
        c.isRock = true;*/

        // big map rect for a drawing
        this.drawRect = new Rect(0, 0, collums * Map.SPRITE_WIDTH, rows * Map.SPRITE_HEIGHT);

        this.testCanvas = new Canvas();
        this.testPaint = new Paint();

        this.drawBitmap = Bitmap.createBitmap(this.drawRect.width(), this.drawRect.height(), Bitmap.Config.ARGB_8888);
        this.testCanvas.setBitmap(this.drawBitmap);
        // this.testCanvas.getClipBounds(this.mapRect);

        this.backgroundGraphic = new AndroidGraphics(assets, this.drawBitmap);

        // draw objects
        // this.b = new BitmapFactory();
        this.testPaint.setStyle(Paint.Style.FILL);
        this.testPaint.setColor(Color.RED);

    }

    private void addObjectToMap ()
    {
        // fixme: fix magic constants
        this.addRock(3, 2, Rock.MOVE_ROCK_1);

        // top line
//        for (int col = 1; col < this.mapCols - 1; col++) {
//            this.fields[0][col] = new TopHalfWall(0, col);
//        }

        // bottom line
         for (int col = 0; col < this.mapCols; col++) {
             this.addBeach(this.mapRows - 1, col);
         }

         // left line
        /*for (int row = 1; row < this.mapRows - 1; row++) {
            this.fields[row][0] = new LeftHalfWall(row, 0);
        }*/

         // right line
        /*for (int row = 1; row < this.mapRows - 1; row++) {
            this.fields[row][this.mapCols - 1] = new RightHalfWall(row, this.mapCols - 1);
        }*/

        // this.fields[0][0] = new LeftTopCorner(0, 0); // left top corner
        //this.fields[0][this.mapCols - 1] = new RightTopCorner(0, this.mapCols - 1); // right top corner
        // this.fields[this.mapRows - 1][0] = new LeftBottomCorner(this.mapRows - 1, 0); // left bottom corner
        // this.fields[this.mapRows - 1][this.mapCols - 1] = new RightBottomCorner(this.mapRows - 1, this.mapCols - 1);  // right bottom corner


        // top line
        /*this.addRock(0, 0, Rock.MOVE_ROCK_3);
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
        this.addRock(0, 19, Rock.MOVE_ROCK_2);*/

        // left line
        /*this.addRock(1, 0, Rock.MOVE_BUSH_1);
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
        this.addRock(18, 0, Rock.MOVE_BUSH_2);*/


        this.addRock(9, 3, Rock.MOVE_ROCK_3);
        this.addRock(9, 4, Rock.MOVE_ROCK_1);


        this.fields[9][15] = new Tree1(9, 15);
        this.fields[14][15] = new Tree2(14, 15);


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

        // bottom linr
        /*this.addBeach(19, 0);
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
        this.addBeach(19, 19);*/


        /*this.addRock(1, 19, Rock.MOVE_BUSH_3);
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
        this.addRock(18, 19, Rock.MOVE_BUSH_1);*/

        this.addRock(1, 9, Rock.MOVE_BUSH_3);
        this.addRock(2, 9, Rock.MOVE_BUSH_4);
        this.addRock(3, 9, Rock.MOVE_BUSH_3);
        this.addRock(4, 9, Rock.MOVE_BUSH_4);
        this.addRock(5, 9, Rock.MOVE_BUSH_3);
        this.addRock(6, 9, Rock.MOVE_BUSH_4);
        this.addRock(7, 9, Rock.MOVE_BUSH_3);
        // this.addRock(14, 9, Rock.MOVE_BUSH_1);
        // this.addRock(15, 9, Rock.MOVE_BUSH_1);
        // this.addRock(16, 9, Rock.MOVE_BUSH_1);
        // this.addRock(17, 9, Rock.MOVE_BUSH_1);
        // this.addRock(18, 9, Rock.MOVE_BUSH_1);

        this.fields[16][8] = new Wall(16, 8);
        this.fields[12][8] = new FuncCorner(12, 8);


        this.fields[12][11] = new LeftTopCorner(12, 11);
        this.fields[12][12] = new RightTopCorner(12, 12);

        this.fields[14][13] = new RightTopCorner(14, 13);
        this.fields[15][13] = new RightBottomCorner(15, 13);

        this.fields[17][11] = new LeftBottomCorner(17, 11);
        this.fields[17][12] = new RightBottomCorner(17, 12);

        this.fields[14][10] = new LeftTopCorner(14, 10);
        this.fields[15][10] = new LeftBottomCorner(15, 10);

        this.fields[13][3] = new BigPillarTopLeft(13, 3);
        this.fields[13][4] = new BigPillarTopRight(13, 4);
        this.fields[14][3] = new BigPillarSecondLeft(14, 3);
        this.fields[14][4] = new BigPillarSecondRight(14, 4);
        this.fields[15][3] = new BigPillarThirdLeft(15, 3);
        this.fields[15][4] = new BigPillarThirdRight(15, 4);
        this.fields[16][3] = new BigPillarBottomLeft(16, 3);
        this.fields[16][4] = new BigPillarBottomRight(16, 4);

        this.fields[16][1] = new Bush(16, 1, Assets.bush1);
        this.fields[16][2] = new Bush(16, 2, Assets.bush2);


        this.fields[11][3] = new Spown(11, 3);
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

        // draw title backend
        for (int row = 0; row < this.mapRows; row++) {
            for (int col = 0; col < this.mapCols; col++) {
                this.backgroundGraphic.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 0, 65, 65);
            }
        }

        // draw walls
        // top wall
        for (int col = 1; col < this.mapCols - 1; col++) {
            this.backgroundGraphic.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, 0, 256, 0,  Map.SPRITE_WIDTH,  Map.SPRITE_HEIGHT);
        }
        // left wall
        for (int row = 1; row < this.mapRows - 2; row++) {
            this.backgroundGraphic.drawPixmap(Assets.mapSprite, 0, row * Map.SPRITE_HEIGHT, 192, 64,  Map.SPRITE_WIDTH,  Map.SPRITE_HEIGHT);
        }
        // right wall
        for (int row = 1; row < this.mapRows - 2; row++) {
            this.backgroundGraphic.drawPixmap(Assets.mapSprite, ((this.mapCols-1)  * Map.SPRITE_WIDTH), row * Map.SPRITE_HEIGHT, 320, 64,  Map.SPRITE_WIDTH,  Map.SPRITE_HEIGHT);
        }

        // corners
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 0, 0, 192, 0,  Map.SPRITE_WIDTH,  Map.SPRITE_HEIGHT);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, ((this.mapCols-1)  * Map.SPRITE_WIDTH), 0, 320, 0,  Map.SPRITE_WIDTH,  Map.SPRITE_HEIGHT);
        // corners before bottom line
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 0, ((this.mapRows - 2) * Map.SPRITE_HEIGHT), 512, 128,  Map.SPRITE_WIDTH,  Map.SPRITE_HEIGHT);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, ((this.mapCols-1)  * Map.SPRITE_WIDTH), ((this.mapRows - 2) * Map.SPRITE_HEIGHT), 384, 128,  Map.SPRITE_WIDTH,  Map.SPRITE_HEIGHT);

        // draw objects
        for (int row = 0; row < this.mapRows; row++) {
            for (int col = 0; col < this.mapCols; col++)
            {

                MapCell c = this.fields[row][col];
                if (c == null) continue;

                // draw block on background
                c.drawOnBackground(this.backgroundGraphic);

                // this.g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 0, 64, 64);
            }
        }

        // draw bush

        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 5 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 0, 64, 64, 64);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 5 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 0, 128, 64, 64);

        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 7 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 128, 64, 64, 64);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 7 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 128, 128, 64, 64);

        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 64, 128, 64, 64);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 64, 0, 64, 64);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 3 * Map.SPRITE_HEIGHT, 128, 0, 64, 64);

        this.backgroundGraphic.drawPixmap(Assets.bigStone, 4 * Map.SPRITE_WIDTH, 5 * Map.SPRITE_HEIGHT, 0, 0, 192, 255);

    }

    public void update(Player player, float deltaTime)
    {
        // move map
        this.updateMapPosition(player);

        // update draw position
        this.updateMapOptimizatonFields();

        // update map cells
        this.updateCells(deltaTime);
    }

    /**
     * update all map blocks
     * todo: think about update only screen blocks
     */
    private void updateCells (float deltaTime)
    {
        for (int row = 0; row < this.mapRows; row++)
        {
            for (int col = 0; col <  this.mapCols; col++)
            {
                MapCell c = this.fields[row][col];
                if (c == null) continue;

                c.update(deltaTime, this);
            }
        }
    }

    private void updateMapOptimizatonFields()
    {
        //
        this.drawMinCol = this.getColByLeft(Math.abs(this.x)) - 2;
        if (this.drawMinCol < 0) this.drawMinCol = 0;

        this.drawMaxCol = this.drawMinCol + 13;
        if (this.drawMaxCol > this.mapCols) this.drawMaxCol = this.mapCols;

        this.drawMinRow = this.getRowByTop(Math.abs(this.y)) - 2;
        if (this.drawMinRow < 0) this.drawMinRow = 0;

        this.drawMaxRow = this.drawMinRow + 15;
        if (this.drawMaxRow > this.mapRows) this.drawMaxRow = this.mapRows;
    }

    private void updateMapPosition (Player player)
    {
        // todo fix magic numbers

        // on top
        int topOnScreen = this.screenTopPotion(player.hitBox.top);
        if (topOnScreen < 200)
        {
            // move map on left
            this.y = this.y + (200 - topOnScreen);

            if (this.y > 0) this.y = 0;
        }

        // on the right border
        int rightOnScreen = this.screenLeftPotion(player.hitBox.right);
        if (rightOnScreen > 440)
        {
            // move map on left
            this.x = this.x - (rightOnScreen - 440);

            if (this.x < this.mapMinX) this.x = this.mapMinX;
        }

        // on down
        int bottomScreen = this.screenTopPotion(player.hitBox.bottom);
        if (bottomScreen > 440)
        {
            // move map on left
            this.y = this.y - (bottomScreen - 440);

            if (this.y < this.minMinY) this.y = this.minMinY;
        }

        // on the left border
        int leftOnScreen = this.screenLeftPotion(player.hitBox.left);
        if (leftOnScreen < 200)
        {
            this.x = this.x + (200 - leftOnScreen);

            if (this.x > 0) this.x = 0;
        }
    }

    /**
     * Intersect object with map hitboxes elements
     */
    public boolean isIntersect (FloatRect rectOnMap)
    {
        // check min max position
        if (rectOnMap.left < this.objectMinX) return true;
        if (rectOnMap.top < this.objectMinY) return true;
        if (rectOnMap.right > this.objectMaxX) return true;
        if (rectOnMap.bottom > this.objectMaxY) return true;

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
                // if (!cell.hasHitBox()) continue;

                // check intersect on hitbox
                Rect hitbox = cell.getHitBox();
                if (hitbox == null) continue;

                // todo: make intersect inside rect
                if (hitbox.bottom < rectOnMap.top){
                    continue;
                }
                if (hitbox.top > rectOnMap.bottom){
                    continue;
                }
                if (hitbox.right < rectOnMap.left){
                    continue;
                }
                if (hitbox.left > rectOnMap.right){
                    continue;
                }

                if (cell.isIntersectRectInsideCell(rectOnMap)){
                    return true;
                }
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

        if (col < 0) return false;
        if (row < 0) return false;

        if (row >= this.mapRows) {
            return false;
        }

        if (col >= this.mapCols) {
            return false;
        }

        // take nine sqars

        MapCell cell = this.fields[row][col];
        if (cell == null) return false;
        // if (!cell.hasHitBox()) return false; // if object have no hitbox

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

    @Override
    public void spownEnemy(MapCell spownCell, Enemy enemy)
    {
        if (this.eventsHandler == null) return;;

        // check intersect with map
        if (this.isIntersect(enemy.getHitBox())) {
            return;
        }

        // retrow to world
        this.eventsHandler.spownEnemyOnCell(spownCell, enemy);
    }
}
