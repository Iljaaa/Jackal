package com.a530games.framework;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.a530games.framework.math.Vector2;

/**
 * Controller input for android
 */
public class AndroidControllerInput implements View.OnKeyListener, MotionEventHandlerEventsListener, ControllerHandler
{
    boolean isTop = false;
    boolean isRight = false;
    boolean isDown = false;
    boolean isLeft = false;

    // start, select
    boolean isStart = false, isSelect = false;

    boolean isA = false;
    boolean isB = false;
    boolean isX = false;
    boolean isY = false;

    private boolean isR1 = false;
    private boolean isL1 = false;

    private boolean isR2 = false, isL2 = false;

    MotionEventHandler motionEventHandler;

    // left stick potion
    private final Vector2 leftStick;

    // left stick potion
    private final Vector2 rightStick;

    private int _action = 0;
    private int _keyCode = 0;

    public AndroidControllerInput(MotionEventHandler motionEventHandler)
    {
        this.leftStick = new Vector2();
        this.rightStick = new Vector2();

        this.motionEventHandler = motionEventHandler;
        this.motionEventHandler.setListener(this);
    }

    /*public boolean isTopButtonDown () {
        return this.isTop;
    }

    public boolean isRightButtonDown () {
        return this.isRight;
    }

    public boolean isBottomButtonDown () {
        return this.isDown;
    }

    public boolean isLeftButtonDown () {
        return this.isLeft;
    }*/

    @Override
    public boolean isStart() {
        return this.isStart;
    }

    @Override
    public boolean isSelect() {
        return this.isSelect;
    }

    @Override
    public Vector2 getLeftStickDirection() {
        return this.leftStick;
    }
    @Override
    public Vector2 getRightStickDirection() {
        return this.rightStick;
    }

    @Override
    public boolean isA() {
        return isA;
    }

    @Override
    public boolean isB() {
        return isB;
    }

    @Override
    public boolean isX() {
        return isX;
    }

    @Override
    public boolean isY() {
        return isY;
    }

    @Override
    public boolean isR1() {
        return isR1;
    }

    @Override
    public boolean isL1() {
        return isL1;
    }

    @Override
    public boolean isR2() {
        return isR2;
    }

    @Override
    public boolean isL2() {
        return isL2;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent)
    {
        this._action = keyEvent.getAction();
        this._keyCode = keyEvent.getKeyCode();

        // if ((event.getSource() & InputDevice.SOURCE_GAMEPAD)
        Log.d("Controller", "onKey");
        Log.d("Controller", String.valueOf(keyEvent.getSource()));
        Log.d("Controller", String.valueOf(keyEvent.getAction()));
        // Log.d("Controller", "keycode: " + String.valueOf(this._keyCode));

        if (this._action ==  KeyEvent.ACTION_DOWN)
        {
            // спуциальная обработка кнопки b

            if (this._keyCode == KeyEvent.KEYCODE_DPAD_UP) this.isTop = true;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) this.isRight = true;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_DOWN) this.isDown = true;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_LEFT) this.isLeft = true;

            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_START) this.isStart = true;

            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_A) this.isA = true;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_B) this.isB = true;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_X) this.isX = true;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_Y) this.isY = true;
        }

        if (this._action ==  KeyEvent.ACTION_UP)
        {
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_UP) this.isTop = false;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) this.isRight = false;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_DOWN) this.isDown = false;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_LEFT) this.isLeft = false;

            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_START) this.isStart = false;

            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_A) this.isA = false;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_B) this.isB = false;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_X) this.isX = false;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_Y) this.isY = false;
        }

        return false;
    }

    @Override
    public void onKeyDown(View view, int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) this.isTop = true;
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) this.isRight = true;
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) this.isDown = true;
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) this.isLeft = true;

        if (keyCode == KeyEvent.KEYCODE_BUTTON_START) this.isStart = true;

        if (keyCode == KeyEvent.KEYCODE_BUTTON_A) this.isA = true;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_B) this.isB = true;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_X) this.isX = true;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_Y) this.isY = true;

        if (keyCode == KeyEvent.KEYCODE_BUTTON_R1) this.isR1 = true;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_L1) this.isL1 = true;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_R2) this.isR2 = true;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_L2) this.isL2 = true;
    }

    @Override
    public void onKeyUp(View view, int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) this.isTop = false;
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) this.isRight = false;
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) this.isDown = false;
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) this.isLeft = false;

        if (keyCode == KeyEvent.KEYCODE_BUTTON_START) this.isStart = false;

        if (keyCode == KeyEvent.KEYCODE_BUTTON_A) this.isA = false;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_B) this.isB = false;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_X) this.isX = false;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_Y) this.isY = false;

        if (keyCode == KeyEvent.KEYCODE_BUTTON_R1) this.isR1 = false;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_L1) this.isL1 = false;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_R2) this.isR2 = false;
        if (keyCode == KeyEvent.KEYCODE_BUTTON_L2) this.isL2 = false;

    }

    @Override
    public void onSetLeftStickPosition(float x, float y)
    {
        this.leftStick.x = (float) (Math.round(x * 100.0) / 100.0);
        this.leftStick.y = (float) (Math.round(y * 100.0) / 100.0);
    }

    @Override
    public void onSetRightStickPosition(float x, float y)
    {
        this.rightStick.x = (float) (Math.round(x * 100.0) / 100.0);
        this.rightStick.y = (float) (Math.round(y * 100.0) / 100.0);
    }
}
