package com.a530games.jackal.objects;

import android.graphics.Color;
import android.util.Log;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.helpers.cor.Handler;
import com.a530games.framework.helpers.cor.Step;
import com.a530games.framework.math.Vector2;
import com.a530games.framework.math.Vector2F;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.World;
import com.a530games.jackal.map.RectCell;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.enemies.EnemyFireEventHandler;

import java.util.LinkedList;
import java.util.ListIterator;

public class DropPad implements Enemy
{
    private final Vector2F position;

    private final Vector2 dropPosition;

    boolean isDead = false;

    Handler<Step> flayHandler;

    private final World world;


    private abstract static class Behavior{

        public abstract boolean isFinish();

        public abstract void update(float deltaTime);
    }

    private static class FlayStep extends Behavior
    {
        DropPad pad;

        private final Vector2 target;

        /**
         *
         */
        private Vector2F velocity;

        public FlayStep(DropPad pad)
        {
            this.pad = pad;

            this.target = new Vector2();
            // this.velocity = new Vector2();
        }

        @Override
        public void update(float deltaTime)
        {
            // calc velocity
            if (this.velocity == null) {
                this.velocity = new Vector2F(
                    this.target.x - this.pad.position.x,
                    this.target.y - this.pad.position.y
                );
                this.velocity.nor();
                this.velocity.mul(100);
            }

            // dist
            float dist = this.pad.position.dist(this.target.x, this.target.y);

            if (10 < dist && dist < 110) {
                this.velocity.nor();
                this.velocity.mul(dist);
            }
            else if (dist < 2) {
                this.velocity.nor();
            }

            if (dist < 1) {
                this.pad.position.set(this.target.x, this.target.y);
                Log.d("DropPad", "On position");
                return;
            }

            this.pad.positionAdd(
                    this.velocity.x * deltaTime,
                    this.velocity.y * deltaTime
            );


        }
        /**
         * Set flay to position and speed
         */
        public void setTarget(int x, int y)
        {
            this.target.set(x, y);
        }

        @Override
        public boolean isFinish() {
            return (this.pad.position.x == this.target.x
                    && this.pad.position.y <= this.target.y // <- its mean can dwrop only from bottom
            );
        }
    }

    private static class PullPlayerStep extends FlayStep
    {

        public PullPlayerStep(DropPad pad)
        {
            super(pad);
        }

        @Override
        public void update(float deltaTime)
        {
            super.update(deltaTime);

            // move player
            this.pad.world.player.offsetCenterTo(
                    this.pad.position.x,
                    this.pad.position.y
            );
        }

    }

    private static class DropStep extends Behavior
    {
        private final DropPad pad;

        boolean isDrop = false;

        public DropStep(DropPad pad)
        {
            this.pad = pad;
        }

        @Override
        public void update(float deltaTime)
        {
            if (this.isDrop) return;

            this.isDrop = true;

            // call callback nethod
            this.pad.world.playerDropped();
        }

        @Override
        public boolean isFinish() {
            return this.isDrop;
        }
    }

    LinkedList<DropPad.Behavior> behaviors;

    ListIterator<DropPad.Behavior> iterator;

    Behavior currentBehaviorStep;

    public DropPad(World world)
    {
        this.world = world;

        this.position = new Vector2F();

        // drop
        this.dropPosition = new Vector2();

        this.behaviors = new LinkedList<>();
        this.behaviors.push(new FlayStep(this)); // flay away
        this.behaviors.push(new DropStep(this)); // drop
        this.behaviors.push(new PullPlayerStep(this)); // flay top drop mid point

        this.iterator = this.behaviors.listIterator();
        this.currentBehaviorStep = this.iterator.next();
    }

    protected void finalize() {
        Log.d("Dropad", "finalizew");
    }

    /**
     * Move drop pad to start position
     */
    public void moveToStart(RectCell playerDropCell)
    {
        Vector2F centerOfDropPadCell = playerDropCell.getCenter();

        // set drop position
        this.dropPosition.set((int) centerOfDropPadCell.x, (int) centerOfDropPadCell.y);

//        // move flay positions
//        MoveStep s = (MoveStep) this.flayHandler.get(0);
//        s.movePoint.set(new Vector2(this.dropPosition.x - 100, this.dropPosition.y + 150));

        // set flay away Point
        ((FlayStep) this.behaviors.get(2)).setTarget(
                (int) (centerOfDropPadCell.x) + 700,
                (int) (centerOfDropPadCell.y) + 500
        );

        // pull to drop point
        ((PullPlayerStep) this.behaviors.get(0)).setTarget(
                (int) (centerOfDropPadCell.x),
                (int) (centerOfDropPadCell.y)
        );

        // outside position
        this.position.x = centerOfDropPadCell.x;
        // this.position.x = centerOfDropPadCell.x - 100;
        this.position.y = centerOfDropPadCell.y + 500;
    }

    public void positionAdd(float x, float y) {
        this.position.add(x, y);
    }

    @Override
    public HitBox getHitBox() {
        return null;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public boolean isDead() {
        return this.isDead;
    }

    @Override
    public void update(float deltaTime, World world)
    {
        // update current step
        this.currentBehaviorStep.update(deltaTime);

        // if current step of behavior is finish get next
        if (this.currentBehaviorStep.isFinish())
        {
            // if has no steps create ne list iterator
            if (this.iterator.hasNext())
            {
                // next step
                this.currentBehaviorStep = this.iterator.next();
            }
            else {
                // here is over of iteration
                Log.d("DropPad", "flay away");
                this.isDead = true;
            }
        }

        /*if (this.flayHandler.isOver()){

        }*/

    }

    @Override
    public void present(Graphics g, Camera2D camera)
    {
        /*g.drawCircle(
                camera.screenLeft(this.position.x),
                camera.screenTop(this.position.y),
                100, Color.RED);*/
    }

    @Override
    public boolean hit(int damage) {
        return false;
    }

    @Override
    public void setFireEventHandler(EnemyFireEventHandler fireEventHandler) {

    }
}
