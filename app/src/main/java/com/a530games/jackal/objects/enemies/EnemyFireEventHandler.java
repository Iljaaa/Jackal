package com.a530games.jackal.objects.enemies;

public interface EnemyFireEventHandler
{
    /**
     * Enemy fire event
     * @param mapPositionX map start position
     * @param mapPositionY map start position
     * @param velocityX velocity
     * @param velocityY velocity
     * @param lifeTime life time
     */
    void enemyFire(float mapPositionX, float mapPositionY, float velocityX, float velocityY, float lifeTime);
}
