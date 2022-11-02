package com.a530games.jackal.map.items.walls;

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

        this.hitBox.rect.bottom = (int) Math.ceil( this.hitBox.rect.top +  0.5 * this.sprite.height);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect r) {
        return true;
    }
}
