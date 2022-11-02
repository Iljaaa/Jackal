package com.a530games.jackal.map;

import android.graphics.Rect;

import com.a530games.framework.helpers.HitBox;

/**
 * Int rect for map hitbox
 */
public class MapHitBox
{

    public Rect rect;

    private MapHitBox(int left, int top, int right, int bottom) {
        this.rect = new Rect(left, top, right, bottom);
    }

    public MapHitBox(MapCellWithHitbox mapCell) {
        this.rect = new Rect(mapCell.rect.left, mapCell.rect.top, mapCell.rect.right, mapCell.rect.bottom);
    }

    /**
     * Check intersect with other hitbox
     */
    public boolean isIntersectsWithHitbox(HitBox otherHitbox)
    {
        //
        if (this.rect.bottom < otherHitbox.top){
            return false;
        }

        if (this.rect.top > otherHitbox.bottom){
            return false;
        }

        if (this.rect.right < otherHitbox.left){
            return false;
        }

        if (this.rect.left > otherHitbox.right) {
            return false;
        }

        return true;
    }
}
