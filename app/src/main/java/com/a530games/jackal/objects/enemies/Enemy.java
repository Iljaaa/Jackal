package com.a530games.jackal.objects.enemies;

import android.graphics.Rect;

import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.map.Map;

public interface Enemy
{
    /**
     * Object hit box
     */
    HitBox getHitBox();

    /**
     * Hitbox rect in map directions
     */
    Rect getScreenDrawHitbox(Map map);

    /**
     * Draw sprite
     */
    Sprite getSprite();


    boolean hasTurret();

    double getTurretAngle();

    void update(float deltaTime, Enemy player);

}
