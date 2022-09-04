package com.a530games.jackal.map;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;

abstract public class BigPillarThird extends MapCellWithHitbox
{
    Sprite sprite;

    public BigPillarThird(int row, int col)
    {
        super(row, col, Map.SPRITE_WIDTH, Map.SPRITE_HEIGHT);

        this.hitBox.top = row * Map.SPRITE_HEIGHT + 38;

        this.sprite = new Sprite(Assets.bigPillar, 0, 0);
    }

    @Override
    void drawOnBackground(Graphics g)
    {
        g.drawPixmap(
                this.sprite.image,
                this.hitBox.left,
                this.hitBox.top,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    @Override
    void update(float deltaTime, CellEventCallbackHandler callbackHandler) {

    }

    @Override
    public void draw(Graphics g, Map map) {

    }

    @Override
    public void drawTopLayout(Graphics g, Map map)
    {
        g.drawPixmap(
                this.sprite.image,
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    @Override
    boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        return true;
    }

    @Override
    boolean isIntersectRectInsideCell(FloatRect r) {
        return true;
    }
}
