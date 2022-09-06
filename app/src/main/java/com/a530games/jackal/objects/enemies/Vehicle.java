package com.a530games.jackal.objects.enemies;

import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.objects.GameObject;

/**
 * Общий класс для транспортного средства
 */
public abstract class Vehicle extends GameObject implements Enemy
{

    /*public static final int MOVE_DOWN = 0;
    public static final int MOVE_DOWN_RIGHT = 25;
    public static final int MOVE_RIGHT = 50;
    public static final int MOVE_TOP_RIGHT = 75;
    public static final int MOVE_TOP = 100;
    public static final int MOVE_TOP_LEFT = 125;
    public static final int MOVE_LEFT = 150;
    public static final int MOVE_DOWN_LEFT = 175;*/

    public Vehicle(float startX, float startY, Pixmap image)
    {
        super(image);

        // default sprite
        this.sprite.set(0, 1);
        this.sprite.setScreenMargin(-12, -12);

        this.hitBox = new HitBox(startX, startY, startX + 40, startY + 40);
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    // public abstract void update(float deltaTime);

    public float getLeft() {
        return this.hitBox.left;
    }

    public float getTop() {
        return this.hitBox.top;
    }

    /**
     * move vehicle on map
     * world to check intersect for move
     */
    public void move(Vector2 velocity, float deltaTime, World world)
    {
        if (velocity.x != 0) {
            this.moveHorizontal(velocity.x, deltaTime, world);
        }

        if (velocity.y != 0) {
            this.moveVertical(velocity.y, deltaTime, world);
        }

        /*switch (direction) {
            case Vehicle.MOVE_DOWN: this.moveDown(deltaTime); break;
            case Vehicle.MOVE_DOWN_RIGHT: this.moveDownRight(deltaTime); break;
            case Vehicle.MOVE_RIGHT: this.moveRight(deltaTime); break;
            case Vehicle.MOVE_TOP_RIGHT: this.moveTopRight(deltaTime); break;
            case Vehicle.MOVE_TOP: this.moveTop(deltaTime); break;
            case Vehicle.MOVE_TOP_LEFT: this.moveTopLeft(deltaTime); break;
            case Vehicle.MOVE_LEFT: this.moveLeft(deltaTime); break;
            case Vehicle.MOVE_DOWN_LEFT:  this.moveDownLeft(deltaTime); break;
        }*/
    }

    private void moveHorizontal(float xSpeed, float deltaTime, World world)
    {
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * xSpeed), this.hitBox.top);

        if (this.checkIntersectForMove(this.hitBox, world)) {
            this.hitBox.rollback();
        }
    }

    private void moveVertical(float ySpeed, float deltaTime, World world)
    {
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * ySpeed));

        if (this.checkIntersectForMove(this.hitBox, world)) {
            this.hitBox.rollback();
        }
    }

    /*public void moveDown(float deltaTime)
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
        this.moveDown(deltaTime);
        this.moveRight(deltaTime);
        // move hibox


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
        this.moveTop(deltaTime);
        this.moveRight(deltaTime);

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
        this.moveTop(deltaTime);
        this.moveLeft(deltaTime);


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
        this.moveLeft(deltaTime);
        this.moveDown(deltaTime);

        // move hibox

    }*/

    protected boolean checkIntersectForMove(FloatRect aHitbox, World world)
    {

        // intersect with map
        if (world.map.isIntersect(aHitbox)) {
            return true;
        }

        // intersect enemies
        if (world.enemies.isAnyEnemyIntersectWith(this)) {
            return true;
        }

        // intersect with player
        if (!world.player.equals(this)) {
            if (Map.isIntersectsTwoRect(aHitbox, world.player.hitBox)) {
                return true;
            }
        }

        return false;
    }



}
