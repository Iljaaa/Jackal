package com.a530games.jackal.objects;

import android.graphics.Rect;
import android.util.Log;

import com.a530games.jackal.Bullet;
import com.a530games.jackal.Map;
import com.a530games.jackal.Sprite;

public class Player extends RotateVehicle
{
    public static final int MOVE_DOWN = 0;
    public static final int MOVE_DOWN_RIGHT = 25;
    public static final int MOVE_RIGHT = 50;
    public static final int MOVE_TOP_RIGHT = 75;
    public static final int MOVE_TOP = 100;
    public static final int MOVE_TOP_LEFT = 125;
    public static final int MOVE_LEFT = 150;
    public static final int MOVE_DOWN_LEFT = 175;

    // задержка перед выстрелом
    private float fireDelay = 0;

    public Sprite sprite;

    public Player(int startX, int startY)
    {
        super(startX, startY);
        this.sprite = new Sprite(2, 1);
    }

    public void update(float deltaTime)
    {
        // уменьшаем задержку выстрела
        if (this.fireDelay > 0) this.fireDelay -= deltaTime;

        // todo: надо переделать angle на setter
        this.updateSprite(this.angle);
    }

    private void updateSprite(double playerAngle)
    {
        // this.sprite.row = 0;
        // this.sprite.col = 0;

        // вниз
        if (0 <= playerAngle && playerAngle < 0.125) {
            this.sprite.row = 2;
            this.sprite.col = 1;
        }

        // вниз в право
        if (0.125 <= playerAngle && playerAngle < 0.375) {
            this.sprite.row = 2;
            this.sprite.col = 2;
        }

        // право
        if (0.375 <= playerAngle && playerAngle < 0.625) {
            this.sprite.row = 1;
            this.sprite.col = 2;
        }

        // вверх в право
        if (0.625 <= playerAngle && playerAngle < 0.875) {
            this.sprite.row = 0;
            this.sprite.col = 2;
        }

        // вверх
        if (0.875 <= playerAngle && playerAngle < 1.125) {
            this.sprite.row = 0;
            this.sprite.col = 1;
        }

        // вверз влевл
        if (1.125 <= playerAngle && playerAngle < 1.375) {
            this.sprite.row = 0;
            this.sprite.col = 0;
        }

        // в лево
        if (1.375 <= playerAngle && playerAngle < 1.625) {
            this.sprite.row = 1;
            this.sprite.col = 0;
        }

        // в лево вниз
        if (1.625 <= playerAngle && playerAngle < 2) {
            this.sprite.row = 2;
            this.sprite.col = 0;
        }
        // Log.d("part", String.valueOf(part));
    }


    /**
     * Возвращает пулю если выстрел удлася
     */
    public Bullet fire(float deltaTime)
    {
        if (this.fireDelay > 0) return null;

        this.fireDelay = 0.55f;
        return new Bullet(this.hitBox.left, this.hitBox.top);
    }
}
