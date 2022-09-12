package com.a530games.jackal.map;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;

abstract public class BigPillarTop extends MapCell
{
    Sprite sprite;

    public BigPillarTop(int row, int col)
    {
        super(row, col);

        this.sprite = new Sprite(Assets.bigPillar, 0, 0);
    }

    @Override
    void drawOnBackground(Graphics g) {

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
    public void drawHitBox(Graphics g, Map map) {

    }

    @Override
    boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    boolean isIntersectRectInsideCell(FloatRect r) {
        return false;
    }
}