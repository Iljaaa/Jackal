package com.a530games.jackal.objects;

import com.a530games.framework.math.Vector2;

public interface EnemyEventHandler
{
    /**
     * Enemy fire event
     * @param mapPositionX map start position
     * @param mapPositionY map start position
     * @param direction normal vector of fly direction
     */
    void enemyFire(float mapPositionX, float mapPositionY, Vector2 direction);
}
