package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.map.Map;

public class LeftBottomFuncCorner extends FuncCorner
{
    public LeftBottomFuncCorner(int row, int col)
    {
        super(row, col);
        this.sprite.set(0, 1);
    }

    @Override
    public void drawHitBox(Graphics g, Map map)
    {
        g.drawLine(
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
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
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_HEIGHT ),
                map.screenTopPotion((this.row * Map.SPRITE_HEIGHT) + this.getYByX(Map.SPRITE_WIDTH)),
                this.hitBoxPaint
                );

        // super.drawHitBox(g, map);
    }

    @Override
    float getYByX (float x) {
        return x;
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return mapTop < (this.getYByX(mapLeft - this.hitBox.rect.left) + this.hitBox.rect.top);
        // float leftInsideRect = mapLeft - this.hitBox.left;
        // float yByLeft = this.getYByX(leftInsideRect);
        // return mapTop < (yByLeft + this.hitBox.rect.top);
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap)
    {
        // check right top corner for top right corner
        // return (this.hitBox.rect.top + this.getYByX(rectOnMap.right - this.hitBox.left) > rectOnMap.top);
        return this.isIntersectPointInsideCell(rectOnMap.right, rectOnMap.top);
    }

}
