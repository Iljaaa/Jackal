package com.a530games.jackal;

import com.a530games.framework.ControllerHandler;
import com.a530games.framework.Input;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.objects.ControllerPresenter;

/**
 * Composite of input class
 * use touch events, key events and controller input for composite to one class
 * who looks like joypad
 *
 * https://developer.android.com/training/game-controllers/controller-input
 *
 */
public class Controller
{
    // right buttons
    boolean isA = false,  isB = false,  isX = false, isY = false;

    // left shifts
    private boolean isL1 = false, isL2 = false;

    // right shifts
    private boolean isR1 = false, isR2 = false;

    // start and select
    private boolean isSelect = false, isStart = false;

    // sticks position
    private final Vector2 leftStick, rightStick;

    public boolean isStart() {
        return this.isStart;
    }

    public Controller() {
        this.leftStick = new Vector2();
        this.rightStick = new Vector2();
    }

    public void updateByController(ControllerHandler controller)
    {
        this.isA = controller.isA();
        this.isB = controller.isB();
        this.isX = controller.isX();
        this.isY = controller.isY();

        this.isL1 = controller.isL1();
        this.isL2 = controller.isL2();
        this.isR1 = controller.isR1();
        this.isR2 = controller.isR2();

        this.isStart = controller.isStart();
        this.isSelect = controller.isSelect();

        this.leftStick.set(controller.getLeftStickDirection());
        this.rightStick.set(controller.getRightStickDirection());
    }

    public Vector2 getLeftStickDirection() {
        return this.leftStick;
    }

    public Vector2 getRightStickDirection() {
        return this.rightStick;
    }

    public boolean isA() {
        return this.isA;
    }

    public boolean isB() {
        return this.isB;
    }

    public boolean isX() {
        return this.isX;
    }

    public boolean isY() {
        return this.isY;
    }

    public boolean isR1() {
        return this.isR1;
    }

    public boolean isL1() {
        return this.isL1;
    }

    public boolean isR2() {
        return this.isR2;
    }

    public boolean isL2() {
        return this.isL2;
    }

    /**
     *
     * @param touchEvents
     * @param presenter Controller presenter used like mask to convert touch to buttons position
     */
    public void updateByTouchEvents(TouchEventsCollection touchEvents, ControllerPresenter presenter)
    {
        // move player by touch events
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i); //.get(i);
            if (event == null) continue;

            // обработка паузы
            if(Input.TouchEvent.TOUCH_DOWN != event.type) continue;

            //
            if (presenter.topButton.isPointInside(event.x, event.y)) {
                this.leftStick.y = -1;
            }
            if (presenter.rightButton.isPointInside(event.x, event.y)) {
                this.leftStick.x = 1;
            }
            if (presenter.downButton.isPointInside(event.x, event.y)) {
                this.leftStick.y = 1;
            }
            if (presenter.leftButton.isPointInside(event.x, event.y)) {
                this.leftStick.x = -1;
            }

            if (presenter.rightAButton.isPointInside(event.x, event.y)) {
                this.isA = true;
            }
            if (presenter.rightBButton.isPointInside(event.x, event.y)) {
                this.isB = true;
            }


        }
    }
}
