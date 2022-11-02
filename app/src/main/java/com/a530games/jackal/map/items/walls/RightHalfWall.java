package com.a530games.jackal.map.items.walls;

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

        this.hitBox.rect.left = (int) Math.ceil( this.hitBox.rect.right - 0.5 * this.sprite.height);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    /*@Override
    public boolean isIntersectRectInsideCell(FloatRect r)
    {
        // che

        return true;
    }*/
}
