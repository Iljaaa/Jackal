package com.a530games.jackal.objects.enemies;

import com.a530games.framework.Pixmap;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.World;

/**
 * Diff rotate vehicle like player
 */
public abstract class RotateVehicle extends Vehicle
{
    /**
     *
     */
    public Vector2F direction;

    public RotateVehicle(float startCenterX, float startCenterY, int hitboxWidth, int hitboxHeight, Pixmap image) {
        super(startCenterX, startCenterY, hitboxWidth, hitboxHeight, image);
        // show top
        this.direction = new Vector2F(0 , -1);
    }

    /*public Vector2F getDirection() {
        return this.direction;
    }*/

    @Override
    public void drive(Vector2F velocity, float deltaTime, World world)
    {
        super.drive(velocity, deltaTime, world);

        // update direction
        this.direction.set(velocity);
        this.direction.nor();
    }

}
