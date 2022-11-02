package com.a530games.jackal.map;

import android.graphics.Color;
import android.graphics.Paint;

import com.a530games.framework.Graphics;
import com.a530games.jackal.Jackal;

public abstract class MapCellWithHitbox extends RectCell implements MapObject
{
    protected MapHitBox hitBox;
    /**
     * Paint for hit box
     */
    protected Paint hitBoxPaint;

    public MapCellWithHitbox(int row, int col)
    {
        super(row, col, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);
        /*this.hitBox = new MapHitBox(
                col * cellWidth,
                row * cellHeight,
                (col * cellWidth) + cellWidth,
                (row * cellHeight) + cellHeight
        );*/
        this.hitBox = new MapHitBox(this);

        this.hitBoxPaint = new Paint();
        this.hitBoxPaint.setStyle(Paint.Style.STROKE);
        this.hitBoxPaint.setStrokeWidth(1);
        this.hitBoxPaint.setColor(Color.YELLOW);
    }

    public MapHitBox getHitBox() {
        return this.hitBox;
    }

    /**
     * Is cell conditions success for win
     * @return boolean is success
     */
    public boolean isWin() {
        return true;
    }

    /**
     *
     * @param g Graphic object
     * @param map Map object
     */
    public void drawHitBox(Graphics g, Map map)
    {
        g.drawRect(
                map.screenLeftPotion(this.hitBox.rect.left),
                map.screenTopPotion(this.hitBox.rect.top),
                this.hitBox.rect.width(),
                this.hitBox.rect.height(),
                this.hitBoxPaint
        );
    }
}
