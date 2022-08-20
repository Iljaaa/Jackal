package com.a530games.jackal.map;

import com.a530games.framework.helpers.FloatRect;

/**
 * fixme: need sprite for top bush
 */
public class RightHalfWall extends Wall
{

    public RightHalfWall(int row, int col)
    {
        super(row, col);

        //
        // this.sprite = new Sprite(Assets.mapSprite, 2, 1);
        this.sprite.set(2, 1);

        this.hitBox.left = (int) Math.ceil( this.hitBox.right - 0.5 * this.sprite.height);
    }

    @Override
    boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        return false;
    }

    /*@Override
    boolean isIntersectRectInsideCell(FloatRect r)
    {
        // che

        return true;
    }*/
}
