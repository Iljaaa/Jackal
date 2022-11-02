package com.a530games.jackal.map.items.walls;

import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.HitBox;

/**
 */
public class BottomHalfWall extends Wall
{

    public BottomHalfWall(int row, int col)
    {
        super(row, col);

        this.sprite.set(1, 2);

        this.hitBox.rect.top = (int) Math.ceil( this.hitBox.rect.top +  0.5 * this.sprite.height);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox r) {
        return true;
    }
}
