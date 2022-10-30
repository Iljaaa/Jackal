package com.a530games.jackal.map.items.fence;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.Texture;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;
import com.a530games.jackal.map.MapTexture;

public class Fence extends MapCellWithHitbox
{
    MapTexture sprite;

    public Fence(int row, int col) {
        super(row, col, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);
        this.sprite = new MapTexture(Assets.fence, 0, 0);
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {

    }

    @Override
    public void drawOnBackground(Graphics g, Map map) {
        this.sprite.draw(g, this.hitBox.left, this.hitBox.top);
    }

    @Override
    public void draw(Graphics g, Map map) {

    }

    @Override
    public void drawTopLayout(Graphics g, Map map) {

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
