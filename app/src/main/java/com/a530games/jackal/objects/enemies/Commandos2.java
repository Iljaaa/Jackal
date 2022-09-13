package com.a530games.jackal.objects.enemies;

import android.graphics.Rect;
import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.World;

public class Commandos2 extends Vehicle
{
    private Vector2 velocity;

    private float rotateTimer = 0;
    private float spriteTimer = 0;

    /*protected float speed = 20;

    private int[] directions = {
        Vehicle.MOVE_DOWN,
        Vehicle.MOVE_DOWN_RIGHT,
        Vehicle.MOVE_RIGHT,
        Vehicle.MOVE_TOP_RIGHT,
        Vehicle.MOVE_TOP,
        Vehicle.MOVE_TOP_LEFT,
        Vehicle.MOVE_LEFT,
        Vehicle.MOVE_DOWN_LEFT
    };*/

    private int[][] dirs = {
        {20, 4},
        {20, -4},
        {20, 0},
        {-20, 4},
        {-20, -4},
        {-20, 0},
        {0, -20},
        {0, 20},
    };

    public Commandos2(int startX, int startY)
    {
        super(startX, startY, Assets.man2);

        // default sprite
        this.sprite.set(0, 1);
        this.sprite.setSpriteSize(31, 49);
        // this.hitBox = new HitBox(startX, startY, startX + 31, startY + 46);

        this.hitBox = new HitBox(startX, startY, startX + 51, startY + 60);
        this.sprite.setScreenMargin(10, 6);

        this.velocity = new Vector2(20, 0);
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void update(float deltaTime, World world)
    {
        if (this.rotateTimer <= 0)
        {
            this.rotateTimer = 5;
            this.spriteTimer = 0.15f;

            int[] a = this.dirs[Jackal.getRandom().nextInt(this.dirs.length)];
            this.velocity.set(a[0], a[1]);

            //
            // this.moveDirection = this.directions[Jackal.getRandom().nextInt(8)];


            // if (this.doConst > 1) this.doConst = 0;

            //
            this.updateSprite(this.velocity);

        }

        if (this.spriteTimer <= 0) {
            this.spriteTimer = 0.15f;
            this.tickSprite();
        }

        this.move(this.velocity, deltaTime, world);

        /*if (this.doConst == 2) {
            if (this.fire()){
                if (Settings.soundEnabled) {
                    Assets.tankFire.play(1);
                }
            }
            this.doConst = 0;
        }*/

        this.rotateTimer -= deltaTime;
        this.spriteTimer -= deltaTime;
    }

    @Override
    public void present(Graphics g, World world)
    {
        Rect screenHitBox = this.getScreenDrawHitbox(world.map);

        g.drawPixmap(
                this.sprite.image,
                screenHitBox.left + this.sprite.screenMarginLeft,
                screenHitBox.top + this.sprite.screenMarginTop,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    @Override
    public void hit(int damage) {
        Log.d("Commandos", "hit by damage");
    }

    /*private boolean fire()
    {
        Bullet b = this.world.enemyBullets.getFreeBullet();
        if (b == null) return false;

        b.reNewByVector(this.hitBox.getCenterLeft(), this.hitBox.getCenterTop(), 1, 0);
        return true;
    }*/

    private void updateSprite(Vector2 velocity) //int direction)
    {
        // this.sprite.row = 0;
        // this.sprite.col = 0;

        // up|down
        if (velocity.y > 0) {
            if (velocity.x < 0) {
                this.sprite.set(0, 1);
            }
            else if (velocity.x > 0) {
                this.sprite.set(0, 2);
            }
            else {
                this.sprite.set(0, 0);
            }
        }
        else {
            if (velocity.x < 0) {
                this.sprite.set(0, 1);
            }
            else if (velocity.x > 0) {
                this.sprite.set(0, 2);
            }
            else {
                this.sprite.set(0, 3);
            }
        }

        /*switch (direction) {
            case Vehicle.MOVE_DOWN:
                this.sprite.set(0, 0);
                break;
            case Vehicle.MOVE_DOWN_RIGHT:
            case Vehicle.MOVE_RIGHT:
            case Vehicle.MOVE_TOP_RIGHT:
                this.sprite.set(0, 1);
                break;
            case Vehicle.MOVE_TOP_LEFT:
            case Vehicle.MOVE_LEFT:
            case Vehicle.MOVE_DOWN_LEFT:
                this.sprite.set(0, 2);
                break;
            case Vehicle.MOVE_TOP:
                this.sprite.set(0, 3);
                break;
        }*/
    }

    private void tickSprite()
    {
        // this.sprite.row = 0;
        // this.sprite.col = 0;

        this.sprite.col++;
        if (this.sprite.col == 4) this.sprite.col = 0;
    }

    /*
     *
     *
    public void move(Vector2 velocity, float deltaTime)
    {


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

        if (this.isIntersectMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    private void moveVertical(Vector2 velocity, float deltaTime)
    {
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * velocity.y));

        if (this.isIntersectMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }*/

    /*public void moveDown(float deltaTime, float speed)
    {
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * speed));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

    }

    public void moveDownRight(float deltaTime)
    {
        this.moveDown(deltaTime, this.speed / 4);
        this.moveRight(deltaTime, this.speed);
    }

    public void moveRight(float deltaTime, float speed)
    {
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * speed), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveTopRight(float deltaTime)
    {
        this.moveTop(deltaTime, this.speed / 4);
        this.moveRight(deltaTime, this.speed);
    }

    public void moveTop(float deltaTime, float speed)
    {
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * speed));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

    }

    public void moveTopLeft(float deltaTime)
    {
        this.moveTop(deltaTime, this.speed / 4);
        this.moveLeft(deltaTime, this.speed);
    }

    public void moveLeft(float deltaTime, float speed)
    {
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * speed), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveDownLeft(float deltaTime)
    {
        this.moveLeft(deltaTime, this.speed);
        this.moveDown(deltaTime, this.speed / 4);
    }*/

    /*
     * Check intersect with: map, enemies and player for moving
     *
    private boolean isIntersectMove(FloatRect aHitbox)
    {

        // intersect with map
        if (this.world.map.isIntersect(aHitbox)) {
            return true;
        }

        // intersect enemies
        if (this.world.enemies.isAnyEnemyIntersectWith(this)) {
            return true;
        }

        // intersect with player
        // if (!this.world.player.equals(this)) {
            if (Map.isIntersectsTwoRect(aHitbox, this.world.player.hitBox)) {
                return true;
            }
        // }

        return false;
    }*/

    @Override
    public void setFireEventHandler(EnemyFireEventHandler fireEventHandler) {

    }
}
