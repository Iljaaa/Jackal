package com.a530games.jackal.objects;

import android.graphics.Point;
import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.SpriteWithAnimation;
import com.a530games.jackal.World;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.objects.enemies.EnemyFireEventHandler;
import com.a530games.jackal.objects.enemies.RotateVehicle;

public class Player extends RotateVehicle
{


    enum PlayerState
    {
        Dropping,

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
    public int hp = 3;

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

    /**
     *
     */
    private final float blinkTime = 0.1f;

    /**
     * Blink on hit
     */
    private boolean isBlink = false;

    //
    public Vector2F turret = new Vector2F(0, -1);

    //
    public Sprite gun;

    // скорость перемещения
    protected float speed = 100;

    //
    private Vector2F velocity;

    private PlayerEventHandler playerEventHandler;

    // blows data

    /**
     * Blows after death
     */
    private SpriteWithAnimation[] blows;

    /**
     * One blow timer
     */
    private float nextBlowIn = 0.5f;

    /**
     * Summery blow up timer
     */
    private float summeryBlowUpTimer = 5f;

    public Player(int startX, int startY, PlayerEventHandler playerEventHandler)
    {
        super(startX, startY, Assets.player);

        this.playerEventHandler = playerEventHandler;

        this.velocity = new Vector2F();

        this.gun = new Sprite(Assets.gun);
        this.gun.set(0, 0);

        this.sprite.set(1, 0);


        this.blows = new SpriteWithAnimation[5];
        for (int i = 0; i < this.blows.length; i++) {
            this.blows[i] = new SpriteWithAnimation(Assets.smallBlow);
            this.blows[i].setSpriteSize(16, 16);
            this.blows[i].setScreenMargin(16, 16);
        }
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

        this.updateBlow(deltaTime);
    }

    private void updateHitTimer(float deltaTim)
    {
        if (this.hitDelay > 0) this.hitDelay -= deltaTim;

        this.blinkDelay -= deltaTim;
        if (this.blinkDelay <= 0) {
            this.isBlink = !this.isBlink;
            this.blinkDelay = this.blinkTime;
        }

        // delay is over
        if (this.hitDelay <= 0) {
            this.state = PlayerState.OnLine;
            this.isBlink = false;
        }
    }

    private void updateBlow (float deltaTime)
    {
        // is blows finish
        /*this.summeryBlowUpTimer -= deltaTime;
        if (this.summeryBlowUpTimer <= 0)
        {
            this.state = Tank.STATE_DEAD;

            if (this.dieEventHandler != null) {
                this.dieEventHandler.enemyDie(this);
            }

            return;
        }*/

        for (int i = 0; i < this.blows.length; i++)
        {
            // SpriteWithAnimation blow = ;
            if (!this.blows[i].isStart) continue;
            this.blows[i].update(deltaTime);
        }

        this.nextBlowIn -= deltaTime;
        if (nextBlowIn <= 0)
        {
            this.nextBlowIn = 0.05f;
            for (int i = 0; i < this.blows.length; i++)
            {
                // SpriteWithAnimation blow = this.blows[i];
                if (this.blows[i].isStart) continue;
                // this.blow.start(this.hitBox.getCenterLeft(), this.hitBox.getCenterTop());

                // todo: fix magic numbers
                this.blows[i].start(
                        this.hitBox.getCenterLeft() + (Jackal.getRandom().nextFloat() * 17 * (Jackal.getRandom().nextBoolean() ? -1 : 1)) - 20,
                        this.hitBox.getCenterTop() + (Jackal.getRandom().nextFloat() * 17 * (Jackal.getRandom().nextBoolean() ? -1 : 1)) - 20
//                        this.hitBox.getCenterLeft() + (((Jackal.getRandom().nextFloat() * 2) - 1) * 32) - 8,
//                        this.hitBox.getCenterTop() + (((Jackal.getRandom().nextFloat() * 2) - 1) * 32) - 8
                );

                break;
            }
        }


    }

    @Override
    public void present(Graphics g, World world)
    {
        // int playerScreenX = Math.round(this.world.player.hitBox.left);
        int playerScreenX = world.map.screenLeftPotion(this.hitBox.left);
        // int playerSourceY = Math.round(this.world.player.hitBox.top);
        int playerSourceY = world.map.screenTopPotion(this.hitBox.top);

        // this.drawPlayerHitBox(g, playerScreenX, playerSourceY);

        /*
         * 12 = 0.5 * 64 (player sprite width) - 40 (player hitbox width)
         */
        if (!this.isBlink) {
            g.drawPixmap(
                    this.sprite.image,
                    Math.round(playerScreenX) - 12, //
                    Math.round(playerSourceY) - 12,
                    this.sprite.getLeft(),
                    this.sprite.getTop(),
                    this.sprite.width, // 64,
                    this.sprite.height); // 64);

            // draw turret
            g.drawPixmap(
                    this.gun.image,
                    Math.round(playerScreenX) - 12, //
                    Math.round(playerSourceY) - 12,
                    this.gun.getLeft(),
                    this.gun.getTop(),
                    this.gun.width, // 64,
                    this.gun.height); // 64);
        }

        // draw blow
        for (int i = 0; i < this.blows.length; i++) {
            // SpriteWithAnimation blow = this.blows[i];
            this.blows[i].present(g, world);
        }

    }

    @Override
    public void move(Vector2F direction, float deltaTime, World world)
    {
        this.move(direction.x, direction.y, deltaTime, world);
    }

    public void setPoint(Point mapPint)
    {
        this.hitBox.moveTo(mapPint.x, mapPint.y);
    }

    public void move(float x, float y, float deltaTime, World world)
    {
        // todo: think need her check user state

        this.velocity.set(x * this.speed, y * this.speed);

        super.move(this.velocity, deltaTime, world);

        this.updateSprite();
    }

    public void setTurretAngle (Vector2F direction)
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

    public boolean isOnline() {
        return (this.state == PlayerState.OnLine || this.state == PlayerState.Hit);
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
            this.blinkDelay = this.blinkTime;
            this.isBlink = true;

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
