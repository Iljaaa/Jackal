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
                map.screenLeftPotion(this.hitBox.rect.left),
                map.screenTopPotion(this.hitBox.rect.top),
                map.screenLeftPotion(this.hitBox.rect.right),
                map.screenTopPotion(this.hitBox.rect.top),
                this.hitBoxPaint
                );

        // bottom line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.rect.centerX()),
                map.screenTopPotion(this.hitBox.rect.bottom),
                map.screenLeftPotion(this.hitBox.rect.right),
                map.screenTopPotion(this.hitBox.rect.bottom),
                this.hitBoxPaint
        );

        // left line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.rect.left),
                map.screenTopPotion(this.hitBox.rect.top),
                map.screenLeftPotion(this.hitBox.rect.left),
                map.screenTopPotion(this.hitBox.rect.centerY()),
                this.hitBoxPaint
        );

        // right line
        g.drawLine(
                map.screenLeftPotion(this.hitBox.rect.right),
                map.screenTopPotion(this.hitBox.rect.top),
                map.screenLeftPotion(this.hitBox.rect.right),
                map.screenTopPotion(this.hitBox.rect.bottom),
                this.hitBoxPaint
                );


        g.drawLine(
                map.screenLeftPotion(this.hitBox.rect.left),
                map.screenTopPotion(this.hitBox.rect.centerY()),
                map.screenLeftPotion(this.hitBox.rect.centerX()),
                map.screenTopPotion(this.hitBox.rect.centerY()),
                this.hitBoxPaint
                );

        g.drawLine(
                map.screenLeftPotion(this.hitBox.rect.centerX()),
                map.screenTopPotion(this.hitBox.rect.centerY()),
                map.screenLeftPotion(this.hitBox.rect.centerX()),
                map.screenTopPotion(this.hitBox.rect.bottom),
                this.hitBoxPaint
                );

        // super.drawHitBox(g, map);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap)
    {
        // check right top connor

        if (rectOnMap.right > this.hitBox.rect.centerX()) {
            return true;
        }

        if (rectOnMap.top < this.hitBox.rect.centerY()) {
            return true;
        }

        return false;
    }
}
