package com.a530games.jackal.map;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;

public abstract class MapCell
{
    protected int row, col;

    protected Paint hitBoxPaint;

    public MapCell(int row, int col)
    {
        this.row = row;
        this.col = col;

        this.hitBoxPaint = new Paint();
        this.hitBoxPaint.setStyle(Paint.Style.STROKE);
        this.hitBoxPaint.setStrokeWidth(1);
        this.hitBoxPaint.setColor(Color.YELLOW);
    }

    /**
     * Draw block on background
     * @param g Graphic object
     */
    abstract void drawOnBackground(Graphics g);

    /**
     * Update not static block before craw
     */
    abstract void update(float deltaTime);

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
    abstract boolean isIntersectPointInsideRect(float mapLeft, float mapTop);

    /**
     * Check intersect inside rect
     * @param rectOnMap Rect in map coordinate system to check
     * @return is intersected
     */
    abstract boolean isIntersectRectInsideCell (FloatRect rectOnMap);
}
