package com.a530games.jackal;

public class Bullet
{
    private final float lifeTime  = 1.5f;

    // position
    public float x;
    public float y;

    private int speed = 150;

    private boolean isOut = false;

    private float timer;

    public Bullet(float x, float y)
    {
        this.x = x;
        this.y = y;

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
        this.y -= deltaTime * this.speed;
    }

    /**
     * Перезапускаем пульку для повторного использования
     */
    public void reNew (float x, float y)
    {
        this.x = x;
        this.y = y;
        this.isOut = false;
        this.timer = this.lifeTime;
    }
}
