package com.a530games.jackal.objects.enemies;

import android.graphics.Rect;
import android.util.Log;

import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.Settings;
import com.a530games.jackal.World;
import com.a530games.jackal.objects.Bullet;
import com.a530games.jackal.objects.GameObject;

import java.util.Random;

public class Commandos extends GameObject implements Enemy
{
    private int driveDirection = 0;

    private float rotateTimer = 0;

    private int doConst = 0;

    private int[] directions = { Vehicle.MOVE_DOWN,
        Vehicle.MOVE_DOWN_RIGHT,
        Vehicle.MOVE_RIGHT,
        Vehicle.MOVE_TOP_RIGHT,
        Vehicle.MOVE_TOP,
        Vehicle.MOVE_TOP_LEFT,
        Vehicle.MOVE_LEFT,
        Vehicle.MOVE_DOWN_LEFT
    };

    public Commandos(World world, int startX, int startY)
    {
        super(world, Assets.man);

        // default sprite
        this.sprite.set(0, 1);
        this.sprite.setSpriteSize(31, 46);

        this.hitBox = new HitBox(startX, startY, startX + 31, startY + 46);
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public boolean hasTurret() {
        return false;
    }

    @Override
    public double getTurretAngle() {
        return 0;
    }

    @Override
    public void update(float deltaTime, Enemy player)
    {
        if (this.rotateTimer <= 0)
        {
            this.rotateTimer = Jackal.getRandom().nextFloat() * 2;

            this.doConst++;
            // if (this.doConst > 1) this.doConst = 0;

            //
            this.driveDirection = this.directions[Jackal.getRandom().nextInt(8)];
            this.updateSprite(this.driveDirection);

        }

        if (this.doConst == 0) {
            // this.move(this.driveDirection, deltaTime);
        }

        if (this.doConst == 1) {
            /*if (this.turretAngle < this.targetAngle) this.turretAngle += 0.05;
            if (this.turretAngle > this.targetAngle) this.turretAngle -= 0.05;*/
        }

        if (this.doConst == 2) {
            if (this.fire()){
                if (Settings.soundEnabled) {
                    Assets.tankFire.play(1);
                }
            }
            this.doConst = 0;
        }

        this.rotateTimer -= deltaTime;
    }


    private boolean fire()
    {
        Bullet b = this.world.enemyBullets.getFreeBullet();
        if (b == null) return false;

        b.reNew(this.hitBox.getCenterLeft(), this.hitBox.getCenterTop(), 1.5f);
        return true;
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
