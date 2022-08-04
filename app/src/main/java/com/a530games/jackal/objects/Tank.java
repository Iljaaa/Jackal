package com.a530games.jackal.objects;

import com.a530games.jackal.Assets;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;

import java.util.Random;

public class Tank extends Vehicle
{
    private int driveDirection = 0;

    private double targetAngle = 0;

    private float rotateTimer = 0;

    private int doConst = 0;

    private Random r;

    private int[] directions = { Vehicle.MOVE_DOWN,
        Vehicle.MOVE_DOWN_RIGHT,
        Vehicle.MOVE_RIGHT,
        Vehicle.MOVE_TOP_RIGHT,
        Vehicle.MOVE_TOP,
        Vehicle.MOVE_TOP_LEFT,
        Vehicle.MOVE_LEFT,
        Vehicle.MOVE_DOWN_LEFT
    };

    public Tank(World world, int startX, int startY)
    {
        super(world, startX, startY, Assets.tank);

        this.r = new Random();
    }

    @Override
    public void update(float deltaTime)
    {
        if (this.rotateTimer <= 0)
        {
            this.rotateTimer = 1;

            this.doConst = (this.doConst > 0) ? 0 : 1;

            //
            this.driveDirection = this.directions[this.r.nextInt(8)];
            this.updateSprite(this.driveDirection);

            // random angle
            this.targetAngle = this.r.nextFloat() * 2;
        }

        if (this.doConst == 0) {
            this.move(this.driveDirection, deltaTime);
        }
        else {
            if (this.turretAngle < this.targetAngle) this.turretAngle += 0.05;
            if (this.turretAngle > this.targetAngle) this.turretAngle -= 0.05;
        }


        this.rotateTimer -= deltaTime;
    }

    private void updateSprite(int direction)
    {
        // this.sprite.row = 0;
        // this.sprite.col = 0;

        // down
        if (direction == Vehicle.MOVE_DOWN) {
            this.sprite.set(1, 2);
        }

        // down right
        if (direction == Vehicle.MOVE_DOWN_RIGHT) {
            this.sprite.set(2, 2);
        }

        // to the right
        if (direction == Vehicle.MOVE_RIGHT) {
            this.sprite.set(2, 1);
        }

        // top right
        if (direction == Vehicle.MOVE_TOP_RIGHT) {
            this.sprite.set(2, 0);
        }

        // to the top
        if (direction == Vehicle.MOVE_TOP) {
            this.sprite.set(1, 0);
        }

        // left top
        if (direction == Vehicle.MOVE_TOP_LEFT) {
            this.sprite.set(0, 0);
        }

        // to the left
        if (direction == Vehicle.MOVE_LEFT) {
            this.sprite.set(0, 1);
        }

        // left down
        if (direction == Vehicle.MOVE_DOWN_LEFT) {
            this.sprite.set(0, 2);
        }

    }
}
