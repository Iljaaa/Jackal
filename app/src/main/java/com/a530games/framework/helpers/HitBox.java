package com.a530games.framework.helpers;

import android.graphics.RectF;

import com.a530games.jackal.objects.Bullet;

public class HitBox
{
    public RectF rect;
    public RectF history;

    public HitBox(float left, float top, float right, float bottom) {
        this.rect = new RectF(left, top, right, bottom);
        this.history = new RectF(left, top, right, bottom);
    }

    /**
     * Save history and move
     */
    public void moveTo(float x, float y)
    {
        // save state
        this.saveState();

        this.offsetTo(x, y);
    }

    public void offsetTo(float newLeft, float newTop) {
        this.rect.offsetTo(newLeft, newTop);
    }

    public float getCenterX() {
        return this.rect.centerX();
    }

    public float getCenterY() {
        return this.rect.centerY();
    }

    public float width() {
        return this.rect.width();
    }

    public float height() {
        return this.rect.height();
    }


    /**
     * Has hit by bullet
     */
    public boolean isHit(Bullet b)
    {
        if (b.position.x < this.rect.left) return false;
        if (b.position.x > this.rect.right) return false;
        if (b.position.y < this.rect.top) return false;
        if (b.position.y > this.rect.bottom) return false;

        return true;
    }



    public void rollback() {
        this.rect.set(this.history);
    }

    private void saveState() {
        this.history.set(this.rect);
        /*this.lastLeft = this.rect.left;
        this.lastTop = this.rect.top;
        this.lastRight = this.rect.right;
        this.lastBottom = this.rect.bottom;*/
    }

    /**
     * Check intersect with other hitbox
     */
    public boolean isIntersectsWithHitbox(HitBox otherHitbox)
    {
        //
        if (this.rect.bottom < otherHitbox.rect.top){
            return false;
        }

        if (this.rect.top > otherHitbox.rect.bottom){
            return false;
        }

        if (this.rect.right < otherHitbox.rect.left){
            return false;
        }

        if (this.rect.left > otherHitbox.rect.right) {
            return false;
        }

        return true;
    }
}
