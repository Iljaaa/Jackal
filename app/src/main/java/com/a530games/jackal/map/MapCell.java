package com.a530games.jackal.map;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Jackal;

public abstract class MapCell extends RectCell
{
    /**
     * Paint for hit box
     */
    protected Paint hitBoxPaint;

    public MapCell(int row, int col)
    {
        super(row, col, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);

        this.hitBoxPaint = new Paint();
        this.hitBoxPaint.setStyle(Paint.Style.STROKE);
        this.hitBoxPaint.setStrokeWidth(1);
        this.hitBoxPaint.setColor(Color.YELLOW);
    }

    /**
     * Draw block on background
     * @param g Graphic object
     * @param map
     */
    public abstract void drawOnBackground(Graphics g, Map map);

    /**
     * Update not static block before craw
     */
    public abstract void update(float deltaTime, CellEventCallbackHandler callbackHandler);

    /**
     * Draw block
     * @param g Graphic object
     */
    public abstract void draw(Graphics g, Map map);

    /**
     * Draw hitbox
     * @param g Graphic object
     * @param map Map object
     */
    public abstract void drawHitBox(Graphics g, Map map);

    /**
     * Draw objects over plyer
     */
    public abstract void drawTopLayout (Graphics g, Map map);

    /**
     * Return not exists hitbox map
     * @return Hitbox rect
     */
    public Rect getHitBox() {
        return null;
    }

    /**
     * Check intersect inside rect
     * @param mapLeft left position on map
     * @param mapTop top position on map
     * @return is has intersect pint inside block
     */
    public abstract boolean isIntersectPointInsideCell(float mapLeft, float mapTop);

    /**
     * Check intersect inside rect
     * @param rectOnMap Rect in map coordinate system to check
     * @return is intersected
     */
    public abstract boolean isIntersectRectInsideCell (FloatRect rectOnMap);

    /**
     * Is cell conditions success for win
     * @return boolean is success
     */
    public boolean isWin() {
        return true;
    }
}
