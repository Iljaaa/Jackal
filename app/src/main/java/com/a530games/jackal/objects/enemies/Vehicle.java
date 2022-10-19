package com.a530games.jackal.objects.enemies;

import android.graphics.Rect;

import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;

/**
 * Общий класс для транспортного средства
 */
public abstract class Vehicle implements Enemy
{
    /**
     * Vehicle hitbox frame
     */
    public HitBox hitBox;

    /**
     * Hitbox mapped to screen coords
     */
    private final Rect hitBoxForDraw;

    /**
     *
     */
    public Sprite sprite;

    public Vehicle(float startX, float startY, Pixmap image)
    {

        this.sprite = new Sprite(image);

        // default sprite
        this.sprite.set(0, 1);
        this.sprite.setScreenMargin(-12, -12);

        this.hitBox = new HitBox(startX, startY, startX + 40, startY + 40);

        this.hitBoxForDraw = new Rect();
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public Sprite getSprite() {
        return this.sprite;
    }

    public float getLeft() {
        return this.hitBox.left;
    }

    public float getTop() {
        return this.hitBox.top;
    }

    /**
     * Just move to
     */
    public void moveCenter(float x, float y) {
        this.moveTo(
                x - (this.hitBox.getWidth() / 2),
                y - (this.hitBox.getHeight() / 2)
        );
    }
    /**
     * Just move to
     */
    public void moveTo(float x, float y) {
        this.hitBox.moveTo(x, y);
    }

    /**
     * Vehicle drive on map
     * world to check intersect for move
     */
    public void drive(Vector2F velocity, float deltaTime, World world)
    {
        if (velocity.x != 0) {
            this.driveHorizontal(velocity.x, deltaTime, world);
        }

        if (velocity.y != 0) {
            this.driveVertical(velocity.y, deltaTime, world);
        }
    }

    private void driveHorizontal(float xSpeed, float deltaTime, World world)
    {
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * xSpeed), this.hitBox.top);

        if (this.checkIntersectForMove(this.hitBox, world)) {
            this.hitBox.rollback();
        }
    }

    private void driveVertical(float ySpeed, float deltaTime, World world)
    {
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * ySpeed));

        if (this.checkIntersectForMove(this.hitBox, world)) {
            this.hitBox.rollback();
        }
    }

    protected boolean checkIntersectForMove(FloatRect aHitbox, World world)
    {

        // intersect with map
        if (world.map.isIntersect(aHitbox)) {
            return true;
        }

        // intersect enemies
        if (world.enemies.isAnyEnemyIntersectWith(this)) {
            return true;
        }

        // intersect with player
        if (!world.player.equals(this)) {
            if (FloatRect.isIntersectsTwoRectF(aHitbox, world.player.hitBox)) {
                return true;
            }
        }

        return false;
    }



    public Rect getScreenDrawHitbox (Map map)
    {
        this.hitBoxForDraw.left = map.screenLeftPotion(this.hitBox.left);
        this.hitBoxForDraw.top = map.screenTopPotion(this.hitBox.top);
        this.hitBoxForDraw.right =  Math.round(this.hitBoxForDraw.left + this.hitBox.getWidth());
        this.hitBoxForDraw.bottom = Math.round(this.hitBoxForDraw.top + this.hitBox.getHeight());
        return this.hitBoxForDraw;
    }

}
