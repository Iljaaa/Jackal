package com.a530games.jackal.map;

import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;

public class Beach extends MapCellWithHitbox
{
    Sprite sprite;

    float spriteTimer = 0.5f;

    public Beach(int row, int col)
    {
        super(row, col,64, 64);
        this.sprite = new Sprite(Assets.botLine, 0, 0);
    }

    @Override
    void drawOnBackground(Graphics g)
    {

    }

    @Override
    void update(float deltaTime)
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
                map.screenLeftPotion(this.hitBox.left),
                map.screenTopPotion(this.hitBox.top),
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    @Override
    public void drawTopLayout(Graphics g, Map map) {

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
