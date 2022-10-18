package com.a530games.jackal.objects.enemies;

import com.a530games.framework.math.Vector2F;

public interface EnemyFireEventHandler
{
    /**
     * Enemy fire event
     * @param mapPositionX map start position
     * @param mapPositionY map start position
     * @param direction normal vector of fly direction
     */
    void enemyFire(float mapPositionX, float mapPositionY, Vector2F direction);
}
