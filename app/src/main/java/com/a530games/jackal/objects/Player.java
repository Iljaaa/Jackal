package com.a530games.jackal.objects;

import com.a530games.framework.Graphics;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.World;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.objects.enemies.EnemyFireEventHandler;
import com.a530games.jackal.objects.enemies.RotateVehicle;

public class Player extends RotateVehicle
{

    // задержка перед выстрелом
    private float fireDelay = 0;

    public Vector2 turret = new Vector2(0, -1);
    // private Vector targetAngle;

    public Sprite gun;

    // скорость перемещения
    protected float speed = 100;

    private Vector2 velocity;

    public Player(int startX, int startY)
    {
        super(startX, startY, Assets.player);

        this.velocity = new Vector2();

        this.gun = new Sprite(Assets.gun);
        this.gun.set(0, 0);

        this.sprite.set(1, 0);
    }

    @Override
    public void update(float deltaTime, World world)
    {
        // уменьшаем задержку выстрела
        if (this.fireDelay > 0) this.fireDelay -= deltaTime;

        // this.updateSprite(this.direction);
    }

    @Override
    public void present(Graphics ge, World world) {

    }

    @Override
    public void move(Vector2 direction, float deltaTime, World world)
    {
        this.velocity.set(
            direction.x * this.speed,
            direction.y * this.speed
        );

        super.move(this.velocity, deltaTime, world);

        // todo: make method
        switch (direction.getQuater()) {
            case 7: this.sprite.set(2, 0); break;
            case 6: this.sprite.set(1, 0); break;
            case 5: this.sprite.set(0, 0); break;
            case 4: this.sprite.set(0, 1); break;
            case 3: this.sprite.set(0, 2); break;
            case 2: this.sprite.set(1, 2); break;
            case 1: this.sprite.set(2, 2); break;
            default: this.sprite.set(2, 1);
                break;
        }
    }

    public void setTurretAngle (Vector2 direction)
    {
        this.turret.set(direction);

        // todo: make method
        switch (direction.getQuater()) {
            case 7: this.gun.set(2, 0); break;
            case 6: this.gun.set(1, 0); break;
            case 5: this.gun.set(0, 0); break;
            case 4: this.gun.set(0, 1); break;
            case 3: this.gun.set(0, 2); break;
            case 2: this.gun.set(1, 2); break;
            case 1: this.gun.set(2, 2); break;
            default: this.gun.set(2, 1);
                break;
        }
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

    /*private void updateSprite(Vector2 direction)
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

    }*/


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

    @Override
    public void hit(int damage) {

    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void setFireEventHandler(EnemyFireEventHandler fireEventHandler) {

    }
}
