package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.map.Map;

public class RightTopFuncCorner extends FuncCorner
{
    public RightTopFuncCorner(int row, int col)
    {
        super(row, col);
        this.sprite.set(1, 0);
    }

    @Override
    public void drawHitBox(Graphics g, Camera2D camera)
    {
        g.drawLine(
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop((this.row + 1) * Map.SPRITE_HEIGHT),
                camera.screenLeft((this.col + 1) * Map.SPRITE_WIDTH),
                camera.screenTop((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
        );

        g.drawLine(
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT),
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
        );

        g.drawLine(
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT + this.getYByX(0)),
                camera.screenLeft((this.col + 1) * Map.SPRITE_HEIGHT ),
                camera.screenTop((this.row * Map.SPRITE_HEIGHT) + this.getYByX(Map.SPRITE_WIDTH)),
                this.hitBoxPaint
        );

    }

    float getYByX (float x) {
        return x;
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return mapTop > (this.getYByX(mapLeft - this.hitBox.rect.left) + this.hitBox.rect.top);
        // float leftInsideRect = mapLeft - this.hitBox.left;
        // float yByLeft = this.getYByX(leftInsideRect);
        // return mapTop > (yByLeft + this.hitBox.rect.top);
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox rectOnMap)
    {
        // check left bottom corner for left bottom corner
        return this.isIntersectPointInsideCell(rectOnMap.rect.left, rectOnMap.rect.bottom);
    }

}
