package com.a530games.jackal.map.items.house;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;

abstract public class House extends MapCellWithHitbox
{
    Sprite sprite;

    public House(int row, int col) {
        super(row, col, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);

        this.sprite = new Sprite(Assets.house, 0, 1);
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
    {
        this._draw(g, this.leftTopCorner.x, this.leftTopCorner.y);
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {

    }

    @Override
    public void draw(Graphics g, Map map) {

    }

    @Override
    public void drawTopLayout(Graphics g, Map map)
    {
        this._draw(g, map.screenLeftPotion(this.leftTopCorner.x), map.screenTopPotion(this.leftTopCorner.y));
    }

    private void _draw(Graphics g, int x, int y) {
        g.drawPixmap(
                this.sprite.image,
                x,
                y,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return true;
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap) {
        return true;
    }
}
