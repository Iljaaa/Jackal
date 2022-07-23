package com.a530games.jackal.objects;

import com.a530games.jackal.Map;

/**
 * Общий класс для транспортного средства
 */
public abstract class RotateVehicle extends Vehicle
{

    /**
     * угол поворота машинки 0 - 2
     */
    public double angle = 0;


    public RotateVehicle(float startX, float startY) {
        super(startX, startY);
    }

    public double getAngle() {
        return this.angle;
    }

    @Override
    public void moveDown(Map map, float deltaTime)
    {
        super.moveDown(map, deltaTime);

        // target 0

        if (1 <= this.angle) {
            this.angle += 0.05;
            if (this.angle >= 2) this.angle = 0;
        }

        if (0 < this.angle && this.angle < 1) {
            this.angle -= 0.05;
            if (this.angle < 0) this.angle = 0;
        }
    }

    @Override
    public void moveDownRight(Map map, float deltaTime)
    {
        super.moveDownRight(map, deltaTime);

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

    @Override
    public void moveRight(Map map, float deltaTime)
    {
        super.moveRight(map, deltaTime);

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
    }

    @Override
    public void moveTopRight(Map map, float deltaTime)
    {
        super.moveTopRight(map, deltaTime);

        // this.x += (deltaTime * this.speed);
        // this.y -= (deltaTime * this.speed);

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

    @Override
    public void moveTop(Map map, float deltaTime)
    {
        super.moveTop(map, deltaTime);

        // traget 1

        if (this.angle < 1) {
            this.angle += 0.05;
            if (this.angle > 1) this.angle = 1;
        }

        if (this.angle > 1) {
            this.angle -= 0.05;
            if (this.angle < 1) this.angle = 1;
        }
    }

    @Override
    public void moveTopLeft(Map map, float deltaTime)
    {
        super.moveTopLeft(map, deltaTime);

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

    @Override
    public void moveLeft(Map map, float deltaTime)
    {
        super.moveLeft(map, deltaTime);

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
    }

    @Override
    public void moveDownLeft(Map map, float deltaTime)
    {
        super.moveDownLeft(map, deltaTime);

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

}
