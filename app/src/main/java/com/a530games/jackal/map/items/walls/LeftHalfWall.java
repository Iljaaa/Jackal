package com.a530games.jackal.map.items.walls;

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

        this.hitBox.rect.right = (int) Math.ceil( this.hitBox.rect.left +  0.5 * this.sprite.height);
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
