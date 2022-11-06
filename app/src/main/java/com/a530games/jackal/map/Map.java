package com.a530games.jackal.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.a530games.framework.AndroidGraphics;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.levels.Level;
import com.a530games.jackal.map.items.DropPadCell;
import com.a530games.jackal.objects.DropPad;
import com.a530games.jackal.objects.Player;
import com.a530games.jackal.objects.enemies.Enemy;

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

    /**
     * map size in blocks
     */
    public int mapRows, mapCols;

    /**
     * Map block size
     */
    public int blockWidth, blockHeight;

    /*
     * map position
     */
    // public Vector2F position;

    /**
     * map start cell
     */
    public MapCell startCell;

    /*
     * Max map position
     * calculated on init
     * checked on move map by player position
     * todo: refactor to camera object
     */
    // private final Vector2F mapMaxPosition;

    /*
     * Screen rect for calculate map position
     */
    // private final Rect playerScreenRect;

    // object () min max position
    // todo: refactor to rect
    // private int objectMinX = 0, objectMaxX = 0, objectMinY = 0, objectMaxY = 0;

    /**
     * Min and max for all objects
     */
    public Rect objectsFrame;

    // map optimization min|max positions for draw
    // public int drawMinCol = 0, drawMaxCol = 0, drawMinRow = 0, drawMaxRow = 0;

    /**
     * Paint for active cell
     */
    public Paint activeCellPaint;

    /**
     * Min sell struct
     */
    public static class Cell
    {
        public int col, row;

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

    Rect drawRect;
    Canvas testCanvas;
    Paint testPaint;

    public Bitmap drawBitmap;

    Graphics backgroundGraphic;

    private MapEventsHandler eventsHandler = null;

    /**
     * fields [row][col]
     */
    public MapObject[][] fields; // = new MapCell[1][1];

    /**
     * Object for follow map
     */
    // private Player follow = null;

    public Map(int blockWidth, int blockHeight)
    {
        // this.fields = new MapCell[mapRows][mapCols];
        // this.fields[3][2] = new MapCell();
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;

        // this.position = new Vector2F(0, 0);
        this.startCell = new DropPadCell(0, 0);
        // this.mapMaxPosition = new Vector2F();
        // this.playerScreenRect = new Rect();

        this.objectsFrame = new Rect();

        this.activeCellPaint = new Paint();
        this.activeCellPaint.setStyle(Paint.Style.FILL);
        this.activeCellPaint.setColor(Color.YELLOW);
    }

    /*public void setFollowObject (Player followObject) {
        this.follow = followObject;
    }*/

    /**
     *
     */
    public void setEventHandler(MapEventsHandler eventHandler) {
        this.eventsHandler = eventHandler;
    }

    /**
     * Prepare map
     */
    public void init (Level level, Graphics g, Player player, DropPad dropPad)
    {
        // map size in cols
        this.mapCols = level.getMapCols();
        this.mapRows = level.getMapRows();

        // calculate max map positions`
        // fixme: need move to camera
        /*this.mapMaxPosition.set(
            -1 * ((this.mapCols * Map.SPRITE_WIDTH) - mapScreenWidth),
            -1 * ((this.mapRows * this.blockHeight) - mapScreenHeight)
        );*/

        // calculate screen rect for move map position
        /*this.playerScreenRect.set (
            200,
            200,
            g.getFrameBufferWidth() - 200,
            g.getFrameBufferHeight() - 200
        );*/

        // calculate min|max objects position
        // this.objectMinX = this.blockWidth; // 1 block left
        // this.objectMaxX = (this.mapCols * this.blockWidth) - this.blockWidth;
        // this.objectMinY = this.blockHeight; // 1 block top
        // this.objectMaxY = (this.mapRows * this.blockHeight) - this.blockHeight;

        this.objectsFrame.set(
            this.blockWidth, // 1 block left
            this.blockHeight, // 1 block top
            (this.mapCols * this.blockWidth) - this.blockWidth,
            (this.mapRows * this.blockHeight) - this.blockHeight
        );

        // move player on map position
        // player.hitBox.moveTo(400,1500);
        // player.hitBox.moveTo(level.getPlayerStartPosition());

        // calculate start map position
        // after move player
        this.startCell.offsetToCell(level.getPlayerDropPointCell());
        //this.centerMapOnPoint(this.startCell.getCenter(), mapScreenWidth, mapScreenHeight);

        // move drop pad to start
        dropPad.moveToStart(this.startCell);

        // move player out of screen, before his drop from drop pad
        player.offsetCenterTo(-100, -100);

        // move player on map position
        // player.hitBox.moveTo(400,1500);
        // player.set();

        // player.hitBox.moveTo(level.getPlayerStartPosition());

        // init fields
        this.fields = new MapObject[this.mapRows][this.mapCols];

        // update draw limits
        // this.updateMapOptimizatonFields();

        //
        // this.addObjectToMap();
        level.addObjectsOnMap(this);

        // big map rect for a drawing
        this.drawRect = new Rect(
                0,
                0,
                this.mapCols * this.blockWidth,
                this.mapRows * this.blockHeight
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

    /*
     * Center map on point

    private void centerMapOnPoint(Vector2F point, int mapScreenWidth, int mapScreenHeight)
    {
        this.position.x = (float) (-1 * (point.x - 0.5 * mapScreenWidth));
        this.position.y = (float) (-1 * (point.y - 0.5 * mapScreenHeight));

        if (this.position.x < this.mapMaxPosition.x) this.position.x = this.mapMaxPosition.x;
        if (this.position.y < this.mapMaxPosition.y) this.position.y = this.mapMaxPosition.y;
    }*/

    /**
     * Level add object on map
     */
    public void addObjectToMap (MapObject c, int col, int row)
    {
        // this.fields[row][col] = new Rock(row, col, type);
        this.fields[row][col] = c;
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
    public void drawBackground()
    {
        // this.g.drawRect(100, 100, 200, 200, Color.RED);

        // draw title backend
        for (int row = 0; row < this.mapRows; row++) {
            for (int col = 0; col < this.mapCols; col++) {
                this.backgroundGraphic.drawPixmap(Assets.mapSprite, col * this.blockWidth, row * this.blockHeight, 0, 0, 65, 65);
            }
        }

        // draw walls

        // top wall
        for (int col = 1; col < this.mapCols - 1; col++) {
            this.backgroundGraphic.drawPixmap(Assets.mapSprite, col * this.blockWidth, 0, 256, 0,  this.blockWidth,  this.blockHeight);
        }

        // left wall
        for (int row = 1; row < this.mapRows - 2; row++) {
            this.backgroundGraphic.drawPixmap(Assets.mapSprite, 0, row * this.blockHeight, 192, 64,  this.blockWidth,  this.blockHeight);
        }
        // right wall
        for (int row = 1; row < this.mapRows - 2; row++) {
            this.backgroundGraphic.drawPixmap(Assets.mapSprite, ((this.mapCols-1)  * this.blockWidth), row * this.blockHeight, 320, 64,  this.blockWidth,  this.blockHeight);
        }

        // corners
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 0, 0, 192, 0,  this.blockWidth,  this.blockHeight);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, ((this.mapCols-1)  * this.blockWidth), 0, 320, 0,  this.blockWidth,  this.blockHeight);

        // corners before bottom line
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, 0, ((this.mapRows - 2) * this.blockHeight), 512, 128,  this.blockWidth,  this.blockHeight);
        this.backgroundGraphic.drawPixmap(Assets.mapSprite, ((this.mapCols-1)  * this.blockWidth), ((this.mapRows - 2) * this.blockHeight), 384, 128,  this.blockWidth,  this.blockHeight);

        // draw map start circle
        // Vector2 startCellLeftTopCorner = this.startCell.col
        this.backgroundGraphic.drawPixmap(
                Assets.dropPoint,
                this.startCell.col * this.blockWidth, // startCellLeftTopCorner.x,
                this.startCell.row * this.blockHeight //startCellLeftTopCorner.y
        );

        // draw objects
        this.drawBackgroundObjects();

        // drwa background net
        // this.drawBackgroundNet(this.backgroundGraphic);
    }

    /**
     * Draw map cells
     */
    private void drawBackgroundObjects()
    {
        for (int row = 0; row < this.mapRows; row++) {
            for (int col = 0; col < this.mapCols; col++)
            {

                MapObject c = this.fields[row][col];
                if (c == null) continue;

                // draw block on background
                c.drawOnBackground(this.backgroundGraphic, this);
            }
        }
    }

    /**
     * Draw net on background
     */
    private void drawBackgroundNet(Graphics g)
    {
        // draw title backend
        for (int row = 1; row < this.mapRows; row++) {
            g.drawLine(
                    0, row * Jackal.BLOCK_HEIGHT,
                    g.getFrameBufferWidth(), row * Jackal.BLOCK_HEIGHT,
                    Color.GREEN
            );
        }

        for (int col = 1; col < this.mapCols; col++) {
            g.drawLine(
                    col * Jackal.BLOCK_WIDTH, 0,
                    col * Jackal.BLOCK_WIDTH, g.getFrameBufferHeight(),
                    Color.GREEN
            );
        }
    }

    /**
     * High lite cell on position

    public void highlightCellByPoint(Graphics g, float x, float y)
    {
        Map.Cell cell = this.getCellByPosition(x, y);
        // int top = this.world.map.screenTopPotion(this.world.map.getTopByRow(playerCell.row));
        // int left = this.world.map.screenLeftPotion(this.world.map.getLeftByCol(playerCell.col));

        g.drawRect(
                this.screenLeftPotion(this.getLeftByCol(cell.col)), // left, // this.world.map.screenLeftPotion(playerCell.col),  // left,
                this.screenTopPotion(this.getTopByRow(cell.row)), // top, // this.world.map.screenTopPotion(playerCell.row), // top,
                this.blockWidth,
                this.blockHeight,
                this.activeCellPaint
        );
    }*/

    public void update(float deltaTime)
    {
        // move map
        /*if (this.follow != null)
        {
            // update map position by follow object
            // HitBox followHitBox = this.follow.hitBox;
            // this.updateMapPosition(followHitBox.getCenterX(), followHitBox.getCenterX());

            // update draw position
            // this.updateMapOptimizatonFields();
        }*/


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
                MapObject c = this.fields[row][col];
                if (c == null) continue;

                c.update(deltaTime, this);

                // check cell win condition
                if (!c.isWin()) isWin = false;
            }
        }

        // this is win
        if (isWin) this.eventsHandler.mapWin();
    }

    /*private void updateMapOptimizatonFields()
    {
        //
        this.drawMinCol = this.getColByLeft(this.position.x) - 2;
        if (this.drawMinCol < 0) this.drawMinCol = 0;

        this.drawMaxCol = this.drawMinCol + 13;
        if (this.drawMaxCol > this.mapCols) this.drawMaxCol = this.mapCols;

        this.drawMinRow = this.getRowByTop(this.position.y) - 2;
        if (this.drawMinRow < 0) this.drawMinRow = 0;

        this.drawMaxRow = this.drawMinRow + 15;
        if (this.drawMaxRow > this.mapRows) this.drawMaxRow = this.mapRows;
    }*/

    /*
     * Update map position by player

    private void updateMapPosition (float left, float top)
    {
        // on top
        float playerCenterY = this.screenTopPotionF(top);
        if (playerCenterY < this.playerScreenRect.top)
        {
            this.position.y = this.position.y + (this.playerScreenRect.top - playerCenterY);
            if (this.position.y > 0) this.position.y = 0;
        }

        // on the left border
        float playerCenterX = this.screenLeftPotionF(left);
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
    }*/

    /**
     * Intersect object (enemies and bullets) with map hitboxes elements
     */
    public boolean isIntersect (HitBox rectOnMap)
    {
        // check is out of map, there is bedrock
        if (rectOnMap.rect.left < this.objectsFrame.left) return true;
        if (rectOnMap.rect.top < this.objectsFrame.top) return true;
        if (rectOnMap.rect.right > this.objectsFrame.right) return true;
        if (rectOnMap.rect.bottom > this.objectsFrame.bottom) return true;
        // if (rectOnMap.rect.left < this.objectMinX) return true;
        // if (rectOnMap.rect.top < this.objectMinY) return true;
        // if (rectOnMap.rect.right > this.objectMaxX) return true;
        // if (rectOnMap.rect.bottom > this.objectMaxY) return true;


        // for left top take 3 top
        int row = this.getRowByTop(rectOnMap.rect.top);
        int col = this.getColByLeft(rectOnMap.rect.left);
//        int row = (int) Math.floor(rectOnMap.top / this.blockHeight);
//        int col = (int) Math.floor(rectOnMap.left / this.blockWidth);

        // take nine sqars
        for (int forCol = col - 1; forCol <= col + 1; forCol++) {
            for (int forRow = row - 1; forRow <= row + 1; forRow++)
            {
                if (forCol < 0) continue;
                if (forRow < 0) continue;
                if (forCol >= this.mapCols) continue;
                if (forRow >= this.mapRows) continue;

                MapObject cell = this.fields[forRow][forCol];
                if (cell == null) continue;
                // if (!cell.hasHitBox()) continue;

                // check intersect on mapHitbox
                MapHitBox mapHitbox = cell.getHitBox();
                if (mapHitbox == null) continue;

                // check global intersect
                // if (!HitBox.isIntersectsTwoRect(mapHitbox, rectOnMap)){
                if (!mapHitbox.isIntersectsWithHitbox(rectOnMap)){
                    return false;
                }

                // todo: make intersect inside rect
                /*if (mapHitbox.bottom < rectOnMap.top){
                    continue;
                }
                if (mapHitbox.top > rectOnMap.bottom){
                    continue;
                }
                if (mapHitbox.right < rectOnMap.left){
                    continue;
                }
                if (mapHitbox.left > rectOnMap.right){
                    continue;
                }*/

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
        // check min value
        if (left < this.objectsFrame.left) return true;
        if (top < this.objectsFrame.top) return true;
        if (top > this.objectsFrame.bottom) return true;
        if (left > this.objectsFrame.right) return true;

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

        MapObject cell = this.fields[row][col];
        if (cell == null) return false;
        // if (!cell.hasHitBox()) return false; // if object have no hitbox

        return cell.isIntersectPointInsideCell(left, top);
    }

    /*
     * Get cell by position on map
     * @return Cell

    public Map.Cell getCellByPointF(PointF point) {
        return this.getCellByPosition(point.x, point.y);
    }*/

    /**
     * Get cell by position on map
     * @return Cell
     */
    public Map.Cell getCellByPosition (float left, float top) {
        return new Map.Cell(
                this.getColByLeft(left),
                this.getRowByTop(top)
        );
    }

    /*
     * @deprecated
     * Row by top position
     *
    public static int getRowByTop(float top) {
        return (int) Math.floor(top / Jackal.BLOCK_HEIGHT);
    }

    /*
     * @deprecated
     * Col by left position
     *
    public static int getColByLeft(float left) {
        // return (int) Math.floor(left / this.blockWidth);
        return (int) Math.floor(left / Jackal.BLOCK_WIDTH);
    }*/

    /**
     * Row by top position
     */
    public int getRowByTop(float top) {
        return (int) Math.floor(top / this.blockHeight);
    }

    /**
     * Col by left position
     */
    public int getColByLeft(float left) {
        return (int) Math.floor(left / this.blockWidth);
    }

    /**
     * Get row coords
     */
    public int getTopByRow (int row) {
        return row * this.blockHeight; // Jackal.BLOCK_HEIGHT;
    }

    /**
     * Col coords
     */
    public int getLeftByCol (int col) {
        return col * this.blockWidth; // Jackal.BLOCK_WIDTH;
    }

    /*
     * Convert screen position by map position
     * @param mapTop Map top position
     * @return Screen top position
     *
    public int screenTopPotion (float mapTop) {
        return (int) Math.floor(mapTop + this.position.y);
    }*/

    /*
     * Convert screen position by map position in int
     * @param mapLeft Map left position
     * @return Screen let position
     *
    public int screenLeftPotion (float mapLeft) {
        return (int) Math.floor(mapLeft + this.position.x);
    }*/

    /*
     * Convert screen position by map position in int
     * @param mapTop Map top position
     * @return Screen top position
     *
    public float screenTopPotionF (float mapTop){
        return mapTop + this.position.y;
    }*/

    /*
     * Convert screen position by map position
     * @param mapLeft Map left position
     * @return Screen let position
     *
    public float screenLeftPotionF (float mapLeft){
        return mapLeft + this.position.x;
    }*/

    @Override
    public void spownEnemy(MapCell spownCell, Enemy enemy)
    {
        if (this.eventsHandler == null) return;

        // retrow to world
        this.eventsHandler.spownEnemyOnCell(spownCell, enemy);
    }
}
