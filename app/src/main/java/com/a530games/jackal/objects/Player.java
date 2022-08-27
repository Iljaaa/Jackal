package com.a530games.jackal.objects;

import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.World;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.enemies.RotateVehicle;

public class Player extends RotateVehicle
{

    // задержка перед выстрелом
    private float fireDelay = 0;

    public Vector2 turret = new Vector2(0, -1);
    // private Vector targetAngle;

    public Sprite gun;

    public Player(World world, int startX, int startY)
    {
        super(world, startX, startY, Assets.player);

        this.gun = new Sprite(Assets.gun);
        this.gun.set(0, 0);

        this.sprite.set(1, 0);

    }

    public void update(float deltaTime, Enemy player)
    {
        // уменьшаем задержку выстрела
        if (this.fireDelay > 0) this.fireDelay -= deltaTime;

        this.updateSprite(this.direction);
    }

    @Override
    public boolean hasTurret() {
        return false;
    }

    @Override
    public Vector2 getTurretAngle() {
        return this.turret;
    }

    @Override
    public Vector2 getTargetAngle() {
        return null;
    }


    private void updateSprite(Vector2 direction)
    {
        float angle = direction.angleInDegrees();

        if (22 > angle || angle >= 337) {
            this.sprite.set(2, 1);
        }

        if (22 < angle && angle <= 68) {
            this.sprite.set(2, 2);
        }

        if (68 < angle && angle <= 112) {
            this.sprite.set(1, 2);
        }

        if (112 < angle && angle <= 157) {
            this.sprite.set(0, 2);
        }

        if (157 < angle && angle <= 202) {
            this.sprite.set(0, 1);
        }

        if (202 < angle && angle <= 248) {
            this.sprite.set(0, 0);
        }

        if (248 < angle && angle <= 292) {
            this.sprite.set(1, 0);
        }

        if (292 < angle && angle <= 337) {
            this.sprite.set(2, 0);
        }

    }


    /**
     * Check can we fire
     */
    public boolean fire()
    {
        if (this.fireDelay > 0) return false;

        this.fireDelay = 0.55f;
        return true;
        // return new Bullet(this.hitBox.getCenterLeft(), this.hitBox.getCenterTop(), 1);
    }

    /**
     * Hit by enemy bullet
     * @param damage damage
     */
    public void hit (int damage) {
        // hp mines
    }
}
