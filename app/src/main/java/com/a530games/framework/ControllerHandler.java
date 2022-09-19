package com.a530games.framework;

import com.a530games.framework.math.Vector2;

/**
 * Interface for input from joypad controller
 * https://developer.android.com/training/game-controllers/controller-input
 */
public interface ControllerHandler
{
    /**
     * Is A button down
     */
    boolean isA();

    /**
     * Is B button down
     */
    boolean isB();

    /**
     * Is X button down
     */
    boolean isX();

    /**
     * Is Y button down
     */
    boolean isY();

    /**
     * Is R1 button down
     */
    boolean isR1();

    /**
     * Is L1 button down
     */
    boolean isL1();

    /**
     * Is R2 button down
     */
    boolean isR2();

    /**
     * Is L2 button down
     */
    boolean isL2();

    /**
     * Is start button down
     */
    boolean isStart();

    /**
     * Is Select button down
     */
    boolean isSelect();

    /**
     *
     * @return Normal vector of left stick position
     */
    Vector2 getLeftStickDirection();

    /**
     * Normal vector of right stick position
     */
    Vector2 getRightStickDirection();


}
