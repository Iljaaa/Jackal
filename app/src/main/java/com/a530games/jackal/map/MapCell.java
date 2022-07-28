package com.a530games.jackal.map;

import android.graphics.Rect;

import com.a530games.jackal.Sprite;

public class MapCell
{
    public static final int MOVE_ROCK_1 = 1;
    public static final int MOVE_ROCK_2 = 2;
    public static final int MOVE_ROCK_3 = 3;

    public static final int MOVE_BUSH_1 = 10;
    public static final int MOVE_BUSH_2 = 11;
    public static final int MOVE_BUSH_3 = 12;
    public static final int MOVE_BUSH_4 = 13;

    public boolean isRock = false;

    public Rect hitBox;

    public int type;

    public MapCell(int row, int col, int cellSize, int type)
    {
        this.type = type;

        /*this.row = row;
        this.col = col;*/

        // create hitbox
        this.hitBox = new Rect(col * cellSize, row * cellSize, (col * cellSize) + cellSize, (row * cellSize) + cellSize);
    }
}
