package com.a530games.jackal.objects.enemies;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.Settings;
import com.a530games.jackal.World;
import com.a530games.jackal.objects.SmallBlowsSwarm;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
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
     * Bullet speed
     */
    public static final int BULLET_SPEED = 140;

    /**
     * Tank state
     */
    private int state;

    /**
     * health points
     */
    private int hp = 2;

    /**
     * Velocity
     */
    private final Vector2F velocity;

    /**
     * Current turret angle
     */
    public Vector2F turretAngle = new Vector2F(1, 0);

    /**
     * Target to player
     */
    private final Vector2F targetAngle = new Vector2F(1, 0);

    /**
     * Rotate turret angle
     */
    public float rotateTurretTimer = 0;

    /**
     * Move tank timer
     */
    public float moveTimer = 0;

    /**
     * Blows after death
     */
    private final SmallBlowsSwarm dieBlows;

    /**
     * Summery blow up timer
     */
    private float summeryBlowUpTimer = 5f;

    /**
     * Directions on random move
     */
    private final int[][] dirs = {
            {50, 0},
            {50, 50},
            {0, 50},
            {-50, 50},
            {-50, 0},
            {-50, -50},
            {0, -50},
            {40, -50},
    };

    /**
     * Handler on enemy fire, check in word on add bullet
     */
    private EnemyFireEventHandler fireEventHandler = null;

    private EnemyDieEventHandler dieEventHandler = null;

    /**
     * Paints for lines
     */
    private final Paint velocityLinePaint, turretLinePaint, targetLinePaint;

    public abstract static class Behavior{

        public abstract boolean isFinish();

        public abstract void update(float deltaTime, World world);

    }

    private static class Aiming extends Behavior
    {
        Tank tank;

        public Aiming(Tank tank) {
            this.tank = tank;
        }

        @Override
        public void update(float deltaTime, World world)
        {
            //
            float y = world.player.hitBox.getCenterY() - this.tank.hitBox.getCenterY();
            float x = world.player.hitBox.getCenterX() - this.tank.hitBox.getCenterX();

            // random angle
            this.tank.targetAngle.set(x, y);
            this.tank.targetAngle.nor();

            // goto turret
            this.tank.rotateTurretTimer = 5;
            this.tank.state = Tank.STATE_ROTATE_TURRET;
        }

        @Override
        public boolean isFinish() {
            return true;
        }
    }

    private static class RotateTurret extends Behavior
    {
        Tank tank;

        public RotateTurret(Tank tank) {
            this.tank = tank;
        }

        @Override
        public void update(float deltaTime, World world)
        {
            float turretAngleInDegrees = this.tank.turretAngle.angleInDegrees();
            float targetAngleInDegrees = this.tank.targetAngle.angleInDegrees();

            float deltaDegrees = targetAngleInDegrees - turretAngleInDegrees;

            if (Math.abs(deltaDegrees) > 0)
            {

                if (Math.abs(deltaDegrees) > 3)
                {
                    float rotateSpeed = 30 * deltaTime;
                    if (deltaDegrees < 0) rotateSpeed *= -1;

                    this.tank.turretAngle.rotate(rotateSpeed);
                }
                else {
                    // deltaDegrees *= -1;
                    this.tank.turretAngle.set(this.tank.targetAngle);
                }


            }
            else {
                // we are aim
                this.tank.rotateTurretTimer = 0;
            }

            this.tank.rotateTurretTimer -= deltaTime;
        }

        @Override
        public boolean isFinish() {
            return this.tank.rotateTurretTimer <= 0;
        }
    }


    /**
     * Fire
     */
    private static class Fire extends Behavior
    {
        Tank tank;

        public Fire(Tank tank) {
            this.tank = tank;
        }

        @Override
        public void update(float deltaTime, World world) {
            this.tank.fire();
        }

        @Override
        public boolean isFinish() {
            return true;
        }
    }

    private static class ChangeDirection extends Behavior
    {
        Tank tank;

        public ChangeDirection(Tank tank) {
            this.tank = tank;
        }

        @Override
        public void update(float deltaTime, World world)
        {
            int[] a = this.tank.dirs[Jackal.getRandom().nextInt(this.tank.dirs.length)];
            this.tank.velocity.set(a[0], a[1]);
            this.tank.updateSprite(this.tank.velocity);

            // 2 secs drive
            this.tank.moveTimer = 2f;
            this.tank.state = Tank.STATE_MOVE;
        }

        @Override
        public boolean isFinish() {
            return true;
        }
    }

    private static class Drive extends Behavior
    {
        Tank tank;

        public Drive(Tank tank) {
            this.tank = tank;
        }

        @Override
        public void update(float deltaTime, World world)
        {

            if (this.tank.moveTimer <= 0) {
                this.tank.state = Tank.STATE_AIMING;
                return;
            }

            this.tank.moveTimer -= deltaTime;

            this.tank.drive(this.tank.velocity, deltaTime, world);
        }

        @Override
        public boolean isFinish() {
            return this.tank.moveTimer <= 0;
        }
    }

    LinkedList<Behavior> behaviors;

    ListIterator<Behavior> currentIterator;

    Behavior currentBehaviorStep;

    public Tank(Vector2F spownPoint)
    {
        super(spownPoint.x, spownPoint.y, 40, 40, Assets.tank);
        this.velocity = new Vector2F(0 ,1);

        //
        this.behaviors = new LinkedList<>();
        this.behaviors.push(new Fire(this));
        this.behaviors.push(new RotateTurret(this));
        this.behaviors.push(new Aiming(this));
        this.behaviors.push(new Drive(this));
        this.behaviors.push(new ChangeDirection(this));

        this.currentIterator = this.behaviors.listIterator();

        // take first step
        this.currentBehaviorStep = this.currentIterator.next();


        this.dieBlows = new SmallBlowsSwarm(5);

        this.velocityLinePaint = new Paint();
        this.velocityLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.velocityLinePaint.setStrokeWidth(3);
        this.velocityLinePaint.setColor(Color.WHITE);

        this.turretLinePaint = new Paint();
        this.turretLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.turretLinePaint.setStrokeWidth(3);
        this.turretLinePaint.setColor(Color.YELLOW);

        this.targetLinePaint = new Paint();
        this.targetLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.targetLinePaint.setStrokeWidth(2);
        this.targetLinePaint.setColor(Color.GREEN);

        // start on move
        // this.setMoved(2);
    }

    protected void finalize() {
        Log.d("Tank", "finalizew");
    }

    @Override
    public boolean isDead() {
        return (this.state == Tank.STATE_DEAD);
    }

    @Override
    public void update(float deltaTime, World world)
    {

        if (this.state == Tank.STATE_DEAD) {
            return;
        }

        // is tank is blow up
        // blow up before dead
        if (this.state == Tank.STATE_BLOWUP) {
            this.updateBlow(deltaTime);
            return;
        }

        // update current step
        this.currentBehaviorStep.update(deltaTime, world);

        // if current step of behavior is finish get next
        if (this.currentBehaviorStep.isFinish())
        {
            // if has no steps create ne list iterator
            if (!this.currentIterator.hasNext())
            {
                // here is over of iteration
                this.currentIterator = this.behaviors.listIterator();
            }

            this.currentBehaviorStep = this.currentIterator.next();
        }


        //
        /*if (this.state == Tank.STATE_AIMING) this.updateAiming(world);
        if (this.state == Tank.STATE_ROTATE_TURRET) this.updateRotateTurret(deltaTime);
        if (this.state == Tank.STATE_MOVE) this.updateMove(deltaTime, world);*/
    }

    /*private void updateAiming(World world)
    {
        // PointF playerCenter = world.player.hitBox.getCenter();
        // PointF tankCenter = this.hitBox.getCenter();

        // float y = playerCenter.y - tankCenter.y;
        // float x = playerCenter.x - tankCenter.x;

        float y = world.player.hitBox.getCenterY() - this.hitBox.getCenterY();
        float x = world.player.hitBox.getCenterX() - this.hitBox.getCenterX();

        // random angle
        this.targetAngle.set(x, y);
        this.targetAngle.nor();

        // goto turret
        this.rotateTimer = 1;
        this.state = Tank.STATE_ROTATE_TURRET;
    }*/

    /*private void updateRotateTurret(float deltaTime)
    {
        float turretAngleInDegrees = this.turretAngle.angleInDegrees();
        float targetAngleInDegrees = this.targetAngle.angleInDegrees();
        float rotate = (turretAngleInDegrees < targetAngleInDegrees) ? 5 : -5;

        float degDelta = targetAngleInDegrees - turretAngleInDegrees;
        if (Math.abs(degDelta) < 5) rotate = degDelta;

        this.turretAngle.rotate(rotate);

        this.rotateTimer -= deltaTime;
        if (this.rotateTimer <= 0)
        {
            this.fire();

            this.setMoved();
        }
    }*/

    /*private void setMoved()
    {
        this.setMoved(1);
    }*/

    /*private void setMoved(int moveTimer)
    {
        // generate new move angle
        int[] a = this.dirs[Jackal.getRandom().nextInt(this.dirs.length)];
        this.velocity.set(a[0], a[1]);
        this.updateSprite(this.velocity);

        this.moveTimer = moveTimer;
        this.state = Tank.STATE_MOVE;
    }*/

    /*private void updateMove(float deltaTime, World world)
    {
        this.drive(this.velocity, deltaTime, world);

        this.moveTimer -= deltaTime;
        if (this.moveTimer <= 0){
            this.state = Tank.STATE_AIMING;
        }
    }*/

    private void updateBlow (float deltaTime)
    {
        this.dieBlows.update(deltaTime);

        // is blows finish
        this.summeryBlowUpTimer -= deltaTime;
        if (this.summeryBlowUpTimer <= 0)
        {
            this.state = Tank.STATE_DEAD;

            if (this.dieEventHandler != null) {
                this.dieEventHandler.enemyDie(this);
            }
        }
    }

    @Override
    public void present(Graphics g, Camera2D camera)
    {
        if (this.state == Tank.STATE_DEAD) {
            return;
        }

        // draw tank
        g.drawPixmap(
                this.sprite.image,
                camera.screenLeft(this.hitBox.rect.left + this.sprite.screenMarginLeft),
                camera.screenTop(this.hitBox.rect.top + this.sprite.screenMarginTop),
                // screenHitBox.left + this.sprite.screenMarginLeft,
                // screenHitBox.top + this.sprite.screenMarginTop,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);

        //
        int screenCenterX =  camera.screenLeft(this.hitBox.getCenterX());
        int screenCenterY = camera.screenTop(this.hitBox.getCenterY());

        // draw blow
        if (this.state == Tank.STATE_BLOWUP) {
            this.dieBlows.present(g,
                    camera.screenLeft(this.hitBox.rect.left),
                    camera.screenTop(this.hitBox.rect.top)
            );
        }


        // target
        // this.drawAngle(g, screenCenterX, screenCenterY, this.targetAngle, this.targetLinePaint);

        // turret
        // this.drawAngle(g, screenCenterX, screenCenterY, this.turretAngle, this.turretLinePaint);

        // velocity
        /*g.drawLine(
                screenCenterX,
                screenCenterY,
                (int) (screenCenterX + this.velocity.x),
                (int) (screenCenterY + this.velocity.y),
                this.velocityLinePaint);*/

    }

    /**
     *
     * @param g graphic object
     */
    private void drawAngle (Graphics g, int screenCenterX, int screenCenterY, Vector2F angleVector, Paint paint)
    {
        g.drawLine(
                screenCenterX,
                screenCenterY,
                Math.round(screenCenterX + (angleVector.x * 50)),
                Math.round(screenCenterY + (angleVector.y * 50)),
                paint);
    }

    public void setFireEventHandler(EnemyFireEventHandler fireEventHandler) {
        this.fireEventHandler = fireEventHandler;
    }

    public void setDieEventHandler(EnemyDieEventHandler dieEventHandler) {
        this.dieEventHandler = dieEventHandler;
    }

    private void fire()
    {
        if (this.fireEventHandler != null)
        {
            this.turretAngle.nor();
            this.fireEventHandler.enemyFire(
                    this.hitBox.getCenterX(),
                    this.hitBox.getCenterY(),
                    this.turretAngle.x * BULLET_SPEED,
                    this.turretAngle.y * BULLET_SPEED,
                    2f
            );
        }
    }

    @Override
    public boolean hit(int damage)
    {
        if (this.hp <= 0) return false;

        this.hp--;
        if (this.hp == 0)
        {
            // set blowup status
            this.state = Tank.STATE_BLOWUP;
            // this.state = Tank.STATE_DEAD;
        }

        this.playHitSound();

        return true;
    }

    private void playHitSound ()
    {
        // play hit sound
        /*if (this.state == Tank.STATE_DEAD || this.state == Tank.STATE_BLOWUP) {
            return;
        }*/

        if(Settings.soundEnabled)
        {
            if (this.hp > 0) Assets.tankHit2.play(1);
            else Assets.tankBlow2.play(1);
        }
    }

    private void updateSprite(Vector2F direction)
    {
        switch (direction.getQuater()) {
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

        // this.sprite.row = 0;
        // this.sprite.col = 0;
        /*float angle = direction.angleInDegrees();

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
        */
    }
}
