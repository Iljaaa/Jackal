package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;

abstract public class Corner extends MapCellWithHitbox
{
    protected Sprite sprite;

    public Corner(int row, int col)
    {
        // todo: fix magic numbers
        super(row, col, 64, 64);

        this.sprite = new Sprite(Assets.bush_sprite1, 2, 0);
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
    public void draw(Graphics g, Map map) {

    }

    @Override
    public void drawTopLayout(Graphics g, Map map) {

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
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion((int) Math.ceil((this.row * Map.SPRITE_HEIGHT) + (Map.SPRITE_HEIGHT * 0.5))),
                this.hitBoxPaint
                );

        g.drawLine(
                map.screenLeftPotion(this.col * Map.SPRITE_WIDTH),
                map.screenTopPotion((int) Math.ceil((this.row * Map.SPRITE_HEIGHT) + (Map.SPRITE_HEIGHT * 0.5))),
                map.screenLeftPotion((int) Math.ceil(this.col * Map.SPRITE_WIDTH + (Map.SPRITE_HEIGHT * 0.5))),
                map.screenTopPotion((int) Math.ceil((this.row * Map.SPRITE_HEIGHT) + (Map.SPRITE_HEIGHT * 0.5))),
                this.hitBoxPaint
                );

        g.drawLine(
                map.screenLeftPotion((int) Math.ceil(this.col * Map.SPRITE_WIDTH + (Map.SPRITE_HEIGHT * 0.5))),
                map.screenTopPotion((int) Math.ceil((this.row * Map.SPRITE_HEIGHT) + (Map.SPRITE_HEIGHT * 0.5))),
                map.screenLeftPotion((int) Math.ceil(this.col * Map.SPRITE_WIDTH + (Map.SPRITE_HEIGHT * 0.5))),
                map.screenTopPotion((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
                );

        g.drawLine(
                map.screenLeftPotion((int) Math.ceil(this.col * Map.SPRITE_WIDTH + (Map.SPRITE_HEIGHT * 0.5))),
                map.screenTopPotion((this.row + 1) * Map.SPRITE_HEIGHT),
                map.screenLeftPotion((this.col + 1) * Map.SPRITE_WIDTH),
                map.screenTopPotion((this.row + 1) * Map.SPRITE_HEIGHT),
                this.hitBoxPaint
                );

        // super.drawHitBox(g, map);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap)
    {
        // check right top connor

        if (rectOnMap.right > this.hitBox.centerX()) {
            return true;
        }

        if (rectOnMap.top < this.hitBox.centerY()) {
            return true;
        }

        return false;
    }
}
