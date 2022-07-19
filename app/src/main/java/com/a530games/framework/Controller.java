package com.a530games.framework;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

/**
 * Абстракцтя вокруг джойпада
 */
public class Controller implements View.OnKeyListener
{
    boolean isTop = false;
    boolean isRight = false;
    boolean isDown = false;
    boolean isLeft = false;

    boolean isStart = false;

    boolean isA = false;
    boolean isB = false;
    boolean isX = false;
    boolean isY = false;

    private int _action = 0;
    private int _keyCode = 0;

    public boolean isTopButtonDown () {
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
    }

    public boolean isStart() {
        return isStart;
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

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent)
    {
        this._action = keyEvent.getAction();
        this._keyCode = keyEvent.getKeyCode();

        // if ((event.getSource() & InputDevice.SOURCE_GAMEPAD)
        Log.d("Controller", "onKey");
        Log.d("Controller", String.valueOf(keyEvent.getSource()));
        Log.d("Controller", String.valueOf(keyEvent.getAction()));
        Log.d("Controller", String.valueOf(keyEvent.getKeyCode()));

        if (this._action ==  KeyEvent.ACTION_DOWN)
        {
            // спуциальная обработка кнопки b

            if (this._keyCode == KeyEvent.KEYCODE_DPAD_UP) this.isTop = true;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) this.isRight = true;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_DOWN) this.isDown = true;
            if (this._keyCode == KeyEvent.KEYCODE_DPAD_LEFT) this.isLeft = true;
            // if (this._keyCode == KeyEvent.KEYCODE_BUTTON_A) this.isDown = true;

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
            // if (this._keyCode == KeyEvent.KEYCODE_BUTTON_A) this.isDown = false;

            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_START) this.isStart = false;

            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_A) this.isA = false;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_B) this.isB = false;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_X) this.isX = false;
            if (this._keyCode == KeyEvent.KEYCODE_BUTTON_Y) this.isY = false;
        }

        return false;
    }
}