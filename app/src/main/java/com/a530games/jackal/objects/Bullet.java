package com.a530games.jackal.objects;

import com.a530games.framework.helpers.FloatPoint;
import com.a530games.framework.math.Vector2;

public class Bullet
{
    private final float lifeTime  = 1;

    // position
    public FloatPoint mapPosition;
    // public float x;
    // public float y;

    // start position
    // for calculate shot blow
    public FloatPoint startMapPosition;

    //
    public Vector2 direction;

    //
    private int speed = 12;

    private boolean isOut = false;

    // timer to death
    public float timer = 0;

    public Bullet(boolean isOut) {
        this.isOut = isOut;
        this.direction = new Vector2(0 ,1);
        this.mapPosition = new FloatPoint(0, 0);
        this.startMapPosition = new FloatPoint(0, 0);
    }

    public Bullet(float x, float y, float angle)
    {
        this.mapPosition.left = x;
        this.mapPosition.top = y;
        this.startMapPosition.left = x;
        this.startMapPosition.top = y;
//        this.x = x;
//        this.y = y;

        // отрисовываем вектор направления
        // double x = Math.sin(this.world.player.getAngle() * Math.PI);
        // double y = Math.cos(this.world.player.getAngle() * Math.PI);

        /*g.drawLine(
                screenCenterLeft,
                screenCenterTop,
                screenCenterLeft + (int) Math.round(Math.sin(angle * Math.PI) * 50),
                screenCenterTop + (int) Math.round(Math.cos(angle * Math.PI) * 50),
                Color.GREEN);*/

        this.direction = new Vector2(
                (float) Math.sin(angle * Math.PI),
                (float) Math.cos(angle * Math.PI)
        );

        this.timer = 0;
    }

    public float getX() {
        // return x;
        return this.mapPosition.left;
    }

    public float getY() {
        // return y;
        return this.mapPosition.top;
    }

    /**
     * If bullet has intersect with map is out
     */
    public void setIsOutOnIntersectWithMap()
    {
        this.isOut = true;
    }
    /**
     * If bullet hit enemy mark as out
     */
    public void setIsOutOnHitEnemy()
    {
        this.isOut = true;
    }

    public boolean isOut() {
        return this.isOut;
    }

    public void update(float deltaTime)
    {
        if (this.isOut) return;

        if (this.timer >= this.lifeTime) {
            this.isOut = true;
            return;
        }

        this.timer += deltaTime;

        // move
        this.mapPosition.left += this.direction.x * this.speed;
        this.mapPosition.top += this.direction.y * this.speed;
        // this.y += this.direction.y * this.speed;
        // this.x += this.direction.x * this.speed;
    }

    /*
     * @deprecated use renewByVector
     * Перезапускаем пульку для повторного использования

    public void reNew (float x, float y, float angle)
    {
        this.mapPosition.left = x;
        this.mapPosition.top = y;
        this.startMapPosition.left = x;
        this.startMapPosition.top = y;
        // this.x = x;
        // this.y = y;
        this.direction.updateByAngle(angle);
        this.isOut = false;
        this.timer = 0;
    } */

    /**
     * @deprecated use new method
     * Перезапускаем пульку для повторного использования
     */
    public void reNewByVector (float x, float y, float directionX, float directionY)
    {
        this.mapPosition.left = x;
        this.mapPosition.top = y;
        this.startMapPosition.left = x;
        this.startMapPosition.top = y;
        // this.x = x;
        // this.y = y;
        // this.direction.updateByAngle(angle);
        this.direction.x = directionX;
        this.direction.y = directionY;

        this.isOut = false;
        this.timer = 0;
    }

    /**
     * Перезапускаем пульку для повторного использования
     */
    public void reNewByDirectionVector (float x, float y, Vector2 direction)
    {
        this.mapPosition.left = x;
        this.mapPosition.top = y;
        this.startMapPosition.left = x;
        this.startMapPosition.top = y;
        // this.x = x;
        // this.y = y;
        // this.direction.updateByAngle(angle);
        this.direction.x = direction.x;
        this.direction.y = direction.y;

        // normarize vector
        this.direction.nor();

        this.isOut = false;
        this.timer = 0;
    }
}
