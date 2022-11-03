package com.a530games.jackal.map.items;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;

public class Bush extends MapCell
{

    Sprite sprite;

    float spriteTimer = 0.5f;

    public Bush(int row, int col, Pixmap image)
    {
        // todo: remove magic numbers
        super(row, col);

        this.sprite = new Sprite(image, 0, 0);
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
    {
        /*g.drawPixmap(
                this.sprite.image,
                this.hitBox.left,
                this.hitBox.rect.top,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height
        );*/
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
    public void draw(Graphics g, Camera2D camera2D)
    {
        g.drawPixmap(
                this.sprite.image,
                camera2D.screenLeft(this.col * Map.SPRITE_WIDTH),
                camera2D.screenTop(this.row * Map.SPRITE_WIDTH),
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height
        );
    }

    @Override
    public void drawTopLayout(Graphics g, Camera2D camera) {

    }

    @Override
    public void drawHitBox(Graphics g, Camera2D camera) {
        /*g.drawRect(
                map.screenLeft(hitBox.left),
                map.screenTopPotion(hitBox.top),
                hitBox.width(),
                hitBox.height(),
                this.hitBoxPaint
        );*/
    }

    /**
     * Is intersect point inside rect
     */
    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        // return this.isRock;
        return true;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox r) {
        return true;
    }
}
