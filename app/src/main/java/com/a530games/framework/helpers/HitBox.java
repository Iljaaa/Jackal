package com.a530games.framework.helpers;

import com.a530games.jackal.objects.Bullet;

public class HitBox extends RollbackFloatRect
{
    public HitBox(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    /**
     * Has hit by bullet
     */
    public boolean isHit(Bullet b) {
        if (b.mapPosition.x < this.left) return false;
        if (b.mapPosition.x > this.right) return false;
        if (b.mapPosition.y < this.top) return false;
        if (b.mapPosition.y > this.bottom) return false;
        // if (b.x < this.left) return false;
        // if (b.x > this.right) return false;
        // if (b.y < this.top) return false;
        // if (b.y > this.bottom) return false;
        return true;
    }

    /**
     * Check intersect with other hitbox
     */
    public boolean isIntersectsWithHitbox(HitBox otherHitbox)
    {
        //
        if (this.bottom < otherHitbox.top){
            return false;
        }

        if (this.top > otherHitbox.bottom){
            return false;
        }

        if (this.right < otherHitbox.left){
            return false;
        }

        if (this.left > otherHitbox.right) {
            return false;
        }

        return true;
    }

}
