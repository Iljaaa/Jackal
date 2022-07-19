package com.a530games.jackal;

public class Bullet
{
    private float x;
    private float y;

    private int speed = 100;

    private boolean isOut = false;

    public Bullet(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isOut() {
        return isOut;
    }

    public void update(float deltaTime)
    {
        if (this.isOut) return;

        if (this.y < 10) {
            this.isOut = true;
        }

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
    }
}
