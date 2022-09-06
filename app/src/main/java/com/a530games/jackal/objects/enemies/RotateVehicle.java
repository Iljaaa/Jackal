package com.a530games.jackal.objects.enemies;

import com.a530games.framework.Pixmap;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.World;

/**
 * Diff rotate vehicle like player
 */
public abstract class RotateVehicle extends Vehicle
{

    /**
     *
     */
    public Vector2 direction;


    public RotateVehicle(World world, float startX, float startY, Pixmap image) {
        super(world, startX, startY, image);
        // show top
        this.direction = new Vector2(0 , -1);
    }

    public Vector2 getDirection() {
        return this.direction;
    }

    @Override
    public void move(Vector2 velocity, float deltaTime)
    {
        super.move(velocity, deltaTime);

        // just set direction
        // float currentAngle = this.direction.angleInDegrees();
        //float targetAngle = direction.
        this.direction.set(velocity);
        this.direction.nor();

        /*float directionAngle = direction.angleInDegrees();
        float currentAngle = this.direction.angleInDegrees();
        float newAngle = currentAngle - directionAngle;
        if (newAngle < 180) this.direction.rotate(-5);
        if (newAngle > 180) this.direction.rotate(5);*/
    }

    /*@Override
    public void moveDown(float deltaTime)
    {
        super.moveDown(deltaTime);

        // target 0

        if (1 <= this.direction) {
            this.direction += 0.05;
            if (this.direction >= 2) this.direction = 0;
        }

        if (0 < this.direction && this.direction < 1) {
            this.direction -= 0.05;
            if (this.direction < 0) this.direction = 0;
        }
    }

    @Override
    public void moveDownRight(float deltaTime)
    {
        super.moveDownRight(deltaTime);

        if (1.25 <= this.direction) {
            this.direction += 0.05;
            if (this.direction > 2) this.direction = this.direction - 2;
        }

        if (0 <= this.direction && this.direction < 0.25) {
            this.direction += 0.05;
            if (this.direction > 0.25) this.direction = 0.25;
        }

        if (0.25 < this.direction && this.direction < 1.25 ) {
            this.direction -= 0.05;
            if (this.direction < 0.25) this.direction = 0.25;
        }
    }

    @Override
    public void moveRight(float deltaTime)
    {
        super.moveRight(deltaTime);

        // target 0.5

        if (this.direction >= 1.5) {
            this.direction += 0.05;
            if (this.direction > 2) this.direction = this.direction - 2;
        }

        if (0 <= this.direction && this.direction < 0.5) {
            this.direction += 0.05;
            if (this.direction > 0.5) this.direction = 0.5;
        }

        if (0.5 < this.direction && this.direction < 1.5) {
            this.direction -= 0.05;
            if (this.direction < 0.5) this.direction = 0.5;
        }
    }

    @Override
    public void moveTopRight(float deltaTime)
    {
        super.moveTopRight(deltaTime);

        // this.x += (deltaTime * this.speed);
        // this.y -= (deltaTime * this.speed);

        // target: 0,75
        if (1.75 <= this.direction) {
            this.direction += 0.05;
            if (this.direction > 2) this.direction = this.direction - 2;
        }

        if (0 <= this.direction && this.direction < 0.75) {
            this.direction += 0.05;
            if (this.direction > 0.75) this.direction = 0.75;
        }

        if (0.75 < this.direction && this.direction < 1.75 ) {
            this.direction -= 0.05;
            if (this.direction < 0.75) this.direction = 0.75;
        }
    }

    @Override
    public void moveTop(float deltaTime)
    {
        super.moveTop(deltaTime);

        // traget 1

        if (this.direction < 1) {
            this.direction += 0.05;
            if (this.direction > 1) this.direction = 1;
        }

        if (this.direction > 1) {
            this.direction -= 0.05;
            if (this.direction < 1) this.direction = 1;
        }
    }

    @Override
    public void moveTopLeft(float deltaTime)
    {
        super.moveTopLeft(deltaTime);

        // target 1.25

        if (0.25 <= this.direction && this.direction < 1.25) {
            this.direction += 0.05;
            if (this.direction > 1.25) this.direction = 1.25;
        }

        if (1.25 < this.direction) {
            this.direction -= 0.05;
            if (this.direction < 1.25) this.direction = 1.25;
        }

        if (0 <= this.direction && this.direction < 0.25) {
            this.direction -= 0.05;
            if (this.direction < 0) this.direction += 2;
        }
    }

    @Override
    public void moveLeft(float deltaTime)
    {
        super.moveLeft(deltaTime);

        // target 1,5

        if (0.5 <= this.direction && this.direction < 1.5) {
            this.direction += 0.05;
            if (this.direction > 1.5) this.direction = 1.5;
        }

        if (1.5 < this.direction) {
            this.direction -= 0.05;
            if (this.direction < 1.5) this.direction = 1.5;
        }

        if (0 <= this.direction && this.direction < 0.5) {
            this.direction -= 0.05;
            if (this.direction < 0) this.direction += 2;
        }
    }

    @Override
    public void moveDownLeft(float deltaTime)
    {
        super.moveDownLeft(deltaTime);

        // target 1.75

        if (0.75 < this.direction && this.direction < 1.75) {
            this.direction += 0.05;
            if (this.direction < 0) this.direction += 2;
        }

        if (1.75 < this.direction) {
            this.direction -= 0.05;
            if (this.direction < 1.75) this.direction = 1.75;
        }

        if (0 <= this.direction && this.direction < 0.75)
        {
            this.direction -= 0.05;
            if (this.direction < 0) this.direction += 2;
        }
    }*/

}
