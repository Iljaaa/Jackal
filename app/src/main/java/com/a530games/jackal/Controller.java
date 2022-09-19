package com.a530games.jackal;

import com.a530games.framework.ControllerHandler;
import com.a530games.framework.math.Vector2;

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
        return isA;
    }

    public boolean isB() {
        return isB;
    }

    public boolean isX() {
        return isX;
    }

    public boolean isY() {
        return isY;
    }

    public boolean isR1() {
        return isR1;
    }

    public boolean isL1() {
        return isL1;
    }

    public boolean isR2() {
        return isR2;
    }

    public boolean isL2() {
        return isL2;
    }
}
