package com.a530games.jackal.menu;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.helpers.cor.Handler;
import com.a530games.framework.helpers.cor.Step;
import com.a530games.jackal.Controller;

/**
 * Wrap around paused menu
 */
public class PauseMenuWrap {

    PauseMenu menu;

    Rect grayFrame;

    private final Handler<PresentStep> liveCircle;

    private interface PresentStep extends Step
    {
        /**
         * Present step
         */
        void present(Graphics g);

        /**
         *
         */
        void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime);
    }

    class ShowStep implements PresentStep
    {
        private float frameTimer;
        private final float frameTickSize = 0.1f;

        private int frame;

        private final Rect[] pices;

        Paint p;

        public ShowStep(Rect frame)
        {
            int pieceHeight = (int) (frame.height() * 0.333333);
            int pieceWidth = (int) (frame.width() * 0.333333);

            this.pices = new Rect[]{

                new Rect(frame.left + pieceWidth, frame.top + pieceHeight, frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight),
                new Rect(frame.left + pieceWidth, frame.top + pieceHeight + pieceHeight, frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight + pieceHeight),

                new Rect(frame.left, frame.top + pieceHeight + pieceHeight, frame.left + pieceWidth, frame.top + pieceHeight + pieceHeight + pieceHeight),
                new Rect(frame.left, frame.top + pieceHeight, frame.left + pieceWidth, frame.top + pieceHeight + pieceHeight),
                new Rect(frame.left, frame.top, frame.left + pieceWidth, frame.top + pieceHeight),
                new Rect(frame.left + pieceWidth, frame.top, frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight),
                new Rect(frame.left + pieceWidth + pieceWidth, frame.top, frame.left + pieceWidth + pieceWidth + pieceWidth, frame.top + pieceHeight),
                new Rect(frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight, frame.left + pieceWidth + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight),
                    new Rect(frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight, frame.left + pieceWidth + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight + pieceHeight),

//                new Rect(frame.left, frame.top, frame.left + pieceWidth, frame.top + pieceHeight),
//                new Rect(frame.left + pieceWidth, frame.top, frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight),
//                new Rect(frame.left + pieceWidth + pieceWidth, frame.top, frame.left + pieceWidth + pieceWidth + pieceWidth, frame.top + pieceHeight),
//
//                new Rect(frame.left, frame.top + pieceHeight, frame.left + pieceWidth, frame.top + pieceHeight + pieceHeight),
//                new Rect(frame.left + pieceWidth, frame.top + pieceHeight, frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight),
//                new Rect(frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight, frame.left + pieceWidth + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight),
//
//                new Rect(frame.left, frame.top + pieceHeight + pieceHeight, frame.left + pieceWidth, frame.top + pieceHeight + pieceHeight + pieceHeight),
//                new Rect(frame.left + pieceWidth, frame.top + pieceHeight + pieceHeight, frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight + pieceHeight),
//                new Rect(frame.left + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight, frame.left + pieceWidth + pieceWidth + pieceWidth, frame.top + pieceHeight + pieceHeight + pieceHeight),
            };

            this.p = new Paint();
            this.p.setStyle(Paint.Style.FILL);
            this.p.setColor(Color.BLACK);
            this.p.setAlpha(90);

            // set first frame
            this.frame = 0;
            this.frameTimer = this.frameTickSize;
        }

        @Override
        public void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime) {
            this.update(deltaTime);
        }

        @Override
        public void update(float deltaTime)
        {
            this.frameTimer -= deltaTime;
            if (this.frameTimer <= 0) {
                this.frameTimer = this.frameTickSize;
                this.frame++;
                /*if (this.frame >= this.pices.length - 1) {
                    this.frame = this.pices.length - 1;
                }*/
            }

        }

        @Override
        public boolean isOver() {
            return !(this.frame < this.pices.length);
        }

        @Override
        public void present(Graphics g)
        {
            for (int i = 0; i <= this.frame; i++) {
                g.drawRect(this.pices[i], this.p);
            }
        }
    }

    class MenuStep implements PresentStep
    {
        Menu menu;

        Rect frame;

        Paint p;

        public MenuStep(Menu menu, Rect frame)
        {
            this.menu = menu;
            this.frame = frame;

            this.p = new Paint();
            this.p.setStyle(Paint.Style.FILL);
            this.p.setColor(Color.BLACK);
            this.p.setAlpha(90);
        }

        @Override
        public void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime) {
            this.menu.update(controller, touchEvents, deltaTime);
        }

        @Override
        public void update(float deltaTime) {

        }

        @Override
        public boolean isOver() {
            return false;
        }

        @Override
        public void present(Graphics g)
        {
            // draw background
            g.drawRect(this.frame, this.p);

            this.menu.present(g);
        }
    }

    public void reset() {
        this.liveCircle.setStep(0);
        ShowStep s = (ShowStep) this.liveCircle.get(0);
        s.frame = 0;
    }

    public PauseMenuWrap(int menuX, int menuY)
    {
        this.menu = new PauseMenu(menuX, menuY);

        // this.frame = new Rect(menuX, menuY, menuX + pausedMenu.getMenuWidth(), menuY+pausedMenu.getMenuHeight());
        // frame with padding
        this.grayFrame = new Rect(
                menuX - 10,
                menuY - 10,
                menuX + menu.getMenuWidth() + 10,
                menuY+ menu.getMenuHeight() + 10
        );

        this.liveCircle = new Handler<>();
        this.liveCircle.add(new ShowStep(this.grayFrame));
        this.liveCircle.add(new MenuStep(this.menu,this.grayFrame));
    }

    public void setEventHandler(MenuEventHandler gameScreen) {
        this.menu.setEventHandler(gameScreen);
    }

    public void update (Controller controller, TouchEventsCollection touchEvents, float deltaTime)
    {
        PresentStep currentStep = this.liveCircle.current();
        currentStep.update(controller, touchEvents, deltaTime);

        // if step is over go to next
        if (currentStep.isOver()) {
            if (this.liveCircle.hasNext()) {
                this.liveCircle.next();
            }
            else {
                // this is end of steps
                Log.d("MenuWrapper", "Ends");
            }
        }

        // this.pausedMenu.update(controller, touchEvents, deltaTime);
    }

    public void present(Graphics g)
    {
        // this.pausedMenu.present(g);

        this.liveCircle.current().present(g);

        // PresentStep s = this.liveCircle.current();
        // s.present(g);
    }
}
