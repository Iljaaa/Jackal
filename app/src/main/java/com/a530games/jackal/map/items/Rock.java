package com.a530games.jackal.map.items;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;

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
        super(row, col);

        this.type = type;
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
    {
        switch (this.type) {
            case Rock.MOVE_ROCK_1:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 64, 0, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.rect.left, this.hitBox.rect.top, 64, 0, 64, 64);
                break;
            case Rock.MOVE_ROCK_2:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 0, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.rect.left, this.hitBox.rect.top, 128, 0, 64, 64);
                break;
            case Rock.MOVE_ROCK_3:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 64, 128, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.rect.left, this.hitBox.rect.top, 64, 128, 64, 64);
                break;
            case Rock.MOVE_BUSH_1:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 64, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.rect.left, this.hitBox.rect.top, 0, 64, 64, 64);
                break;
            case Rock.MOVE_BUSH_2:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 0, 128, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.rect.left, this.hitBox.rect.top, 0, 128, 64, 64);
                break;
            case Rock.MOVE_BUSH_3:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 64, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.rect.left, this.hitBox.rect.top, 128, 64, 64, 64);
                break;
            case Rock.MOVE_BUSH_4:
                // g.drawPixmap(Assets.mapSprite, col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, 128, 128, 64, 64);
                g.drawPixmap(Assets.mapSprite, this.hitBox.rect.left, this.hitBox.rect.top, 128, 128, 64, 64);
                break;
        }
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {

    }

    @Override
    public void draw(Graphics g, Camera2D camera2D) {

    }

    @Override
    public void drawTopLayout(Graphics g, Camera2D camera) {

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
    public boolean isIntersectRectInsideCell(HitBox r) {
        return true;
    }
}
