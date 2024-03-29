package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;

abstract public class Corner extends MapCellWithHitbox
{
    protected Sprite sprite;

    public Corner(int row, int col)
    {
        // todo: fix magic numbers
        super(row, col);

        this.sprite = new Sprite(Assets.bush_sprite1, 2, 0);
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
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
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {

    }

    @Override
    public void draw(Graphics g, Camera2D camera2D) {

    }

    @Override
    public void drawTopLayout(Graphics g, Camera2D camera) {

    }

    @Override
    public void drawHitBox(Graphics g, Camera2D camera)
    {
        g.drawLine(
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT),
                camera.screenLeft((this.col + 1) * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
                );

        g.drawLine(
                camera.screenLeft((this.col + 1) * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT),
                camera.screenLeft((this.col + 1) * Map.SPRITE_WIDTH),
                camera.screenTop((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
                );

        g.drawLine(
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop(this.row * Map.SPRITE_HEIGHT),
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop((int) Math.ceil((this.row * Map.SPRITE_HEIGHT) + (Map.SPRITE_HEIGHT * 0.5))),
                this.hitBoxPaint
                );

        g.drawLine(
                camera.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera.screenTop((int) Math.ceil((this.row * Map.SPRITE_HEIGHT) + (Map.SPRITE_HEIGHT * 0.5))),
                camera.screenLeft((int) Math.ceil(this.col * Map.SPRITE_WIDTH + (Map.SPRITE_HEIGHT * 0.5))),
                camera.screenTop((int) Math.ceil((this.row * Map.SPRITE_HEIGHT) + (Map.SPRITE_HEIGHT * 0.5))),
                this.hitBoxPaint
                );

        g.drawLine(
                camera.screenLeft((int) Math.ceil(this.col * Map.SPRITE_WIDTH + (Map.SPRITE_HEIGHT * 0.5))),
                camera.screenTop((int) Math.ceil((this.row * Map.SPRITE_HEIGHT) + (Map.SPRITE_HEIGHT * 0.5))),
                camera.screenLeft((int) Math.ceil(this.col * Map.SPRITE_WIDTH + (Map.SPRITE_HEIGHT * 0.5))),
                camera.screenTop((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
                );

        g.drawLine(
                camera.screenLeft((int) Math.ceil(this.col * Map.SPRITE_WIDTH + (Map.SPRITE_HEIGHT * 0.5))),
                camera.screenTop((this.row + 1) * Map.SPRITE_HEIGHT),
                camera.screenLeft((this.col + 1) * Map.SPRITE_WIDTH),
                camera.screenTop((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
                );

        // super.drawHitBox(g, map);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox rectOnMap)
    {
        // check right top connor

        if (rectOnMap.rect.right > this.hitBox.rect.centerX()) {
            return true;
        }

        if (rectOnMap.rect.top < this.hitBox.rect.centerY()) {
            return true;
        }

        return false;
    }
}
