package com.a530games.jackal.objects;

import android.graphics.Color;

import com.a530games.framework.helpers.Vector;

public class Bullet
{
    private final float lifeTime  = 1;

    // position
    public float x;
    public float y;

    //
    public Vector direction;

    private int speed = 12;

    private boolean isOut = false;

    private float timer;

    public Bullet(float x, float y, float angle)
    {
        this.x = x;
        this.y = y;

        // отрисовываем вектор направления
        // double x = Math.sin(this.world.player.getAngle() * Math.PI);
        // double y = Math.cos(this.world.player.getAngle() * Math.PI);

        /*g.drawLine(
                screenCenterLeft,
                screenCenterTop,
                screenCenterLeft + (int) Math.round(Math.sin(angle * Math.PI) * 50),
                screenCenterTop + (int) Math.round(Math.cos(angle * Math.PI) * 50),
                Color.GREEN);*/

        this.direction = new Vector(
                (float) Math.sin(angle * Math.PI),
                (float) Math.cos(angle * Math.PI)
        );

        this.timer = this.lifeTime;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * If bullet has intersect with map is out
     */
    public void setIsOutOnIntersectWithMap()
    {
        this.isOut = true;
    }

    public boolean isOut() {
        return this.isOut;
    }

    public void update(float deltaTime)
    {
        if (this.isOut) return;

        if (this.timer <= 0) {
            this.isOut = true;
            return;
        }

        this.timer -= deltaTime;

        // move
        this.y += this.direction.y * this.speed;
        this.x += this.direction.x * this.speed;
    }

    /**
     * Перезапускаем пульку для повторного использования
     */
    public void reNew (float x, float y, float angle)
    {
        this.x = x;
        this.y = y;
        this.direction.updateByAngle(angle);
        this.isOut = false;
        this.timer = this.lifeTime;
    }
}
