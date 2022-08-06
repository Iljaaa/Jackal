package com.a530games.jackal.objects;

import com.a530games.jackal.Assets;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.Sprite;

public class Player extends RotateVehicle
{

    // задержка перед выстрелом
    private float fireDelay = 0;

    public Sprite gun;

    public Player(World world, int startX, int startY)
    {
        super(world, startX, startY, Assets.player);

        this.gun = new Sprite(Assets.gun);
        this.gun.set(0, 1);

    }

    public void update(float deltaTime)
    {
        // уменьшаем задержку выстрела
        if (this.fireDelay > 0) this.fireDelay -= deltaTime;

        this.updateSprite(this.angle);
    }

    private void updateSprite(double playerAngle)
    {

        // вниз
        if (0 <= playerAngle && playerAngle < 0.125) {
            this.sprite.set(1, 2);
            this.gun.set(1, 2);
        }

        // вниз в право
        if (0.125 <= playerAngle && playerAngle < 0.375) {
            this.sprite.set(2, 2);
            this.gun.set(2, 2);
        }

        // to the right
        if (0.375 <= playerAngle && playerAngle < 0.625) {
            this.sprite.set(2, 1);
            this.gun.set(2, 1);
        }

        // top right
        if (0.625 <= playerAngle && playerAngle < 0.875) {
            this.sprite.set(2, 0);
            this.gun.set(2, 0);
        }

        // вверх
        if (0.875 <= playerAngle && playerAngle < 1.125) {
            this.sprite.set(1, 0);
            this.gun.set(1, 0);
        }

        // top left
        if (1.125 <= playerAngle && playerAngle < 1.375) {
            this.sprite.set(0, 0);
            this.gun.set(0, 0);
        }

        // to the left
        if (1.375 <= playerAngle && playerAngle < 1.625) {
            this.sprite.set(0, 1);
            this.gun.set(0, 1);
        }

        // в лево вниз
        if (1.625 <= playerAngle && playerAngle < 2) {
            this.sprite.set(0, 2);
            this.gun.set(0, 2);
        }
        // Log.d("part", String.valueOf(part));
    }


    /**
     * Fire
     */
    public boolean fire()
    {
        if (this.fireDelay > 0) return false;

        this.fireDelay = 0.55f;
        return true;
        // return new Bullet(this.hitBox.getCenterLeft(), this.hitBox.getCenterTop(), 1);
    }
}
