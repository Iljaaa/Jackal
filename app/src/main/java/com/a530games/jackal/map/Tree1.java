package com.a530games.jackal.map;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.map.MapCellWithHitbox;

public class Tree1 extends MapCellWithHitbox
{

    Sprite sprite;

    float spriteTimer = 0.5f;

    public Tree1(int row, int col)
    {
        super(row, col, 64, 64);

        this.sprite = new Sprite(Assets.tree1, 0, 0);
        this.sprite.setSpriteSize(128, 128);
    }

    @Override
    void drawOnBackground(Graphics g) {

    }

    @Override
    void update(float deltaTime) {
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
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT - 64),
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    /**
     * Is intersect point inside rect
     */
    public boolean isIntersectPointInsideRect(float mapLeft, float mapTop)
    {
        // return this.isRock;
        return true;
    }

    @Override
    boolean isIntersectRectInsideCell(FloatRect rectOnMap) {
        return true;
    }

}
