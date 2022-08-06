package com.a530games.jackal.objects;

import android.graphics.Rect;

import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.helpers.RollbackFloatRect;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;

/**
 * Общий класс для транспортного средства
 */
public abstract class Vehicle extends GameObject
{

    public static final int MOVE_DOWN = 0;
    public static final int MOVE_DOWN_RIGHT = 25;
    public static final int MOVE_RIGHT = 50;
    public static final int MOVE_TOP_RIGHT = 75;
    public static final int MOVE_TOP = 100;
    public static final int MOVE_TOP_LEFT = 125;
    public static final int MOVE_LEFT = 150;
    public static final int MOVE_DOWN_LEFT = 175;

    // скорость перемещения
    protected float speed = 100;

    public HitBox hitBox;

    // hit box rect in map position for draw
    private Rect hitBoxForDraw;

    /**
     * To check the intersection while moving
     * enemies in move
     */
    protected World world;

    public Vehicle(World world, float startX, float startY, Pixmap image)
    {
        super(image);

        // default sprite
        this.sprite.set(0, 1);

        this.world = world;

        this.hitBox = new HitBox(startX, startY, startX + 40, startY + 40);

        // hitbox for draw
        this.hitBoxForDraw = new Rect();
    }

    public abstract void update(float deltaTime);

    public float getLeft() {
        return this.hitBox.left;
    }

    public float getTop() {
        return this.hitBox.top;
    }

    /**
     *
     */
    public void move(int direction, float deltaTime)
    {
        switch (direction) {
            case Player.MOVE_DOWN: this.moveDown(deltaTime); break;
            case Player.MOVE_DOWN_RIGHT: this.moveDownRight(deltaTime); break;
            case Player.MOVE_RIGHT: this.moveRight(deltaTime); break;
            case Player.MOVE_TOP_RIGHT: this.moveTopRight(deltaTime); break;
            case Player.MOVE_TOP: this.moveTop(deltaTime); break;
            case Player.MOVE_TOP_LEFT: this.moveTopLeft(deltaTime); break;
            case Player.MOVE_LEFT: this.moveLeft(deltaTime); break;
            case Player.MOVE_DOWN_LEFT:  this.moveDownLeft(deltaTime); break;
        }
    }


    public void moveDown(float deltaTime)
    {
        // move don
        // this._newPos = this.y + (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * this.speed));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

    }

    public void moveDownRight(float deltaTime)
    {
        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * this.speed));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

        // move hibox
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * this.speed), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

        // this.x += (deltaTime * this.speed);
        // this.y += (deltaTime * this.speed);
        // this.hitBox.left = Math.round(this.x);
        // this.hitBox.top = Math.round(this.y);
    }

    public void moveRight(float deltaTime)
    {
        // this.x += (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * this.speed), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveTopRight(float deltaTime)
    {
        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

        // move hibox
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * this.speed), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveTop(float deltaTime)
    {
        // this.y -= (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

    }

    public void moveTopLeft(float deltaTime)
    {
        //this.x -= (deltaTime * this.speed);
        // this.y -= (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

        // move hibox
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

    }

    public void moveLeft(float deltaTime)
    {
        // this.x -= (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveDownLeft(float deltaTime)
    {
        // this.x -= (deltaTime * this.speed);
        // this.y += (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * this.speed));

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }

        // move hibox
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        if (this.checkIntersectFroMove(this.hitBox)) {
            this.hitBox.rollback();
        }
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

    // ----------> methods move in interface

    public float turretAngle = 0.5f;

    public Rect getScreenDrawRect ()
    {

        // this.hitBoxForDraw.left = (int) Math.ceil(this.hitBox.left);
        this.hitBoxForDraw.left = this.world.map.screenLeftPotion(this.hitBox.left);
        this.hitBoxForDraw.top = this.world.map.screenTopPotion(this.hitBox.top);
        // this.hitBoxForDraw.right = (int) Math.round(this.hitBox);
        this.hitBoxForDraw.right =  Math.round(this.hitBoxForDraw.left + this.hitBox.getWidth());
        // this.hitBoxForDraw.bottom = (int) Math.round(this.hitBox);
        this.hitBoxForDraw.bottom = Math.round(this.hitBoxForDraw.top + this.hitBox.getHeight());
        return this.hitBoxForDraw;
    }


    public boolean hasTurret (){
        return true;
    }


}
