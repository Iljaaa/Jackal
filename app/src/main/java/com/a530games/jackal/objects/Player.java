package com.a530games.jackal.objects;

import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.World;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.objects.enemies.EnemyFireEventHandler;
import com.a530games.jackal.objects.enemies.RotateVehicle;

public class Player extends RotateVehicle
{
    enum PlayerState {
        OnLine,
        Hit,

        LevelFinish,

        BlowUpBeforeDeath,
        Dead
    }

    /**
     * Player state
     */
    public PlayerState state = PlayerState.OnLine;

    /**
     * Player hit points
     */
    public int hp = 2;

    // задержка перед выстрелом
    private float fireDelay = 0;

    /**
     * Delay on hit
     */
    private float hitDelay = 0;

    /**
     * Delay on blink
     */
    private float blinkDelay  = 0;

    //
    public Vector2 turret = new Vector2(0, -1);

    //
    public Sprite gun;

    // скорость перемещения
    protected float speed = 100;

    //
    private Vector2 velocity;

    private PlayerEventHandler playerEventHandler;

    public Player(int startX, int startY, PlayerEventHandler playerEventHandler)
    {
        super(startX, startY, Assets.player);

        this.playerEventHandler = playerEventHandler;

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
        // update hit timmer
        if (this.hitDelay > 0){
            this.updateHitTimer(deltaTime);
        }
    }

    private void updateHitTimer(float deltaTim)
    {
        if (this.hitDelay > 0) this.hitDelay -= deltaTim;

        // delay is over
        if (this.hitDelay <= 0) {
            this.state = PlayerState.OnLine;
        }
    }

    @Override
    public void present(Graphics ge, World world) {

    }

    public void move(float x, float y, float deltaTime, World world)
    {
        // todo: think need her check user state

        this.velocity.set(x * this.speed, y * this.speed);

        super.move(this.velocity, deltaTime, world);

        this.updateSprite();
    }

    @Override
    public void move(Vector2 direction, float deltaTime, World world)
    {
        this.move(direction.x, direction.y, deltaTime, world);
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

    private void updateSprite()
    {
        switch (this.direction.getQuater()) {
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

    public boolean isOnline(){
        return (this.state == PlayerState.OnLine);
    }

    @Override
    public boolean hit(int damage)
    {
        Log.d("Player", "hit");

        if (this.hp <= 0) return false;
        if (this.state == PlayerState.Dead) return false;

        // check hit
        if (this.state == PlayerState.Hit) return false;

        this.hp--;

        // hit
        if (this.hp > 0)
        {
            this.state = PlayerState.Hit;
            this.hitDelay = 1;

            return true;
        }

        // you die
        Log.d("Player", "Yoy diy");

        this.state = PlayerState.Dead;

        //
        this.playerEventHandler.onPlayerDie();

        // is diy is not hit?
        return false;
    }

    @Override
    public boolean isDead() {
        return this.state == PlayerState.Dead;
    }

    @Override
    public void setFireEventHandler(EnemyFireEventHandler fireEventHandler) {

    }

}
