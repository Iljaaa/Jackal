package com.a530games.jackal.map;

import android.graphics.Point;

import com.a530games.jackal.Jackal;

public class Cell
{
    public int row, col, width, height;

    /**
     * Left top corner of cell
     */
    protected Point leftTopCorner;

    /**
     * Left top corner of cell
     */
    public Point center;

    public Cell() {
        this(0, 0);
    }

    public Cell(int row, int col) {
        this(row, col, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);
    }

    public Cell(int row, int col, int width, int height)
    {
        this.row = row;
        this.col = col;
        this.width = width;
        this.height = height;

        this.leftTopCorner = new Point (
                // this.col * Jackal.BLOCK_WIDTH,
                this.col * width,
                // this.row * Jackal.BLOCK_HEIGHT
                this.row * height
        );

        this.center = new Point(
                // this.leftTopCorner.x + (int) (Jackal.BLOCK_WIDTH * 0.5),
                this.leftTopCorner.x + (int) (width * 0.5),
                //this.leftTopCorner.y + (int) (Jackal.BLOCK_HEIGHT * 0.5
                this.leftTopCorner.y + (int) (height * 0.5)
        );

    }


}
