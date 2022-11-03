package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.map.Map;

public class RightBottomFuncCorner extends FuncCorner
{
    public RightBottomFuncCorner(int row, int col)
    {
        super(row, col);
        this.sprite.set(1, 1);
    }

    @Override
    public void drawHitBox(Graphics g, Camera2D camera)
    {
        g.drawLine(
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT),
                camera.screenLeft((this.col + 1) * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT),
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

        // super.drawHitBox(g, map);
    }

    @Override
    float getYByX (float x) {
        return 64 - x;
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return mapTop < (this.getYByX(mapLeft - this.hitBox.rect.left) + this.hitBox.rect.top);
        // float leftInsideRect = mapLeft - this.hitBox.rect.left;
        // float yByLeft = this.getYByX(leftInsideRect);
        // return mapTop < (yByLeft + this.hitBox.rect.rect.top);
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox rectOnMap)
    {
        // check left top corner for top left corner
        // return (this.hitBox.rect.top + this.getYByX(rectOnMap.left - this.hitBox.rect.left) > rectOnMap.top);
        return this.isIntersectPointInsideCell(rectOnMap.rect.left, rectOnMap.rect.top);
    }

}
