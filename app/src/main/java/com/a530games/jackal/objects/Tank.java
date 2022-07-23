package com.a530games.jackal.objects;

import com.a530games.jackal.Map;

import java.util.Random;

public class Tank extends Vehicle
{
    private int driveDirection = 0;

    private float rotateTimer = 0;

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

    public Tank(Map map, int startX, int startY)
    {
        super(map, startX, startY);

        this.r = new Random();
    }

    @Override
    public void update(float deltaTime)
    {
        if (this.rotateTimer <= 0) {
            this.rotateTimer = 1;

            this.driveDirection = this.directions[this.r.nextInt(8)];
        }

        this.move(this.driveDirection, deltaTime);

        this.rotateTimer -= deltaTime;


    }
}
