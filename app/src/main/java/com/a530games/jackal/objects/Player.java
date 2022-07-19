package com.a530games.jackal.objects;

import android.util.Log;

import com.a530games.jackal.Bullet;
import com.a530games.jackal.Sprite;

public class Player extends Vehicle
{
    public static final int MOVE_DOWN = 0;
    public static final int MOVE_DOWN_RIGHT = 25;
    public static final int MOVE_RIGHT = 50;
    public static final int MOVE_TOP_RIGHT = 75;
    public static final int MOVE_TOP = 100;
    public static final int MOVE_TOP_LEFT = 125;
    public static final int MOVE_LEFT = 150;
    public static final int MOVE_DOWN_LEFT = 175;

    /**
     * угол поворота машинки 0 - 1
     */
    public double angle = 0;

    // скорость перемещения
    private float speed = 100;

    // задержка перед выстрелом
    private float fireDelay = 0;

    public Sprite sprite;

    public Player(int startX, int startY) {
        super(startX, startY);
        this.sprite = new Sprite(2, 1);
    }

    /**
     * todo: рефакторить с геттера на публичнуу переменну.
     * @return
     */
    public float getX() {
        return this.x;
    }

    /**
     * todo: рефакторить с геттера на публичнуу переменну.
     * @return
     */
    public float getY() {
        return this.y;
    }

    public double getAngle() {
        return this.angle;
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
     *
     * @param direction
     */
    public void move(int direction, float deltaTime)
    {
        switch (direction) {
            case Player.MOVE_DOWN: this.moveDown(deltaTime); break;
            case Player.MOVE_DOWN_RIGHT: this.moveDownRight(deltaTime); break;
            case Player.MOVE_RIGHT: this.moveRight(deltaTime); break;
            case Player.MOVE_TOP_RIGHT: this.moveTopRight(deltaTime); break;
            case Player.MOVE_TOP: this.moveTop(deltaTime); break;
            case Player.MOVE_TOP_LEFT: this.moveTopLeft(deltaTime); break;
            case Player.MOVE_LEFT: this.moveLeft(deltaTime); break;
            case Player.MOVE_DOWN_LEFT:  this.moveDownLeft(deltaTime); break;
        }
    }

    public void moveDown(float deltaTime)
    {
        this.y += (deltaTime * this.speed);

        // traget 0

        if (1 <= this.angle) {
            this.angle += 0.05;
            if (this.angle >= 2) this.angle = 0;
        }

        if (0 < this.angle && this.angle < 1) {
            this.angle -= 0.05;
            if (this.angle < 0) this.angle = 0;
        }

        Log.d("angle", String.valueOf(this.angle));
    }

    public void moveDownRight(float deltaTime)
    {
        this.x += (deltaTime * this.speed);
        this.y += (deltaTime * this.speed);

        // target 0.25

        if (1.25 <= this.angle) {
            this.angle += 0.05;
            if (this.angle > 2) this.angle = this.angle - 2;
        }

        if (0 <= this.angle && this.angle < 0.25) {
            this.angle += 0.05;
            if (this.angle > 0.25) this.angle = 0.25;
        }

        if (0.25 < this.angle && this.angle < 1.25 ) {
            this.angle -= 0.05;
            if (this.angle < 0.25) this.angle = 0.25;
        }
    }

    public void moveRight(float deltaTime)
    {
        this.x += (deltaTime * this.speed);

        // target 0.5

        if (this.angle >= 1.5) {
            this.angle += 0.05;
            if (this.angle > 2) this.angle = this.angle - 2;
        }

        if (0 <= this.angle && this.angle < 0.5) {
            this.angle += 0.05;
            if (this.angle > 0.5) this.angle = 0.5;
        }

        if (0.5 < this.angle && this.angle < 1.5) {
            this.angle -= 0.05;
            if (this.angle < 0.5) this.angle = 0.5;
        }


        Log.d("angle", String.valueOf(this.angle));
    }

    public void moveTopRight(float deltaTime)
    {
        this.x += (deltaTime * this.speed);
        this.y -= (deltaTime * this.speed);

        // target: 0,75
        if (1.75 <= this.angle) {
            this.angle += 0.05;
            if (this.angle > 2) this.angle = this.angle - 2;
        }

        if (0 <= this.angle && this.angle < 0.75) {
            this.angle += 0.05;
            if (this.angle > 0.75) this.angle = 0.75;
        }

        if (0.75 < this.angle && this.angle < 1.75 ) {
            this.angle -= 0.05;
            if (this.angle < 0.75) this.angle = 0.75;
        }
    }

    public void moveTop(float deltaTime)
    {
        this.y -= (deltaTime * this.speed);

        // traget 1

        if (this.angle < 1) {
            this.angle += 0.05;
            if (this.angle > 1) this.angle = 1;
        }

        if (this.angle > 1) {
            this.angle -= 0.05;
            if (this.angle < 1) this.angle = 1;
        }


        Log.d("angle", String.valueOf(this.angle));
    }

    public void moveTopLeft(float deltaTime)
    {
        this.x -= (deltaTime * this.speed);
        this.y -= (deltaTime * this.speed);

        // target 1.25

        if (0.25 <= this.angle && this.angle < 1.25) {
            this.angle += 0.05;
            if (this.angle > 1.25) this.angle = 1.25;
        }

        if (1.25 < this.angle) {
            this.angle -= 0.05;
            if (this.angle < 1.25) this.angle = 1.25;
        }

        if (0 <= this.angle && this.angle < 0.25) {
            this.angle -= 0.05;
            if (this.angle < 0) this.angle += 2;
        }
    }

    public void moveLeft(float deltaTime)
    {
        this.x -= (deltaTime * this.speed);

        // target 1,5

        if (0.5 <= this.angle && this.angle < 1.5) {
            this.angle += 0.05;
            if (this.angle > 1.5) this.angle = 1.5;
        }

        if (1.5 < this.angle) {
            this.angle -= 0.05;
            if (this.angle < 1.5) this.angle = 1.5;
        }

        if (0 <= this.angle && this.angle < 0.5) {
            this.angle -= 0.05;
            if (this.angle < 0) this.angle += 2;
        }

        Log.d("angle", String.valueOf(this.angle));
    }

    public void moveDownLeft(float deltaTime)
    {
        this.x -= (deltaTime * this.speed);
        this.y += (deltaTime * this.speed);

        // target 1.75

        if (0.75 < this.angle && this.angle < 1.75) {
            this.angle += 0.05;
            if (this.angle < 0) this.angle += 2;
        }

        if (1.75 < this.angle) {
            this.angle -= 0.05;
            if (this.angle < 1.75) this.angle = 1.75;
        }

        if (0 <= this.angle  && this.angle < 0.75)
        {
            this.angle -= 0.05;
            if (this.angle < 0) this.angle += 2;
        }
    }


    /**
     * Возвращает пулю если выстрел удлася
     * @param deltaTime
     */
    public Bullet fire(float deltaTime)
    {
        if (this.fireDelay > 0) return null;

        this.fireDelay = 0.55f;
        return new Bullet(this.x, this.y);
    }
}
