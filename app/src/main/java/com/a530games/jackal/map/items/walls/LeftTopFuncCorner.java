package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.map.Map;

public class LeftTopFuncCorner extends FuncCorner
{
    public LeftTopFuncCorner(int row, int col)
    {
        super(row, col);
    }

    @Override
    public void drawHitBox(Graphics g, Map map)
    {
        g.drawLine(
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion((this.row + 1) * Map.SPRITE_HEIGHT),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_WIDTH),
                map.screenTopPotion((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
        );

        g.drawLine(
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_WIDTH),
                map.screenTopPotion((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
        );

        g.drawLine(
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT + this.getYByX(0)),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_HEIGHT ),
                map.screenTopPotion((this.row * Map.SPRITE_HEIGHT) + this.getYByX(Map.SPRITE_WIDTH)),
                this.hitBoxPaint
        );

    }

    @Override
    float getYByX (float x) {
        return 64 - x;
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return mapTop > (this.getYByX(mapLeft - this.hitBox.rect.left) + this.hitBox.rect.top);
        // float leftInsideRect = mapLeft - this.hitBox.left;
        // float yByLeft = this.getYByX(leftInsideRect);
        // return mapTop > (yByLeft + this.hitBox.rect.top);
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap)
    {
        // check right bottom corner for bottom right corner
        // return (this.hitBox.bottom + this.getYByX(rectOnMap.right - this.hitBox.left) < rectOnMap.bottom);
        return this.isIntersectPointInsideCell(rectOnMap.right, rectOnMap.bottom);
    }

}
