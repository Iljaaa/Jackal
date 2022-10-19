package com.a530games.jackal.objects;

import android.graphics.Color;
import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.helpers.cor.Handler;
import com.a530games.framework.helpers.cor.Step;
import com.a530games.framework.math.Vector2;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Cell;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.enemies.EnemyFireEventHandler;

public class DropPad implements Enemy
{
    private Vector2F position;

    private Vector2 dropPosition;

    boolean isDead = false;

    Handler flayHandler;

    private Player player;

    private class Step1 implements Step
    {
        DropPad pad;
        private Vector2 dropPosition;

        public Step1(DropPad pad, Vector2 dropPosition)
        {
            this.pad = pad;
            this.dropPosition = dropPosition;
        }

        @Override
        public void update(float deltaTime) {

            if (this.pad.position.y > this.dropPosition.y) {
                this.pad.position.y -= (deltaTime * 100);
                return;
            }

            // we move to end position
            this.pad.position.y = this.dropPosition.y;
        }

        @Override
        public boolean isOver() {
            return (this.pad.position.y == this.dropPosition.y);
        }
    }


    protected class FlayStep implements Step
    {
        DropPad pad;
        private Vector2 movePoint;
        private Vector2 velocity;

        public FlayStep(DropPad pad, Vector2 movePoint) {
            this.pad = pad;
            this.movePoint = movePoint;

            this.velocity = new Vector2();
        }

        @Override
        public void update(float deltaTime)
        {

            if (this.pad.position.x != this.movePoint.x)
            {
                float dX = (this.velocity.x * deltaTime);
                float remX = this.movePoint.x - this.pad.position.x;
                if (Math.abs(remX) <= Math.abs(dX)) {
                    this.moveTo(this.movePoint.x, this.pad.position.y);
                    // this.pad.position.x = this.movePoint.x;
                }
                else {
                    this.moveTo(this.pad.position.x + dX, this.pad.position.y);
                    // this.pad.position.x += dX;
                }
            }

            if (this.pad.position.y != this.movePoint.y)
            {
                float dY = (this.velocity.y * deltaTime);
                float remY = this.movePoint.y - this.pad.position.y;
                Log.d("remY", String.valueOf(remY) + " - " +String.valueOf(dY));
                if (Math.abs(remY) <= Math.abs(dY)) {
                    this.moveTo(this.pad.position.x, this.movePoint.y);
                    // this.pad.position.y = this.movePoint.y;
                }
                else {
                    this.moveTo(this.pad.position.x, this.pad.position.y + dY);
                    // this.pad.position.y += dY;
                }
            }

            // we move to end position
        }

        protected void moveTo(float x, float y)
        {
            // move pad
            this.pad.move(x, y);
        }

        /**
         * Set flay to position and speed
         */
        public void setMovePoint (int x, int y, int velocityX, int velocityY)
        {
            this.movePoint.set(x, y);

            // calculate move point
            this.velocity.set(
                    // (pad.position.x == movePoint.x) ? 0 : (pad.position.x < movePoint.x) ? 50 : -50,
                    // (pad.position.y == movePoint.y) ? 0 : (pad.position.y < movePoint.y) ? 50 : -50
                    velocityX, velocityY
            );
        }

        @Override
        public boolean isOver() {
            return (this.pad.position.x == this.movePoint.x && this.pad.position.y == this.movePoint.y);
        }
    }


    private class PullPlayerStep extends FlayStep
    {;

        public PullPlayerStep(DropPad pad, Vector2 movePoint) {
            super(pad, movePoint);
        }

        @Override
        protected void moveTo(float x, float y)
        {
            super.moveTo(x, y);

            // move player
            this.pad.player.moveCenter(x, y);
        }
    }

    public DropPad(Player p)
    {
        this.player = p;

        this.position = new Vector2F();
        this.dropPosition = new Vector2();

        this.flayHandler = new Handler();

        // fly to mid point
        this.flayHandler.add(new PullPlayerStep(this, new Vector2()));
        // this.flayHandler.add(new MoveStep(this, new Vector2(this.dropPosition.x - 100, this.dropPosition.y + 150))); // fly to point

        // fly to drop point
        this.flayHandler.add(new PullPlayerStep(this, new Vector2()));

        // drop
        // this.flayHandler.add(new MoveStep(this, new Vector2()));

        // flay away
        this.flayHandler.add(new FlayStep(this, new Vector2()));

    }

    protected void finalize() {
        Log.d("Dropad", "finalizew");
    }

    /**
     * Move drop pad to start position
     */
    public void moveToStart(Cell playerDropCell)
    {
        this.dropPosition.set(playerDropCell.center.x, playerDropCell.center.y);

//        // move flay positions
//        MoveStep s = (MoveStep) this.flayHandler.get(0);
//        s.movePoint.set(new Vector2(this.dropPosition.x - 100, this.dropPosition.y + 150));

        // mid point
        ((PullPlayerStep) this.flayHandler.get(0)).setMovePoint(playerDropCell.center.x - 100, playerDropCell.center.y + 150, 50, -50);

        // drop point
        ((PullPlayerStep) this.flayHandler.get(1)).setMovePoint(playerDropCell.center.x, playerDropCell.center.y, 50, -50);

        // flay awey
        ((FlayStep) this.flayHandler.get(2)).setMovePoint(playerDropCell.center.x + 700, playerDropCell.center.y + 500, 50, 50);

        this.position.x = playerDropCell.center.x - 100;
        this.position.y = playerDropCell.center.y + 500;
    }

    public void move(float x, float y) {
        this.position.set(x, y);
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
        Step currentStep = this.flayHandler.getCurrentStep();
        currentStep.update(deltaTime);
        if (currentStep.isOver()) this.flayHandler.next();

        // if handler ends
        if (this.flayHandler.isOver()){
            Log.d("DropPad", "IsDed");
            this.isDead = true;
        }



        /*if (this.flayHandler.isOver()){

        }*/


    }

    @Override
    public void present(Graphics g, World world)
    {

        g.drawCircle(
                world.map.screenLeftPotion(this.position.x),
                world.map.screenTopPotion(this.position.y),
                100, Color.RED);

    }

    @Override
    public boolean hit(int damage) {
        return false;
    }

    @Override
    public void setFireEventHandler(EnemyFireEventHandler fireEventHandler) {

    }
}
