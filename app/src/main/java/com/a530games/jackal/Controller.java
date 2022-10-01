package com.a530games.jackal;

import android.view.KeyEvent;

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

    /**
     * Touch pointer for youch
     */
    private int _leftStickPointer = -1, _rightStickPointer = -1;

    /**
     * Event handler
     */
    private ControllerEventHandler eventHandler;

    public boolean isStart() {
        return this.isStart;
    }

    public Controller() {
        this.leftStick = new Vector2();
        this.rightStick = new Vector2();
    }

    public void setEventHandler(ControllerEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void updateByController(ControllerHandler controller)
    {
        /*boolean newValue = controller.isA();
        if (newValue != this.isA && this.eventHandler != null) {
            this.isA = newValue;
            if (newValue) this.eventHandler.onButtonDown(KeyEvent.KEYCODE_4);
            else this.eventHandler.onButtonUp(KeyEvent.KEYCODE_4);
        }*/

        this.isA = controller.isA();
        this.isB = controller.isB();
        this.isX = controller.isX();
        this.isY = controller.isY();

        this.isL1 = controller.isL1();
        this.isL2 = controller.isL2();
        this.isR1 = controller.isR1();
        this.isR2 = controller.isR2();

        // start button
        boolean newValue = controller.isStart();
        if (newValue != this.isStart && this.eventHandler != null) {
            if (newValue) this.eventHandler.onButtonDown(KeyEvent.KEYCODE_BUTTON_START);
            else this.eventHandler.onButtonUp(KeyEvent.KEYCODE_BUTTON_START);
        }
        this.isStart = newValue;

        // select button
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
     * @param touchEvents Collection of touch events
     * @param presenter Controller presenter used like mask to convert touch to buttons position
     */
    public void updateByTouchEvents(TouchEventsCollection touchEvents, ControllerPresenter presenter)
    {
        // update is input type button
        // this.updateTouchEventsByButtons(touchEvents, presenter);

        // update sticks stick position by on screen controller
        this.updateLeftStickPositionByTouch(touchEvents, presenter);
        this.updateRightStickPositionByTouch(touchEvents, presenter);
    }

    private void updateLeftStickPositionByTouch(TouchEventsCollection touchEvents, ControllerPresenter presenter)
    {
        // move player by touch events
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i); //.get(i);
            if (event == null) continue;

            // обработка паузы
            // if(Input.TouchEvent.TOUCH_DOWN != event.type) continue;

            if (Input.TouchEvent.TOUCH_DOWN == event.type && this._leftStickPointer == -1)
            {
                if (presenter.leftStickCircle.isPointInside(event.x, event.y))
                {
                    this._leftStickPointer = event.pointer;

                    this.leftStick.x = (event.x - presenter.leftStickCircle.center.x) / presenter.leftStickCircle.radius;
                    this.leftStick.y = (event.y - presenter.leftStickCircle.center.y) / presenter.leftStickCircle.radius;
                    break;
                }
            }

            if (event.pointer == this._leftStickPointer)
            {
                if (event.type != Input.TouchEvent.TOUCH_DOWN){
                    this._leftStickPointer = -1;
                    break;
                }

                this.leftStick.x = (event.x - presenter.leftStickCircle.center.x) / presenter.leftStickCircle.radius;
                if (this.leftStick.x > 1) this.leftStick.x = 1;
                if (this.leftStick.x < -1) this.leftStick.x = -1;

                this.leftStick.y = (event.y - presenter.leftStickCircle.center.y) / presenter.leftStickCircle.radius;
                if (this.leftStick.y > 1) this.leftStick.y = 1;
                if (this.leftStick.y < -1) this.leftStick.y = -1;

                break;

            }
        }
    }

    private void updateRightStickPositionByTouch(TouchEventsCollection touchEvents, ControllerPresenter presenter)
    {
        // move player by touch events
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i); //.get(i);
            if (event == null) continue;

            // additional button
            if (Input.TouchEvent.TOUCH_DOWN == event.type && presenter.rightStickAdditionalFireButton.isPointInside(event.x, event.y)) {
                this.isB = true;
            }

            //
            if (Input.TouchEvent.TOUCH_DOWN == event.type && this._rightStickPointer == -1)
            {
                if (presenter.rightStickCircle.isPointInside(event.x, event.y))
                {
                    this._rightStickPointer = event.pointer;

                    this.rightStick.x = (event.x - presenter.rightStickCircle.center.x) / presenter.rightStickCircle.radius;
                    this.rightStick.y = (event.y - presenter.rightStickCircle.center.y) / presenter.rightStickCircle.radius;
                    break;
                }
            }

            if (event.pointer == this._rightStickPointer)
            {
                if (event.type != Input.TouchEvent.TOUCH_DOWN) {
                    this._rightStickPointer = -1;
                    break;
                }

                this.rightStick.x = (event.x - presenter.rightStickCircle.center.x) / presenter.rightStickCircle.radius;
                if (this.rightStick.x > 1) this.rightStick.x = 1;
                if (this.rightStick.x < -1) this.rightStick.x = -1;

                this.rightStick.y = (event.y - presenter.rightStickCircle.center.y) / presenter.rightStickCircle.radius;
                if (this.rightStick.y > 1) this.rightStick.y = 1;
                if (this.rightStick.y < -1) this.rightStick.y = -1;

                break;
            }
        }
    }

    /**
     * Use presenter buttons as matrix of touch
     */
    private void updateTouchEventsByButtons (TouchEventsCollection touchEvents, ControllerPresenter presenter)
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
