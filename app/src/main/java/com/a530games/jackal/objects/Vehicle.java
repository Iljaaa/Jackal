package com.a530games.jackal.objects;

import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.RollbackFloatRect;

/**
 * Общий класс для транспортного средства
 */
public abstract class Vehicle
{
    public RollbackFloatRect hitBox;

    public Vehicle(float startX, float startY)
    {
        this.hitBox = new RollbackFloatRect(startX, startY, startX + 20, startY + 20);
    }

    public abstract void update(float deltaTime);

}
