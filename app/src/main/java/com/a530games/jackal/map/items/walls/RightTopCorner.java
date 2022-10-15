package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.map.Map;

public class RightTopCorner extends Corner
{

    public RightTopCorner(int row, int col)
    {
        super(row, col);
    }

    @Override
    public void drawHitBox(Graphics g, Map map)
    {
        // top line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.left),
                map.screenTopPotion(this.hitBox.top),
                map.screenLeftPotion(this.hitBox.right),
                map.screenTopPotion(this.hitBox.top),
                this.hitBoxPaint
                );

        // bottom line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.centerX()),
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
                map.screenTopPotion(this.hitBox.centerY()),
                this.hitBoxPaint
        );

        // right line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.right),
                map.screenTopPotion(this.hitBox.top),
                map.screenLeftPotion(this.hitBox.right),
                map.screenTopPotion(this.hitBox.bottom),
                this.hitBoxPaint
                );


        g.drawLine(
                map.screenLeftPotion(this.hitBox.left),
                map.screenTopPotion(this.hitBox.centerY()),
                map.screenLeftPotion(this.hitBox.centerX()),
                map.screenTopPotion(this.hitBox.centerY()),
                this.hitBoxPaint
                );

        g.drawLine(
                map.screenLeftPotion(this.hitBox.centerX()),
                map.screenTopPotion(this.hitBox.centerY()),
                map.screenLeftPotion(this.hitBox.centerX()),
                map.screenTopPotion(this.hitBox.bottom),
                this.hitBoxPaint
                );

        // super.drawHitBox(g, map);
    }

    @Override
    public boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap)
    {
        // check right top connor

        if (rectOnMap.right > this.hitBox.centerX()) {
            return true;
        }

        if (rectOnMap.top < this.hitBox.centerY()) {
            return true;
        }

        return false;
    }
}
