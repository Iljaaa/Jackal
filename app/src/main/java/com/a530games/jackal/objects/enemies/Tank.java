package com.a530games.jackal.objects.enemies;

import android.graphics.Color;
import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatPoint;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.SpriteWithAnimation;
import com.a530games.jackal.World;
import com.a530games.jackal.objects.EnemyEventHandler;

/**
 * todo: сделать прицеливание не ограниченым по времени
 * todo: сделать прицеливание по оптимальной траектории
 */
public class Tank extends Vehicle
{
    public static final int STATE_READY = 0; // raady not inited
    public static final int STATE_AIMING = 2; //
    public static final int STATE_ROTATE_TURRET = 4; // rotate turret
    public static final int STATE_MOVE = 6; //
    public static final int STATE_BLOWUP = 95;
    public static final int STATE_DEAD = 100;

    /**
     * Tank state
     */
    private int state;

    private Vector2 velocity;

    //
    public Vector2 turretAngle = new Vector2(1, 0);

    /**
     * Target to player
     */
    private final Vector2 targetAngle = new Vector2(1, 0);

    /**
     * health points
     */
    private int hp = 2;

    /**
     *
     */
    private float rotateTimer = 0;

    /**
     * Blows after death
     */
    private SpriteWithAnimation[] blows;

    private float nextBlowIn = 0.5f;

    /**
     * Directions on random move
     */
    private final int[][] dirs = {
            {40, 0},
            {40, 40},
            {0, 40},
            {-40, 40},
            {-40, 0},
            {-40, -40},
            {0, -40},
            {40, -40},
    };

    private EnemyEventHandler eventHandler = null;

    public Tank(int startX, int startY)
    {
        super(startX, startY, Assets.tank);
        this.velocity = new Vector2(0 ,1);
        this.state = Tank.STATE_MOVE;

        // this.blow = new SpriteWithAnimation(Assets.playerFire);
        // this.blows = new SpriteWithAnimation(Assets.playerFire);

        this.blows = new SpriteWithAnimation[5];
        for (int i = 0; i < this.blows.length; i++) {
            this.blows[i] = new SpriteWithAnimation(Assets.playerFire);
            this.blows[i].setSpriteSize(32, 32);
            this.blows[i].setScreenMargin(16, 16);
        }
    }

    @Override
    public boolean hasTurret() {
        return true;
    }

    @Override
    public Vector2 getTurretAngle() {
        return this.turretAngle;
    }

    @Override
    public Vector2 getTargetAngle() {
        return this.targetAngle;
    }

    @Override
    public void update(float deltaTime, World world)
    {

        if (this.state == Tank.STATE_DEAD) {
            return;
        }

        // blow up before dead
        if (this.state == Tank.STATE_BLOWUP) this.updateBlow(deltaTime);

        if (this.state == Tank.STATE_AIMING) this.updateAiming(world);
        if (this.state == Tank.STATE_ROTATE_TURRET) this.updateRotateTurret(deltaTime);
        if (this.state == Tank.STATE_MOVE) this.updateMove(deltaTime, world);
    }

    private void updateAiming(World world)
    {
        FloatPoint playerCenter = world.player.getHitBox().getCenter();
        FloatPoint tankCenter = this.hitBox.getCenter();

        float y = playerCenter.top - tankCenter.top;
        float x = playerCenter.left - tankCenter.left;

        // random angle
        // this.targetAngle = Jackal.getRandom().nextFloat() * 2;
        // this.targetAngle.set(Jackal.getRandom().nextFloat(), Jackal.getRandom().nextFloat());
        this.targetAngle.set(x, y);
        this.targetAngle.nor();

        // goto turret
        this.rotateTimer = 1;
        this.state = Tank.STATE_ROTATE_TURRET;
    }

    private void updateRotateTurret(float deltaTime)
    {
        // todo: rotate on speed
        float turretAngleInDegrees = this.turretAngle.angleInDegrees();
        float targetAngleInDegrees = this.targetAngle.angleInDegrees();
        float rotate = (turretAngleInDegrees < targetAngleInDegrees) ? 5 : -5;

        float degDelta = targetAngleInDegrees - turretAngleInDegrees;
        if (Math.abs(degDelta) < 5) rotate = degDelta;

        this.turretAngle.rotate(rotate);

        // todo: if you aim

        this.rotateTimer -= deltaTime;
        if (this.rotateTimer <= 0)
        {
            this.fire();


            // goto move

            // generate new move angle
            int[] a = this.dirs[Jackal.getRandom().nextInt(this.dirs.length)];
            this.velocity.set(a[0], a[1]);
            this.updateSprite(this.velocity);

            this.rotateTimer = 1;
            this.state = Tank.STATE_MOVE;
        }
    }

    private void updateMove(float deltaTime, World world)
    {
        this.move(this.velocity, deltaTime, world);

        this.rotateTimer -= deltaTime;
        if (this.rotateTimer <= 0){
            this.state = Tank.STATE_AIMING;
        }
    }

    private void updateBlow (float deltaTime)
    {

        for (int i = 0; i < this.blows.length; i++)
        {
            // SpriteWithAnimation blow = ;
            if (!this.blows[i].isStart) continue;
            this.blows[i].update(deltaTime);
        }

        this.nextBlowIn -= deltaTime;
        if (nextBlowIn <= 0)
        {
            this.nextBlowIn = 0.5f * Jackal.getRandom().nextFloat();
            for (int i = 0; i < this.blows.length; i++)
            {
                // SpriteWithAnimation blow = this.blows[i];
                if (this.blows[i].isStart) continue;
                // this.blow.start(this.hitBox.getCenterLeft(), this.hitBox.getCenterTop());
                // todo: fix magic numbers
                this.blows[i].start(
                        this.hitBox.getCenterLeft() + (((Jackal.getRandom().nextFloat() * 2) - 1) * 32) - 32,
                        this.hitBox.getCenterTop() + (((Jackal.getRandom().nextFloat() * 2) - 1) * 32) - 32
                );
            }
        }
    }

    @Override
    public void present(Graphics g, World world)
    {
        Rect screenHitBox = this.getScreenDrawHitbox(world.map);

        // Sprite s = this.getSprite();

        // todo: not draw enemie if if is not in screem

        g.drawPixmap(
                this.sprite.image,
                screenHitBox.left + this.sprite.screenMarginLeft,
                screenHitBox.top + this.sprite.screenMarginTop,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);

        // draw hitbox
        g.drawRect(screenHitBox, Color.RED);
        /*this.drawEnemyHitBox(
                g,
                this.world.map.screenLeftPotion(b.hitBox.left),
                this.world.map.screenTopPotion(b.hitBox.top),
                Math.round(b.hitBox.getWidth()),
                Math.round(b.hitBox.getHeight())
        );*/

        // draw blow
        for (int i = 0; i < this.blows.length; i++) {
            // SpriteWithAnimation blow = this.blows[i];
            this.blows[i].present(g, world);
        }

        // its only for tanks and turrets
        if (this.hasTurret())
        {
            // target
            this.drawAngle(g,
                    screenHitBox.centerX(),
                    screenHitBox.centerY(),
                    this.getTargetAngle(),
                    Color.LTGRAY
            );

            // turret
            this.drawAngle(g,
                    screenHitBox.centerX(),
                    screenHitBox.centerY(),
                    this.getTurretAngle(),
                    Color.GREEN
            );
        }
    }

    /**
     *
     * @param g graphic object
     */
    private void drawAngle (Graphics g, int screenCenterLeft, int screenCenterTop, Vector2 angleVector, int color)
    {
        // int centerTop = Math.round(this.world.player.hitBox.getCenterTop());

        // отрисовываем вектор направления
        // double s = Math.sin(this.world.player.getAngle() * Math.PI);
        // double c = Math.cos(this.world.player.getAngle() * Math.PI);

        /*g.drawLine(
                screenCenterLeft,
                screenCenterTop,
                screenCenterLeft + (int) Math.round(Math.sin(angle * Math.PI) * 50),
                screenCenterTop + (int) Math.round(Math.cos(angle * Math.PI) * 50),
                Color.GREEN);*/

        g.drawLine(
                screenCenterLeft,
                screenCenterTop,
                Math.round(screenCenterLeft + (angleVector.x * 50)),
                Math.round(screenCenterTop + (angleVector.y * 50)),
                color);
    }

    public void setEventHandler(EnemyEventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    private void fire()
    {
        if (this.eventHandler != null) {
            this.turretAngle.nor();
            this.eventHandler.enemyFire(this.hitBox.getCenterLeft(), this.hitBox.getCenterTop(), this.turretAngle);
        }
    }

    @Override
    public void hit(int damage)
    {
        if (this.hp <= 0) return;

        this.hp--;
        if (this.hp == 0)
        {
            // set blowup status
            this.state = Tank.STATE_BLOWUP;
            // this.state = Tank.STATE_DEAD;


        }

        // todo: call event handler
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

    /*
     *
     */
    /*public void move2(Vector2 velocity, float deltaTime, World world)
    {


        if (velocity.x != 0) {
            this.moveHorizontal(velocity, deltaTime);
        }

        if (velocity.y != 0) {
            this.moveVertical(velocity, deltaTime);
        }
    }

    private void moveHorizontal(Vector2 velocity, float deltaTime, World world)
    {
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * velocity.x), this.hitBox.top);

        if (this.checkIntersectForMove(this.hitBox, world)) {
            this.hitBox.rollback();
        }
    }

    private void moveVertical(Vector2 velocity, float deltaTime, World world)
    {
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * velocity.y));

        if (this.checkIntersectForMove(this.hitBox, world)) {
            this.hitBox.rollback();
        }
    }*/
}
