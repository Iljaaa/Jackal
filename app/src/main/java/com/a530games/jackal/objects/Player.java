package com.a530games.jackal.objects;

import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.SpriteWithAnimation;
import com.a530games.jackal.World;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.enemies.EnemyFireEventHandler;

public class Player
{

    /**
     * Vehicle hitbox frame
     */
    public HitBox hitBox;

    public enum PlayerState
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
     * start on drop
     */
    public PlayerState state = PlayerState.Dropping;

    /**
     * Player hit points
     */
    public int hp = 3;


    /**
     *
     */
    public Vector2F direction;

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
    private final Vector2F velocity;

    private final PlayerEventHandler playerEventHandler;

    // blows data

    /**
     * Blows after death
     */
    private final SpriteWithAnimation[] blows;

    /**
     * One blow timer
     */
    private float nextBlowIn = 0.5f;

    /**
     * Summery blow up timer
     */
    private float summeryBlowUpTimer = 5f;

    /**
     *
     */
    public Sprite sprite;

    public Player(PlayerEventHandler playerEventHandler)
    {

        this.playerEventHandler = playerEventHandler;

        this.velocity = new Vector2F();

        this.hitBox = new HitBox(0, 0, 40, 40);

        this.direction = new Vector2F(0 , -1);

        this.gun = new Sprite(Assets.gun);
        this.gun.set(0, 0);

        this.sprite = new Sprite(Assets.player, 0, 0);
        this.sprite.set(1, 0);

        this.blows = new SpriteWithAnimation[5];
        for (int i = 0; i < this.blows.length; i++) {
            this.blows[i] = new SpriteWithAnimation(Assets.smallBlow);
            this.blows[i].setSpriteSize(16, 16);
            this.blows[i].setScreenMargin(16, 16);
        }
    }

    public void update(float deltaTime)
    {
        // уменьшаем задержку выстрела
        if (this.fireDelay > 0) this.fireDelay -= deltaTime;

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
                        this.hitBox.getCenterX() + (Jackal.getRandom().nextFloat() * 17 * (Jackal.getRandom().nextBoolean() ? -1 : 1)) - 20,
                        this.hitBox.getCenterY() + (Jackal.getRandom().nextFloat() * 17 * (Jackal.getRandom().nextBoolean() ? -1 : 1)) - 20
//                        this.hitBox.getCenterLeft() + (((Jackal.getRandom().nextFloat() * 2) - 1) * 32) - 8,
//                        this.hitBox.getCenterTop() + (((Jackal.getRandom().nextFloat() * 2) - 1) * 32) - 8
                );

                break;
            }
        }


    }

    public void present(Graphics g, World world)
    {
        // int playerScreenX = Math.round(this.world.player.hitBox.left);
        int playerScreenX = world.map.screenLeftPotion(this.hitBox.rect.left);
        // int playerSourceY = Math.round(this.world.player.hitBox.top);
        int playerSourceY = world.map.screenTopPotion(this.hitBox.rect.top);

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

    public void offsetCenterTo(float newLeft, float newTop) {
        this.hitBox.offsetTo(
                newLeft - this.hitBox.width() * 0.5f,
                newTop - this.hitBox.height() * 0.5f
        );
    }

    public void offsetTo(float newLeft, float newTop) {
        this.hitBox.offsetTo(newLeft, newTop);
    }

    public void drive(Vector2F direction, float deltaTime, World world)
    {
        // todo: think need her check user state

        this.velocity.set(direction.x * this.speed, direction.y * this.speed);

        if (velocity.x != 0) {
            this.driveHorizontal(velocity.x, deltaTime, world);
        }

        if (velocity.y != 0) {
            this.driveVertical(velocity.y, deltaTime, world);
        }

        this.updateSprite();
    }

    private void driveHorizontal(float xSpeed, float deltaTime, World world)
    {
        this.hitBox.moveTo(this.hitBox.rect.left + (deltaTime * xSpeed), this.hitBox.rect.top);

        if (this.checkIntersectForMove(this.hitBox, world)) {
            this.hitBox.rollback();
        }
    }

    private void driveVertical(float ySpeed, float deltaTime, World world)
    {
        this.hitBox.moveTo(this.hitBox.rect.left, this.hitBox.rect.top + (deltaTime * ySpeed));

        if (this.checkIntersectForMove(this.hitBox, world)) {
            this.hitBox.rollback();
        }
    }

    protected boolean checkIntersectForMove(HitBox aHitbox, World world)
    {
        // intersect with map
        if (world.map.isIntersect(aHitbox)) {
            return true;
        }

        // intersect enemies
        if (world.enemies.isHitboxIdIntersectWithEnemy(this.hitBox)) {
            return true;
        }

        // intersect with other peoples

        return false;
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

    public boolean isDead() {
        return this.state == PlayerState.Dead;
    }


    public void setFireEventHandler(EnemyFireEventHandler fireEventHandler) {

    }

}
