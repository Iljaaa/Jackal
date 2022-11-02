package com.a530games.jackal.map.items;

import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.FloatRect;
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
    public void draw(Graphics g, Map map)
    {
        g.drawPixmap(
                this.sprite.image,
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_WIDTH),
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height
        );
    }

    @Override
    public void drawTopLayout(Graphics g, Map map) {

    }

    @Override
    public void drawHitBox(Graphics g, Map map) {
        /*g.drawRect(
                map.screenLeftPotion(hitBox.left),
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
    public boolean isIntersectRectInsideCell(FloatRect r) {
        return true;
    }
}
