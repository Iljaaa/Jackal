package com.a530games.jackal.map.items.fence;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;
import com.a530games.jackal.map.MapTexture;

public class Fence extends MapCellWithHitbox
{
    MapTexture sprite;

    public Fence(int row, int col) {
        super(row, col);

        this.sprite = new MapTexture(Assets.fence, this);
        this.sprite.offsetToFrames(1, 1);
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {

    }

    @Override
    public void drawOnBackground(Graphics g, Map map) {
        this.sprite.draw(g, this.hitBox.rect.left, this.hitBox.rect.top);
    }

    @Override
    public void draw(Graphics g, Camera2D camera2D) {

    }

    @Override
    public void drawTopLayout(Graphics g, Camera2D camera) {

    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return true;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox rectOnMap) {
        return true;
    }
}
