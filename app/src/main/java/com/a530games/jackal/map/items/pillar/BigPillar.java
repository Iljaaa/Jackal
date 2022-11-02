package com.a530games.jackal.map.items.pillar;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;

public class BigPillar extends MapCellWithHitbox
{

    public static final int PART_LEFT_TOP = 0;
    public static final int PART_RIGHT_TOP = 1;
    public static final int PART_RIGHT_L1 = 10;
    public static final int PART_LEFT_L1 = 11;
    public static final int PART_RIGHT_L2 = 20;
    public static final int PART_LEFT_L2 = 21;
    public static final int PART_LEFT_DOWN = 30;
    public static final int PART_RIGHT_DOWN = 31;

    Sprite sprite;

    public BigPillar(int row, int col, int part)
    {
        // todo: fix magic numbers
        super(row, col);

        if (part == PART_LEFT_TOP) {
            this.sprite = new Sprite(Assets.bigPillar, 0, 0);
        }
        else if (part == PART_RIGHT_TOP) {
            this.sprite = new Sprite(Assets.bigPillar, 1, 0);
        }
        else if (part == PART_LEFT_L1) {
            this.sprite = new Sprite(Assets.bigPillar, 0, 1);
        }
        else if (part == PART_RIGHT_L1) {
            this.sprite = new Sprite(Assets.bigPillar, 1, 1);
        }
        else if (part == PART_LEFT_L2) {
            this.sprite = new Sprite(Assets.bigPillar, 0, 2);
        }
        else if (part == PART_RIGHT_L2) {
            this.sprite = new Sprite(Assets.bigPillar, 1, 2);
        }
        else if (part == PART_LEFT_DOWN) {
            this.sprite = new Sprite(Assets.bigPillar, 0, 3);
        }
        else if (part == PART_RIGHT_DOWN) {
            this.sprite = new Sprite(Assets.bigPillar, 1, 3);
        }

        else {
            this.sprite = new Sprite(Assets.bigPillar, 0, 0);
        }

    }

    @Override
    public void drawOnBackground(Graphics g, Map map) {
        g.drawPixmap(
                this.sprite.image,
                this.hitBox.rect.left,
                this.hitBox.rect.top,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);

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
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox r) {
        return true;
    }
}
