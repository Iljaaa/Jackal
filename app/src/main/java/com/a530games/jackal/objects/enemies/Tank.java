package com.a530games.jackal.objects.enemies;

import com.a530games.framework.helpers.FloatPoint;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.World;
import com.a530games.jackal.objects.EnemyEventHandler;

/**
 * todo: сделать прицеливание не ограниченым по времени
 * todo: сделать прицеливание по оптимальной траектории
 */
public class Tank extends Vehicle
{
    private Vector2 velocity;

    private final Vector2 targetAngle = new Vector2(1, 0);

    //
    public Vector2 turretAngle = new Vector2(1, 0);

    private float rotateTimer = 0;

    private int doConst = 0;

    private int[][] dirs = {
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
    public void update(float deltaTime, Enemy player, World world)
    {
        if (this.rotateTimer <= 0)
        {
            this.rotateTimer = 1;

            this.doConst++;

            // calculate angle by who points
            FloatPoint playerCenter = player.getHitBox().getCenter();
            FloatPoint tankCenter = this.hitBox.getCenter();

            // d
            float y = playerCenter.top - tankCenter.top;
            float x = playerCenter.left- tankCenter.left;

            // random angle
            // this.targetAngle = Jackal.getRandom().nextFloat() * 2;
            // this.targetAngle.set(Jackal.getRandom().nextFloat(), Jackal.getRandom().nextFloat());
            this.targetAngle.set(x, y);
            this.targetAngle.nor();
        }

        if (this.doConst == 0) {
            this.move(this.velocity, deltaTime, world);
        }

        if (this.doConst == 1)
        {
            float turretAngleInDegrees = this.turretAngle.angleInDegrees();
            float targetAngleInDegrees = this.targetAngle.angleInDegrees();
            float rotate = (turretAngleInDegrees < targetAngleInDegrees) ? 5 : -5;

            float degDelta = targetAngleInDegrees - turretAngleInDegrees;
            if (Math.abs(degDelta) < 5) rotate = degDelta;

            this.turretAngle.rotate(rotate);
            //if (turretAngleInDegrees < targetAngleInDegrees) this.turretAngle.rotate(5);
            // if (turretAngleInDegrees > targetAngleInDegrees) this.turretAngle.rotate(-5);
        }

        if (this.doConst == 2)
        {
            this.fire();

            // generate new angle
            int[] a = this.dirs[Jackal.getRandom().nextInt(this.dirs.length)];
            this.velocity.set(a[0], a[1]);
            this.updateSprite(this.velocity);

            this.doConst = 0;
        }

        this.rotateTimer -= deltaTime;
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
