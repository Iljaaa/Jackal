package com.a530games.jackal.objects;

import android.util.Log;

import com.a530games.framework.math.Vector2F;

public class Bullet
{
    /**
     * Position
     */
    public Vector2F position;

    /**
     * start position
     *     // for calculate shot blow
     */
    public Vector2F startMapPosition;

    /**
     * Bullet velocity
     */
    public Vector2F velocity;

    /**
     * Is out dead flag
     */
    private boolean isOut = false;

    /**
     * Live timer
     */
    public float liveTime = 1;

    public Bullet(boolean isOut)
    {
        this(0, 0, 0, 0);

        this.isOut = isOut;
    }

    public Bullet(float x, float y, float velocityX, float velocityY)
    {
        this.position = new Vector2F(x, y);

        this.startMapPosition = new Vector2F(x, y);

        // отрисовываем вектор направления
        // double x = Math.sin(this.world.player.getAngle() * Math.PI);
        // double y = Math.cos(this.world.player.getAngle() * Math.PI);

        /*g.drawLine(
                screenCenterLeft,
                screenCenterTop,
                screenCenterLeft + (int) Math.round(Math.sin(angle * Math.PI) * 50),
                screenCenterTop + (int) Math.round(Math.cos(angle * Math.PI) * 50),
                Color.GREEN);*/

        this.velocity = new Vector2F(velocityX, velocityY);
    }

    protected void finalize() {
        Log.d("Bullet", "finalizew");
    }


    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
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

        //
        this.liveTime -= deltaTime;

        // check life time
        if (this.liveTime < 0) {
            this.isOut = true;
            return;
        }

        // move
        this.position.x += this.velocity.x * deltaTime;
        this.position.y += this.velocity.y * deltaTime;

        // this.position.x += this.velocity.x * this.speed;
        // this.position.y += this.velocity.y * this.speed;
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

    /*
     * @deprecated use new method
     * Перезапускаем пульку для повторного использования

    public void reNewByVector (float x, float y, float directionX, float directionY)
    {
        this.position.x = x;
        this.position.y = y;

        this.startMapPosition.x = x;
        this.startMapPosition.y = y;

        this.direction.x = directionX;
        this.direction.y = directionY;

        this.isOut = false;
        this.timer = 0;
    }*/

    /**
     * Renew bullet
     */
    public void reNew(float x, float y, float velocityX, float velocityY, float liveTime)
    {
        this.position.x = x;
        this.position.y = y;

        this.startMapPosition.x = x;
        this.startMapPosition.y = y;

        this.velocity.x = velocityX;
        this.velocity.y = velocityY;

        // normarize vector
        // this.direction.nor();

        this.isOut = false;
        this.liveTime = liveTime;
    }
}
