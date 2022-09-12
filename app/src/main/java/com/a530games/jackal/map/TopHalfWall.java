package com.a530games.jackal.map;

import com.a530games.framework.helpers.FloatRect;

/**
 * fixme: need sprite for top bush
 */
public class TopHalfWall extends Wall
{

    public TopHalfWall(int row, int col)
    {
        super(row, col);

        this.sprite.set(1, 0);

        this.hitBox.bottom = (int) Math.ceil( this.hitBox.top +  0.5 * this.sprite.height);
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
