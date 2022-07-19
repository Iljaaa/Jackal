package com.a530games.jackal.objects;

import android.graphics.Rect;

/**
 * Общий класс для транспортного средства
 */
public abstract class Vehicle
{
    // пожение на экране
    public float x;
    public float y;

    public Rect hitBox;

    public Vehicle(int startX, int startY)
    {
        this.x = startX;
        this.y = startY;

        this.hitBox = new Rect(Math.round(this.x), Math.round(this.y), Math.round(this.x) + 20, Math.round(this.y) + 20);
    }

    public abstract void update(float deltaTime);

}
