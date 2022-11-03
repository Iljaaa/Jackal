package com.a530games.jackal.map.items.house;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;

abstract public class HouseRoof extends MapCell
{
    Sprite sprite;

    public HouseRoof(int row, int col) {
        super(row, col);

        this.sprite = new Sprite(Assets.house, 0, 0);
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
    {
        this._draw(g, this.rect.left, this.rect.top);
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
        this._draw(g, camera.screenLeft(this.rect.left), camera.screenTop(this.rect.top));
    }

    @Override
    public void drawHitBox(Graphics g, Camera2D camera) {

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
        return false;
    }


    public boolean isIntersectRectInsideCell(HitBox rectOnMap) {
        return false;
    }
}
