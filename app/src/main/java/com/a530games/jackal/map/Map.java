package com.a530games.jackal.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.a530games.framework.AndroidGraphics;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.levels.Level;
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
     * map position
     */
    public Vector2F position;

    /**
     * map start cell
     */
    private Cell startCell;

    /**
     * Max map position
     * calculated on init
     * checked on move map by player position
     */
    private final Vector2F mapMaxPosition;

    /**
     * Screen rect for calculate map position
     */
    private final Rect playerScreenRect;

    // object () min max position
    private int objectMinX = 0, objectMaxX = 0, objectMinY = 0, objectMaxY = 0;

    // map size in blocks
    public int mapRows, mapCols;

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

        this.position = new Vector2F(0, 0);
        this.startCell = new Cell();
        this.mapMaxPosition = new Vector2F();
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
     * Prepare map
     * @param mapScreenWidth map screen size in pixels round by blocks count
     * @param mapScreenHeight map screen size round in pixels by blocks count
     */
    public void init (Level level, Graphics g, Player player, DropPad dropPad, int mapScreenWidth, int mapScreenHeight)
    {
        // map size in cols
        this.mapCols = level.getMapWidthInCols();
        this.mapRows = level.getMapHeightInCols();

        // calculate max map positions`
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
        // player.hitBox.moveTo(level.getPlayerStartPosition());

        // calculate start map position
        // after move player
        this.startCell = level.getPlayerDropPointCell();
        this.centerMapOnPoint(this.startCell.center, mapScreenWidth, mapScreenHeight);

        // move drop pad to start
        dropPad.moveToStart(this.startCell);

        // move player out of screen, before his drop from drop pad
        player.moveTo(-100, -100);

        // move player on map position
        // player.hitBox.moveTo(400,1500);
        // player.set();

        // player.hitBox.moveTo(level.getPlayerStartPosition());

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
     * Center map on point
     */
    private void centerMapOnPoint(Point point, int mapScreenWidth, int mapScreenHeight)
    {
        this.position.x = (float) (-1 * (point.x - 0.5 * mapScreenWidth));
        this.position.y = (float) (-1 * (point.y - 0.5 * mapScreenHeight));

        if (this.position.x < this.mapMaxPosition.x) this.position.x = this.mapMaxPosition.x;
        if (this.position.y < this.mapMaxPosition.y) this.position.y = this.mapMaxPosition.y;
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
    public void drawBackground()
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

        // draw map start circle
        this.backgroundGraphic.drawPixmap(Assets.dropPoint, this.startCell.leftTopCorner.x, this.startCell.leftTopCorner.y);

        // draw objects
        for (int row = 0; row < this.mapRows; row++) {
            for (int col = 0; col < this.mapCols; col++)
            {

                MapCell c = this.fields[row][col];
                if (c == null) continue;

                // draw block on background
                c.drawOnBackground(this.backgroundGraphic, this);
            }
        }


        this.drawBackgroundNet(this.backgroundGraphic);
    }

    /**
     *
     */
    private void drawBackgroundNet(Graphics g)
    {
        // draw title backend
        for (int row = 1; row < this.mapRows; row++) {
            g.drawLine(
                    0, row * Jackal.BLOCK_HEIGHT,
                    g.getWidth(), row * Jackal.BLOCK_HEIGHT,
                    Color.GREEN
            );
        }

        for (int col = 1; col < this.mapCols; col++) {
            g.drawLine(
                    col * Jackal.BLOCK_WIDTH, 0,
                    col * Jackal.BLOCK_WIDTH, g.getHeight(),
                    Color.GREEN
            );
        }
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
        this.drawMinCol = this.getColByLeft(this.position.x) - 2;
        if (this.drawMinCol < 0) this.drawMinCol = 0;

        this.drawMaxCol = this.drawMinCol + 13;
        if (this.drawMaxCol > this.mapCols) this.drawMaxCol = this.mapCols;

        this.drawMinRow = this.getRowByTop(this.position.y) - 2;
        if (this.drawMinRow < 0) this.drawMinRow = 0;

        this.drawMaxRow = this.drawMinRow + 15;
        if (this.drawMaxRow > this.mapRows) this.drawMaxRow = this.mapRows;
    }

    /**
     * Update map position by player
     */
    private void updateMapPosition (PointF playerHitboxCenter)
    {
        // on top
        float playerCenterY = this.screenTopPotionF(playerHitboxCenter.y);
        if (playerCenterY < this.playerScreenRect.top)
        {
            this.position.y = this.position.y + (this.playerScreenRect.top - playerCenterY);
            if (this.position.y > 0) this.position.y = 0;
        }

        // on the left border
        float playerCenterX = this.screenLeftPotionF(playerHitboxCenter.x);
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
        // check is out of map, there is bedrock
        if (rectOnMap.left < this.objectMinX) return true;
        if (rectOnMap.top < this.objectMinY) return true;
        if (rectOnMap.right > this.objectMaxX) return true;
        if (rectOnMap.bottom > this.objectMaxY) return true;

        // for left top take 3 top
        int row = this.getRowByTop(rectOnMap.top);
        int col = this.getColByLeft(rectOnMap.left);
//        int row = (int) Math.floor(rectOnMap.top / Map.SPRITE_HEIGHT);
//        int col = (int) Math.floor(rectOnMap.left / Map.SPRITE_WIDTH);

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

                // check global intersect
                if (!HitBox.isIntersectsTwoRect(hitbox, rectOnMap)){
                    return false;
                }

                // todo: make intersect inside rect
                /*if (hitbox.bottom < rectOnMap.top){
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

        return cell.isIntersectPointInsideCell(left, top);
    }

    /**
     * Row by top position
     */
    public static int getRowByTop(float top) {
        // return (int) Math.floor(top / Map.SPRITE_HEIGHT);
        return (int) Math.floor(top / Jackal.BLOCK_HEIGHT);
    }

    /**
     * Col by left position
     */
    public static int getColByLeft(float left) {
        // return (int) Math.floor(left / Map.SPRITE_WIDTH);
        return (int) Math.floor(left / Jackal.BLOCK_WIDTH);
    }

    /**
     * Get row coords
     */
    public static int getTopByRow (int row) {
        return row * Jackal.BLOCK_HEIGHT;
    }

    /**
     * Col coords
     */
    public static int getLeftByCol (int col) {
        return col * Jackal.BLOCK_WIDTH;
    }

    /**
     * Convert screen position by map position
     * @param mapTop Map top position
     * @return Screen top position
     */
    public int screenTopPotion (float mapTop) {
        return (int) Math.floor(mapTop + this.position.y);
    }

    /**
     * Convert screen position by map position in int
     * @param mapLeft Map left position
     * @return Screen let position
     */
    public int screenLeftPotion (float mapLeft) {
        return (int) Math.floor(mapLeft + this.position.x);
    }

    /**
     * Convert screen position by map position in int
     * @param mapTop Map top position
     * @return Screen top position
     */
    public float screenTopPotionF (float mapTop){
        return mapTop + this.position.y;
    }

    /**
     * Convert screen position by map position
     * @param mapLeft Map left position
     * @return Screen let position
     */
    public float screenLeftPotionF (float mapLeft){
        return mapLeft + this.position.x;
    }

    @Override
    public void spownEnemy(MapCell spownCell, Enemy enemy)
    {
        if (this.eventsHandler == null) return;

        // retrow to world
        this.eventsHandler.spownEnemyOnCell(spownCell, enemy);
    }
}
