package com.a530games.jackal.map;

import android.graphics.PointF;
import android.graphics.Rect;

import com.a530games.framework.math.Vector2F;

/**
 * Extended cell by size
 */
public class RectCell extends Map.Cell
{

    /*
     * Cell size
     */
    // public int width, height;

    /*
     * Left top corner of cell
     */
    // private Vector2 _leftTopCorner;

    /*
     * Center of cell
     */
    // private Vector2F _center;

    /**
     *
     */
    protected Rect rect;

    /*public RectCell() {
        this(0, 0, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);
    }*/

    protected RectCell(int col, int row, int width, int height)
    {
        super(col, row);

        this.rect = new Rect(
            col * width,
            row * height,
            col * width + width,
            row * height + height
        );

        // this.width = width;
        // this.height = height;

        // this._leftTopCorner = new Vector2 (this.col * width, this.row * height);

        /*this._center = new Vector2F(
                this.col * width + 0.5f * width,
                this.row * height + 0.5f * width
        );*/

        /*this.center = new Point(
                // this.leftTopCorner.x + (int) (Jackal.BLOCK_WIDTH * 0.5),
                this.leftTopCorner.x + (int) (width * 0.5),
                //this.leftTopCorner.y + (int) (Jackal.BLOCK_HEIGHT * 0.5
                this.leftTopCorner.y + (int) (height * 0.5)
        );*/

    }

    /**
     * Move cell
     */
    public void offsetToCell(Map.Cell cell)
    {
        this.offsetToCell(cell.col, cell.row);
    }

    /**
     * Move cell
     */
    private void offsetToCell(int col, int row)
    {
        this.rect.offsetTo(
            col * this.rect.width(),
            row * this.rect.height()
        );

        this.col = col;
        this.row = row;
    }

    /**
     * Center of cell
     */
    public Vector2F getCenter() {
        return new Vector2F(
                (this.col + 0.5f) * this.rect.width(),
                (this.row + 0.5f) * this.rect.height()
        );
    }


}
