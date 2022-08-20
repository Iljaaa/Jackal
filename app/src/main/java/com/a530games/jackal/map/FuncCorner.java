package com.a530games.jackal.map;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;

public class FuncCorner extends MapCellWithHitbox
{
    Sprite sprite;

    public FuncCorner(int row, int col)
    {
        // todo: fix magic numbers
        super(row, col, 64, 64);

        this.sprite = new Sprite(Assets.mapSprite, 2, 1);
    }

    @Override
    void drawOnBackground(Graphics g)
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
    void update(float deltaTime) {

    }

    @Override
    public void draw(Graphics g, Map map) {

    }

    @Override
    public void drawHitBox(Graphics g, Map map)
    {
        g.drawLine(
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
        );

        g.drawLine(
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_WIDTH),
                map.screenTopPotion((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
        );

        g.drawLine(
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion(this.row * Map.SPRITE_HEIGHT),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_HEIGHT ),
                map.screenTopPotion(this.getYByX((this.row + 1) * Map.SPRITE_HEIGHT)),
                this.hitBoxPaint
                );

        // super.drawHitBox(g, map);
    }

    float getYByX (float y) {
        // y = ax + b
        return y;
    }

    float getXByY (float x) {
        // y = ax + b
        return x;
    }

    @Override
    boolean isIntersectPointInsideRect(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    boolean isIntersectRectInsideCell(FloatRect rectOnMap)
    {
        // check right top corner for top right corner
        return (this.hitBox.top + this.getYByX(rectOnMap.right - this.hitBox.left) > rectOnMap.top);

        // x position of right top coroner inside this rect
        /*float delta = rectOnMap.right - this.hitBox.left;

        // max top position of right top corner
        float maxYOfRightCoroner = this.hitBox.top +  this.getYByX(delta);

        if (maxYOfRightCoroner > rectOnMap.top) {
            return true;
        }

        return false;*/
    }

}
