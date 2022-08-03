package com.a530games.jackal.objects;

import com.a530games.framework.Pixmap;
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

    public RollbackFloatRect hitBox;

    /**
     * For get map for move
     * enemies in move
     */
    protected World world;

    public Vehicle(World world, float startX, float startY, Pixmap image)
    {
        super(image);

        // default sprite
        this.sprite.set(1, 2);

        this.world = world;

        this.hitBox = new RollbackFloatRect(startX, startY, startX + 40, startY + 40);
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

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            // this.hitBox.top = this._newPos;
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }
    }

    public void moveDownRight(float deltaTime)
    {
        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * this.speed));

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            // this.y = this._newPos;
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }

        // move hibox
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * this.speed), this.hitBox.top);

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
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

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }
    }

    public void moveTopRight(float deltaTime)
    {
        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }

        // move hibox
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * this.speed), this.hitBox.top);

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }
    }

    public void moveTop(float deltaTime)
    {
        // this.y -= (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // intersect with enemies
        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }


        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }

    }

    public void moveTopLeft(float deltaTime)
    {
        //this.x -= (deltaTime * this.speed);
        // this.y -= (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }

        // move hibox
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }

        //

    }

    public void moveLeft(float deltaTime)
    {
        // this.x -= (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }
    }

    public void moveDownLeft(float deltaTime)
    {
        // this.x -= (deltaTime * this.speed);
        // this.y += (deltaTime * this.speed);

        // move hibox
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * this.speed));

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }

        // move hibox
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        // intersect with map
        if (this.world.map.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // Vehicle me = this.e
        if (this.world.enemies.fff(this)) {
            this.hitBox.rollback();
        }

        // intersect with player
        if (!this.world.player.equals(this)){
            if (Map.isIntersectsTwoRect(this.hitBox, this.world.player.hitBox)) {
                this.hitBox.rollback();
            }
        }
    }

}
