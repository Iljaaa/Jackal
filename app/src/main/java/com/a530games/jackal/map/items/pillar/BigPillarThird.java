package com.a530games.jackal.map.items.pillar;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;

abstract public class BigPillarThird extends MapCellWithHitbox
{
    Sprite sprite;

    public BigPillarThird(int row, int col)
    {
        super(row, col);

        this.hitBox.rect.top = row * this.hitBox.rect.top + 38;

        this.sprite = new Sprite(Assets.bigPillar, 0, 0);
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
    {
        g.drawPixmap(
                this.sprite.image,
                this.hitBox.rect.left,
                this.hitBox.rect.top,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {

    }

    @Override
    public void draw(Graphics g, Camera2D camera2D) {

    }

    @Override
    public void drawTopLayout(Graphics g, Camera2D camera)
    {
        g.drawPixmap(
                this.sprite.image,
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT),
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
    public boolean isIntersectRectInsideCell(HitBox r) {
        return true;
    }
}
