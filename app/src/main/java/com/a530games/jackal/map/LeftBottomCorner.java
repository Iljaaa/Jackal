package com.a530games.jackal.map;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;

public class LeftBottomCorner extends Corner
{

    public LeftBottomCorner(int row, int col)
    {
        super(row, col);
        this.sprite.set(0, 1);
    }

    @Override
    public void drawHitBox(Graphics g, Map map)
    {
        // top line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.left),
                map.screenTopPotion(this.hitBox.top),
                map.screenLeftPotion(this.hitBox.centerX()),
                map.screenTopPotion(this.hitBox.top),
                this.hitBoxPaint
                );

        // bottom line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.left),
                map.screenTopPotion(this.hitBox.bottom),
                map.screenLeftPotion(this.hitBox.right),
                map.screenTopPotion(this.hitBox.bottom),
                this.hitBoxPaint
        );

        // left line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.left),
                map.screenTopPotion(this.hitBox.top),
                map.screenLeftPotion(this.hitBox.left),
                map.screenTopPotion(this.hitBox.bottom),
                this.hitBoxPaint
        );

        // right line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.right),
                map.screenTopPotion(this.hitBox.centerY()),
                map.screenLeftPotion(this.hitBox.right),
                map.screenTopPotion(this.hitBox.bottom),
                this.hitBoxPaint
                );


        g.drawLine(
                map.screenLeftPotion(this.hitBox.centerX()),
                map.screenTopPotion(this.hitBox.centerY()),
                map.screenLeftPotion(this.hitBox.right),
                map.screenTopPotion(this.hitBox.centerY()),
                this.hitBoxPaint
                );

        g.drawLine(
                map.screenLeftPotion(this.hitBox.centerX()),
                map.screenTopPotion(this.hitBox.top),
                map.screenLeftPotion(this.hitBox.centerX()),
                map.screenTopPotion(this.hitBox.centerY()),
                this.hitBoxPaint
                );


        // super.drawHitBox(g, map);
    }

    @Override
    boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    boolean isIntersectRectInsideCell(FloatRect rectOnMap)
    {
        // check left bottom connor

        if (rectOnMap.left < this.hitBox.centerX()) {
            return true;
        }

        if (rectOnMap.bottom > this.hitBox.centerY()) {
            return true;
        }

        return false;
    }
}
