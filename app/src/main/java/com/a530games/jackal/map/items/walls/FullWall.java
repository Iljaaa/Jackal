package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;


abstract public class FullWall extends Wall
{

    public FullWall(int row, int col)
    {
        super(row, col);

        this.sprite = new Sprite(Assets.mapSprite, 1, 1);
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
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return true;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox r) {
        return true;
    }
}
