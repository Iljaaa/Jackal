package com.a530games.jackal.map;

import android.graphics.Rect;

import com.a530games.framework.Graphics;

public abstract class MapCell
{

    public Rect hitBox;

    public MapCell(int row, int col, int cellWidth, int cellHeight)
    {
        this.hitBox = new Rect(col * cellWidth, row * cellHeight, (col * cellWidth) + cellWidth, (row * cellHeight) + cellHeight);
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
     * Check intersect inside rect
     * @param mapLeft left position on map
     * @param mapTop top position on map
     * @return is has intersect pint inside block
     */
    abstract boolean isIntersectPointInsideRect(float mapLeft, float mapTop);
}
