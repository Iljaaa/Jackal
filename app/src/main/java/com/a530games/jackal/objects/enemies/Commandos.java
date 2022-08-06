package com.a530games.jackal.objects.enemies;

import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.Settings;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.objects.Bullet;
import com.a530games.jackal.objects.GameObject;

public class Commandos extends GameObject implements Enemy
{
    private int moveDirection = 0;

    private float rotateTimer = 0;
    private float spriteTimer = 0;

    protected float speed = 20;

    private int[] directions = {
        Vehicle.MOVE_DOWN,
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
        this.sprite.setSpriteSize(31, 49);
        // this.hitBox = new HitBox(startX, startY, startX + 31, startY + 46);

        this.hitBox = new HitBox(startX, startY, startX + 51, startY + 60);
        this.sprite.setScreenMargin(10, 6);
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
            this.rotateTimer = 5;
            this.spriteTimer = 0.15f;

            //
            this.moveDirection = this.directions[Jackal.getRandom().nextInt(8)];


            // if (this.doConst > 1) this.doConst = 0;

            //
            this.updateSprite(this.moveDirection);

        }

        if (this.spriteTimer <= 0) {
            this.spriteTimer = 0.15f;
            this.tickSprite();
        }

        this.move(this.moveDirection, deltaTime);

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

        switch (direction) {
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
        }
    }

    private void tickSprite()
    {
        // this.sprite.row = 0;
        // this.sprite.col = 0;

        this.sprite.col++;
        if (this.sprite.col == 4) this.sprite.col = 0;
    }

    /**
     *
     */
    public void move(int direction, float deltaTime)
    {
        switch (direction) {
            case Vehicle.MOVE_DOWN: this.moveDown(deltaTime, this.speed); break;
            case Vehicle.MOVE_DOWN_RIGHT: this.moveDownRight(deltaTime); break;
            case Vehicle.MOVE_RIGHT: this.moveRight(deltaTime, this.speed); break;
            case Vehicle.MOVE_TOP_RIGHT: this.moveTopRight(deltaTime); break;
            case Vehicle.MOVE_TOP: this.moveTop(deltaTime, this.speed); break;
            case Vehicle.MOVE_TOP_LEFT: this.moveTopLeft(deltaTime); break;
            case Vehicle.MOVE_LEFT: this.moveLeft(deltaTime, this.speed); break;
            case Vehicle.MOVE_DOWN_LEFT:  this.moveDownLeft(deltaTime); break;
        }
    }

    public void moveDown(float deltaTime, float speed)
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
    }

    private boolean checkIntersectFroMove (FloatRect aHitbox)
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
        if (!this.world.player.equals(this)) {
            if (Map.isIntersectsTwoRect(aHitbox, this.world.player.hitBox)) {
                return true;
            }
        }

        return false;
    }

}
