package com.a530games.jackal.map;

import com.a530games.framework.helpers.FloatRect;

/**
 */
public class BottomHalfWall extends Wall
{

    public BottomHalfWall(int row, int col)
    {
        super(row, col);

        this.sprite.set(1, 2);

        this.hitBox.top = (int) Math.ceil( this.hitBox.top +  0.5 * this.sprite.height);
    }

    @Override
    boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    boolean isIntersectRectInsideCell(FloatRect r) {
        return true;
    }
}
