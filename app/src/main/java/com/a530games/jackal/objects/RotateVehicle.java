package com.a530games.jackal.objects;

import com.a530games.framework.Pixmap;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;

/**
 * Общий класс для транспортного средства
 */
public abstract class RotateVehicle extends Vehicle
{

    /**
     * угол поворота машинки 0 - 2
     */
    public double angle = 1;


    public RotateVehicle(World world, float startX, float startY, Pixmap image) {
        super(world, startX, startY, image);
    }

    public double getAngle() {
        return this.angle;
    }

    @Override
    public void moveDown(float deltaTime)
    {
        super.moveDown(deltaTime);

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
    public void moveDownRight(float deltaTime)
    {
        super.moveDownRight(deltaTime);

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
    public void moveRight(float deltaTime)
    {
        super.moveRight(deltaTime);

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
    public void moveTopRight(float deltaTime)
    {
        super.moveTopRight(deltaTime);

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
    public void moveTop(float deltaTime)
    {
        super.moveTop(deltaTime);

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
    public void moveTopLeft(float deltaTime)
    {
        super.moveTopLeft(deltaTime);

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
    public void moveLeft(float deltaTime)
    {
        super.moveLeft(deltaTime);

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
    public void moveDownLeft(float deltaTime)
    {
        super.moveDownLeft(deltaTime);

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
