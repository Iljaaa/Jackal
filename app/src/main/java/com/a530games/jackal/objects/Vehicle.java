package com.a530games.jackal.objects;

import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.RollbackFloatRect;

/**
 * Общий класс для транспортного средства
 */
public abstract class Vehicle
{
    // пожение на экране
    public float x;
    public float y;

    public RollbackFloatRect hitBox;

    public Vehicle(float startX, float startY)
    {
        this.x = startX;
        this.y = startY;

        this.hitBox = new RollbackFloatRect(this.x, this.y, this.x + 20, this.y + 20);
    }

    public abstract void update(float deltaTime);

}
