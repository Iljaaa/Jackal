package com.a530games.jackal.menu;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.jackal.Controller;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Wrap around game over lose menu
 */
public class GameOverLoseMenuWrap
{
    GameOverLoseMenu menu;

    private abstract static class Behavior
    {
        public abstract void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime);

        public abstract void present(Graphics g);

        public abstract void reset();

        public abstract boolean isFinish();
    }

    private static class LightUp extends Behavior
    {
        private final Rect menuFrame;

        private final Paint paint;

        public LightUp(Rect menuFrame)
        {
            this.menuFrame = menuFrame;

            this.paint = new Paint();
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(Color.BLACK);
            this.paint.setAlpha(0);
        }

        @Override
        public void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime)
        {
            if (this.paint.getAlpha() != 90){
                this.paint.setAlpha(this.paint.getAlpha() + (int) (400 * deltaTime));
                if (this.paint.getAlpha() > 90) {
                    this.paint.setAlpha(90);
                }
            }
        }

        @Override
        public void present(Graphics g) {
            g.drawRect(this.menuFrame, this.paint);
        }

        @Override
        public boolean isFinish() {
            return (this.paint.getAlpha() == 90);
        }

        @Override
        public void reset() {
            this.paint.setAlpha(0);
        }
    }

    private static class Menu extends Behavior
    {
        private final Rect menuFrame;
        private final GameOverLoseMenu menu;

        private final Paint paint;

        public Menu(Rect menuFrame, GameOverLoseMenu menu)
        {
            this.menuFrame = menuFrame;
            this.menu = menu;

            this.paint = new Paint();
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(Color.BLACK);
            this.paint.setAlpha(90);
        }

        @Override
        public void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime) {
            this.menu.update(controller, touchEvents, deltaTime);
        }

        @Override
        public void present(Graphics g) {
            g.drawRect(this.menuFrame, this.paint);
            this.menu.present(g);
        }

        @Override
        public boolean isFinish() {
            return false;
        }

        @Override
        public void reset() {
            this.menu.reset();
        }
    }

    private final LinkedList<Behavior> liveCircle;

    private ListIterator<Behavior> iterator;
    private Behavior currentBehaviorStep;

    public GameOverLoseMenuWrap(int menuX, int menuY)
    {
        this.menu = new GameOverLoseMenu(menuX, menuY);

        Rect grayFrame = new Rect(
                menuX - 10,
                menuY - 10,
                menuX + this.menu.getMenuWidth() + 10,
                menuY + this.menu.getMenuHeight() + 10
        );

        this.liveCircle = new LinkedList<>();
        this.liveCircle.add(new LightUp(grayFrame));
        this.liveCircle.add(new Menu(grayFrame, this.menu));

        this.iterator = this.liveCircle.listIterator();

        // take first step
        this.currentBehaviorStep = this.iterator.next();
    }

    public void setEventHandler(MenuEventHandler gameScreen) {
        this.menu.setEventHandler(gameScreen);
    }

    public void reset() {
        this.iterator = this.liveCircle.listIterator();

        // reset light
        ((LightUp) this.liveCircle.get(0)).reset();
    }

    public void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime)
    {
        // update current step
        this.currentBehaviorStep.update(controller, touchEvents, deltaTime);

        // if current step of behavior is finish get next
        if (this.currentBehaviorStep.isFinish())
        {
            // if has no steps create ne list iterator
            if (this.iterator.hasNext())
            {
                this.currentBehaviorStep = this.iterator.next();
            }
        }

    }


    public void present(Graphics g) {
        this.currentBehaviorStep.present(g);
    }

}
