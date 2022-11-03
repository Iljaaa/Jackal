package com.a530games.jackal.map.items;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;

public class Tree1 extends MapCellWithHitbox
{

    Sprite sprite;

    float spriteTimer = 0.5f;

    public Tree1(int row, int col)
    {
        super(row, col);

        this.sprite = new Sprite(Assets.tree1, 0, 0);
        this.sprite.setSpriteSize(128, 128);
    }

    @Override
    public void drawOnBackground(Graphics g, Map map) {

    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {
        this.spriteTimer -= deltaTime;
        if (this.spriteTimer <= 0) {
            this.spriteTimer = 0.5f;
            this.sprite.col++;
            if (this.sprite.col == 4) this.sprite.col = 0;
        }
    }

    @Override
    public void draw(Graphics g, Camera2D camera2D) {

    }

    @Override
    public void drawTopLayout(Graphics g, Camera2D camera)
    {
        g.drawPixmap(
                this.sprite.image,
                camera.screenLeft(this.rect.left),
                camera.screenTop(this.rect.top - 64),
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    /**
     * Is intersect point inside rect
     */
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop)
    {
        // return this.isRock;
        return true;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox rectOnMap) {
        return true;
    }

}
