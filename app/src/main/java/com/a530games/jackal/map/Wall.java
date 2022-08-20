package com.a530games.jackal.map;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;

public class Wall extends MapCellWithHitbox
{
    protected Sprite sprite;

    public Wall(int row, int col)
    {
        super(row, col, 64, 64);

        // right half wall
        // this.hitBox.left = (int) Math.ceil( (col + 0.5) * Map.SPRITE_WIDTH);
        // this.hitBox.right = this.hitBox.left + 32;

        this.sprite = new Sprite(Assets.mapSprite, 2, 1);
    }

    @Override
    void drawOnBackground(Graphics g)
    {
        g.drawPixmap(
                this.sprite.image,
                this.col * Map.SPRITE_WIDTH,
                this.row * Map.SPRITE_HEIGHT,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height
        );
    }

    @Override
    void update(float deltaTime) {

    }

    @Override
    public void draw(Graphics g, Map map) {

    }

    @Override
    boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    boolean isIntersectRectInsideCell(FloatRect r)
    {
        // che

        return true;
    }
}
