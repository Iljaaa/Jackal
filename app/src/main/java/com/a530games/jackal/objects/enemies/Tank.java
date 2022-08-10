package com.a530games.jackal.objects.enemies;

import android.util.Log;

import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.Settings;
import com.a530games.jackal.World;
import com.a530games.jackal.objects.Bullet;

public class Tank extends Vehicle
{
    private Vector2 velocity;

    private double targetAngle;
    // private Vector targetAngle;

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

    private int[][] dirs = {
            {40, 0},
            {40, 40},
            {0, 40},
            {-40, 40},
            {-40, 0},
            {-40, -40},
            {0, -40},
            {40, -40},
    };

    public Tank(World world, int startX, int startY)
    {
        super(world, startX, startY, Assets.tank);
        this.velocity = new Vector2(0 ,1);
    }

    @Override
    public boolean hasTurret() {
        return true;
    }

    @Override
    public double getTurretAngle() {
        return this.turretAngle;
    }

    @Override
    public void update(float deltaTime, Enemy player)
    {
        if (this.rotateTimer <= 0)
        {
            this.rotateTimer = 1;

            this.doConst++;
            // if (this.doConst > 1) this.doConst = 0;

            //
            /*this.velocity = this.directions[Jackal.getRandom().nextInt(8)];*/


            // calculate angle by who points
            // FloatPoint playerCenter = player.getHitBox().getCenter();
            // FloatPoint tankCenter = this.hitBox.getCenter();

            // d
            /*float x = playerCenter.left- tankCenter.left;
            float y = playerCenter.top - tankCenter.top;
            double d = Math.sqrt((x * x) + (y * y));
            Vector v = new Vector((float) (x / d), (float) (y / d));*/

            // random angle
            this.targetAngle = Jackal.getRandom().nextFloat() * 2;
            // Log.d("player angle", String.valueOf(this.targetAngle));
        }

        if (this.doConst == 0) {
            this.move2(this.velocity, deltaTime);
        }

        if (this.doConst == 1) {
            if (this.turretAngle < this.targetAngle) this.turretAngle += 0.05;
            if (this.turretAngle > this.targetAngle) this.turretAngle -= 0.05;
        }

        if (this.doConst == 2) {
            if (this.fire()){
                if (Settings.soundEnabled) {
                    Assets.tankFire.play(0.7f);
                }
            }

            // generate new angle
            int[] a = this.dirs[Jackal.getRandom().nextInt(this.dirs.length)];
            this.velocity.set(a[0], a[1]);
            this.updateSprite(this.velocity);

            this.doConst = 0;
        }

        this.rotateTimer -= deltaTime;
    }


    private boolean fire()
    {
        Bullet b = this.world.enemyBullets.getFreeBullet();
        if (b == null) return false;

        b.reNew(this.hitBox.getCenterLeft(), this.hitBox.getCenterTop(), this.turretAngle);
        return true;
    }

    private void updateSprite(Vector2 direction)
    {
        // this.sprite.row = 0;
        // this.sprite.col = 0;
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
            this.sprite.set(0, 1);
        }

        if (292 < angle && angle <= 337) {
            this.sprite.set(0, 2);
        }

        // down
        /*if (direction == Vehicle.MOVE_DOWN) {
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
        }*/

    }

    /**
     *
     */
    public void move2(Vector2 velocity, float deltaTime)
    {
        /*switch (direction) {
            case Vehicle.MOVE_DOWN: this.moveDown(deltaTime, this.speed); break;
            case Vehicle.MOVE_DOWN_RIGHT: this.moveDownRight(deltaTime); break;
            case Vehicle.MOVE_RIGHT: this.moveRight(deltaTime, this.speed); break;
            case Vehicle.MOVE_TOP_RIGHT: this.moveTopRight(deltaTime); break;
            case Vehicle.MOVE_TOP: this.moveTop(deltaTime, this.speed); break;
            case Vehicle.MOVE_TOP_LEFT: this.moveTopLeft(deltaTime); break;
            case Vehicle.MOVE_LEFT: this.moveLeft(deltaTime, this.speed); break;
            case Vehicle.MOVE_DOWN_LEFT:  this.moveDownLeft(deltaTime); break;
        }*/

        if (velocity.x != 0) {
            this.moveHorizontal(velocity, deltaTime);
        }

        if (velocity.y != 0) {
            this.moveVertical(velocity, deltaTime);
        }
    }

    private void moveHorizontal(Vector2 velocity, float deltaTime)
    {
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * velocity.x), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    private void moveVertical(Vector2 velocity, float deltaTime)
    {
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * velocity.y));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }
}
