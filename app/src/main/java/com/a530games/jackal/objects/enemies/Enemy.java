package com.a530games.jackal.objects.enemies;

import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.World;
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

    /**
     * Has enemy turret
     * @return boolean
     */
    boolean hasTurret();

    /**
     * Normal vector on turret angle
     */
    Vector2 getTurretAngle();

    /**
     * Normal vector to target
     */
    Vector2 getTargetAngle();

    /**
     * @return is tank dead
     */
    boolean isDead();

    /**
     * Update enemy
     * @param deltaTime Delta time
     * @param world World for check intersect on move
     */
    void update(float deltaTime, World world);

    /**
     * Present enemy
     */
    void present (Graphics g, World world);

    /**
     * Is enemy hit by payer bullet
     */
    void hit(int damage);

    /**
     * Setting event handler
     * @param fireEventHandler object with handler
     */
    void setFireEventHandler(EnemyFireEventHandler fireEventHandler);
}
