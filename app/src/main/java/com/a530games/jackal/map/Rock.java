package com.a530games.jackal.map;

import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;

public class Rock extends MapCellWithHitbox
{
    public static final int MOVE_ROCK_1 = 1;
    public static final int MOVE_ROCK_2 = 2;
    public static final int MOVE_ROCK_3 = 3;

    public static final int MOVE_BUSH_1 = 10;
    public static final int MOVE_BUSH_2 = 11;
    public static final int MOVE_BUSH_3 = 12;
    public static final int MOVE_BUSH_4 = 13;

    public int type;

    public Rock(int row, int col, int type)
    {
        // todo: remove magic numbers
        super(row, col,64, 64);

        this.type = type;
    }

    @Override
    void drawOnBackground(Graphics g)
    {
        switch (this.type) {
            case Rock.MOVE_ROCK_1:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 64, 0, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.left, this.hitBox.top, 64, 0, 64, 64);
                break;
            case Rock.MOVE_ROCK_2:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 0, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.left, this.hitBox.top, 128, 0, 64, 64);
                break;
            case Rock.MOVE_ROCK_3:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 64, 128, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.left, this.hitBox.top, 64, 128, 64, 64);
                break;
            case Rock.MOVE_BUSH_1:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 64, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.left, this.hitBox.top, 0, 64, 64, 64);
                break;
            case Rock.MOVE_BUSH_2:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 128, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.left, this.hitBox.top, 0, 128, 64, 64);
                break;
            case Rock.MOVE_BUSH_3:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 64, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.left, this.hitBox.top, 128, 64, 64, 64);
                break;
            case Rock.MOVE_BUSH_4:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 128, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.left, this.hitBox.top, 128, 128, 64, 64);
                break;
        }
    }

    @Override
    void update(float deltaTime) {

    }

    @Override
    public void draw(Graphics g, Map map) {

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
    boolean isIntersectRectInsideCell(FloatRect r) {
        return true;
    }
}
