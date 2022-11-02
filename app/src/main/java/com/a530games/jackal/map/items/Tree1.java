package com.a530games.jackal.map.items;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
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
    public void draw(Graphics g, Map map) {

    }

    @Override
    public void drawTopLayout(Graphics g, Map map)
    {
        g.drawPixmap(
                this.sprite.image,
                map.screenLeftPotion(this.rect.left),
                map.screenTopPotion(this.rect.top - 64),
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
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap) {
        return true;
    }

}
