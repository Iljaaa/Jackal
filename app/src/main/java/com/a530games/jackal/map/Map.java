package com.a530games.jackal.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;

import com.a530games.framework.AndroidGraphics;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatPoint;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.levels.Level;
import com.a530games.jackal.objects.Player;
import com.a530games.jackal.objects.enemies.Enemy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Map implements CellEventCallbackHandler
{

    /**
     * sprite height
     * @deprecated use Jackal.BLOCK_HEIGHT
     */
    public static final int SPRITE_HEIGHT = 64;

    /**
     * sprite width
     * @deprecated use Jackal.BLOCK_WIDTH
     */
    public static final int SPRITE_WIDTH = 64;

    // map position
    public Vector2 position;

    /**
     * Max map position
     * calculated on init
     * checked on move map by player position
     */
    private final Vector2 mapMaxPosition;

    /**
     * Screen rect for calculate map position
     */
    private final Rect playerScreenRect;

    // object () min max position
    private int objectMinX = 0, objectMaxX = 0, objectMinY = 0, objectMaxY = 0;

    // map size in blocks
    public int mapRows;
    public int mapCols;

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

    /**
     * Object for follow map
     */
    private Enemy follow = null;

    public Map()
    {
        // this.fields = new MapCell[mapRows][mapCols];
        // this.fields[3][2] = new MapCell();

        this.position = new Vector2(0, 0);
        this.mapMaxPosition = new Vector2();
        this.playerScreenRect = new Rect();
    }

    public void setFollowObject (Enemy followObject) {
        this.follow = followObject;
    }

    /**
     *
     */
    public void setEventHandler(MapEventsHandler eventHandler) {
        this.eventsHandler = eventHandler;
    }

    /**
     * todo: move to math helper
     */
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
     * Prepare map
     * @param mapScreenWidth map screen size round by blocks count
     * @param mapScreenHeight map screen size round by blocks count
     */
    public void init (Level level, Graphics g, Player player, int mapScreenWidth, int mapScreenHeight)
    {
        // map size in cols
        this.mapCols = level.getMapWidthInCols();
        this.mapRows = level.getMapHeightInCols();

        // calculate max map positions`
        // fixme: magic numbers
        this.mapMaxPosition.set(
            -1 * ((this.mapCols * Map.SPRITE_WIDTH) - mapScreenWidth),
            -1 * ((this.mapRows * Map.SPRITE_HEIGHT) - mapScreenHeight)
        );

        // calculate screen rect for move map position
        this.playerScreenRect.set (
            200,
            200,
            g.getWidth() - 200,
            g.getHeight() - 200
        );

        // calculate min|max objects position
        this.objectMinX = Map.SPRITE_WIDTH; // 1 block left
        this.objectMaxX = (this.mapCols * Map.SPRITE_WIDTH) - Map.SPRITE_WIDTH;
        this.objectMinY = Map.SPRITE_HEIGHT; // 1 block top
        this.objectMaxY = (this.mapRows * Map.SPRITE_HEIGHT) - Map.SPRITE_HEIGHT;

        // move player on map position
        // player.hitBox.moveTo(400,1500);
        player.hitBox.moveTo(level.getPlayerStartPosition());

        // calculate start map position
        // after move player
        this.position.x = -1 * (player.hitBox.left - 320 - 20);
        this.position.y = -1 * (player.hitBox.top - 320 - 20);
        if (this.position.x < this.mapMaxPosition.x) this.position.x = (int) Math.ceil(this.mapMaxPosition.x);
        if (this.position.y < this.mapMaxPosition.y) this.position.y = (int) Math.ceil(this.mapMaxPosition.y);

        // init fields
        this.fields = new MapCell[this.mapRows][this.mapCols];

        // update draw limits
        this.updateMapOptimizatonFields();

        //
        // this.addObjectToMap();
        level.addObjectsOnMap(this);

        // big map rect for a drawing
        this.drawRect = new Rect(
                0,
                0,
                this.mapCols * Map.SPRITE_WIDTH,
                this.mapRows * Map.SPRITE_HEIGHT
        );

        this.testCanvas = new Canvas();
        this.testPaint = new Paint();

        this.drawBitmap = Bitmap.createBitmap(this.drawRect.width(), this.drawRect.height(), Bitmap.Config.ARGB_8888);
        this.testCanvas.setBitmap(this.drawBitmap);
        // this.testCanvas.getClipBounds(this.mapRect);

        this.backgroundGraphic = new AndroidGraphics(g.getAssetManager(), this.drawBitmap);

        // draw objects
        // this.b = new BitmapFactory();
        this.testPaint.setStyle(Paint.Style.FILL);
        this.testPaint.setColor(Color.RED);


    }

    /**
     * Level add object on map
     */
    public void addObjectToMap (MapCell c)
    {
        // this.fields[row][col] = new Rock(row, col, type);
        this.fields[c.row][c.col] = c;
    }

    /*private void addRock (int row, int col, int type)
    {
        this.fields[row][col] = new Rock(row, col, type);
    }

    private void addBeach (int row, int col)
    {
        this.fields[row][col] = new Beach(row, col);
    }*/

    /**
     * Drawing background image
     */
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
        // todo: refactor go game objects
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 5 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 0, 64, 64, 64);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 5 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 0, 128, 64, 64);

        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 7 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 128, 64, 64, 64);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 7 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 128, 128, 64, 64);

        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 1 * Map.SPRITE_HEIGHT, 64, 128, 64, 64);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 2 * Map.SPRITE_HEIGHT, 64, 0, 64, 64);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 6 * Map.SPRITE_WIDTH, 3 * Map.SPRITE_HEIGHT, 128, 0, 64, 64);

        this.backgroundGraphic.drawPixmap(Assets.bigStone, 4 * Map.SPRITE_WIDTH, 5 * Map.SPRITE_HEIGHT, 0, 0, 192, 255);
        this.backgroundGraphic.drawPixmap(Assets.house, 12 * Map.SPRITE_WIDTH, 5 * Map.SPRITE_HEIGHT, 0, 0, 150, 100);

    }

    public void update(Player player, float deltaTime)
    {
        // move map
        if (this.follow != null)
        {
            // update map position by follow object
            this.updateMapPosition(this.follow.getHitBox().getCenter());

            // update draw position
            this.updateMapOptimizatonFields();
        }


        // update map cells
        this.updateCells(deltaTime);
    }

    /**
     * update all map blocks
     * todo: think about update only screen blocks
     */
    private void updateCells (float deltaTime)
    {
        boolean isWin = true;

        for (int row = 0; row < this.mapRows; row++)
        {
            for (int col = 0; col <  this.mapCols; col++)
            {
                MapCell c = this.fields[row][col];
                if (c == null) continue;

                c.update(deltaTime, this);

                // check cell win condition
                if (!c.isWin()) isWin = false;
            }
        }

        // this is win
        if (isWin) this.eventsHandler.mapWin();
    }

    private void updateMapOptimizatonFields()
    {
        //
        this.drawMinCol = this.getColByLeft(Math.abs(this.position.x)) - 2;
        if (this.drawMinCol < 0) this.drawMinCol = 0;

        this.drawMaxCol = this.drawMinCol + 13;
        if (this.drawMaxCol > this.mapCols) this.drawMaxCol = this.mapCols;

        this.drawMinRow = this.getRowByTop(Math.abs(this.position.y)) - 2;
        if (this.drawMinRow < 0) this.drawMinRow = 0;

        this.drawMaxRow = this.drawMinRow + 15;
        if (this.drawMaxRow > this.mapRows) this.drawMaxRow = this.mapRows;
    }

    /**
     * Update map position by player
     */
    private void updateMapPosition (FloatPoint playerHitboxCenter)
    {
        // on top
        float playerCenterY = this.screenTopPotionF(playerHitboxCenter.top);
        if (playerCenterY < this.playerScreenRect.top)
        {
            this.position.y = this.position.y + (this.playerScreenRect.top - playerCenterY);
            if (this.position.y > 0) this.position.y = 0;
        }

        // on the left border
        float playerCenterX = this.screenLeftPotionF(playerHitboxCenter.left);
        if (playerCenterX < this.playerScreenRect.left)
        {
            this.position.x = this.position.x + (this.playerScreenRect.left - playerCenterX);
            if (this.position.x > 0) this.position.x = 0;
        }

        // on the right border
        if (playerCenterX > this.playerScreenRect.right)
        {
            this.position.x = this.position.x - (playerCenterX - this.playerScreenRect.right);
            if (this.position.x < this.mapMaxPosition.x) this.position.x = this.mapMaxPosition.x;
        }

        // on down border
        if (playerCenterY > this.playerScreenRect.bottom)
        {
            this.position.y = this.position.y - (playerCenterY - this.playerScreenRect.bottom);
            if (this.position.y < this.mapMaxPosition.y) this.position.y = this.mapMaxPosition.y;
        }
    }

    /**
     * Intersect object (enemies and bullets) with map hitboxes elements
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
        return (int) Math.floor(globalTop + this.position.y);
    }

    public int screenLeftPotion (float globalLeft){
        return (int) Math.floor(globalLeft + this.position.x);
    }

    public float screenTopPotionF (float globalTop){
        return globalTop + this.position.y;
    }

    public float screenLeftPotionF (float globalLeft){
        return globalLeft + this.position.x;
    }

    @Override
    public void spownEnemy(MapCell spownCell, Enemy enemy)
    {
        if (this.eventsHandler == null) return;

        // retrow to world
        this.eventsHandler.spownEnemyOnCell(spownCell, enemy);
    }
}
