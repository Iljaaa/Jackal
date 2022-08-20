package com.a530games.jackal.map;

import com.a530games.framework.helpers.FloatRect;

/**
 * fixme: need sprite for top bush
 */
public class LeftHalfWall extends Wall
{

    public LeftHalfWall(int row, int col)
    {
        super(row, col);

        //
        // this.sprite = new Sprite(Assets.mapSprite, 2, 1);
        this.sprite.set(0, 1);

        this.hitBox.right = (int) Math.ceil( this.hitBox.left +  0.5 * this.sprite.height);
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
