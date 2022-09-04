package com.a530games.jackal.map;

// import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;

public class Beach extends MapCell
{
    Sprite sprite;

    float spriteTimer = 0.5f;

    public Beach(int row, int col)
    {
        super(row, col);
        this.sprite = new Sprite(Assets.botLine, 0, 0);
    }

    @Override
    void drawOnBackground(Graphics g)
    {

    }

    @Override
    void update(float deltaTime, CellEventCallbackHandler callbackHandler)
    {
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
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    @Override
    public void drawTopLayout(Graphics g, Map map) {

    }

    @Override
    public void drawHitBox(Graphics g, Map map) {

    }

    /**
     * Is intersect point inside rect
     */
    public boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        // return this.isRock;
        return true;
    }

    @Override
    boolean isIntersectRectInsideCell(FloatRect r) {
        return true;
    }
}
