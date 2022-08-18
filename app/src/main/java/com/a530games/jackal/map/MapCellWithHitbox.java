package com.a530games.jackal.map;

import android.graphics.Rect;

public abstract class MapCellWithHitbox extends MapCell
{

    protected Rect hitBox;

    public MapCellWithHitbox(int row, int col, int cellWidth, int cellHeight)
    {
        super(row, col);
        this.hitBox = new Rect(col * cellWidth, row * cellHeight, (col * cellWidth) + cellWidth, (row * cellHeight) + cellHeight);
    }

    public Rect getHitBox() {
        return this.hitBox;
    }

}
