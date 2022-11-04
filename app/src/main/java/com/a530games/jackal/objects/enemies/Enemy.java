package com.a530games.jackal.objects.enemies;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.World;

public interface Enemy
{
    /**
     * Object hit box
     */
    HitBox getHitBox();

    /**
     * todo: remove
     * Draw sprite
     */
    Sprite getSprite();

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
     * @param g Graphic object
     * @param camera Camera object
     */
    void present (Graphics g, Camera2D camera);


    /**
     * Is enemy hit by payer bullet
     * @return is hit success
     */
    boolean hit(int damage);

    /**
     * Setting event handler
     * @param fireEventHandler object with handler
     */
    void setFireEventHandler(EnemyFireEventHandler fireEventHandler);
}
