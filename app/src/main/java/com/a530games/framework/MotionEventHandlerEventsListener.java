package com.a530games.framework;

import android.view.KeyEvent;
import android.view.View;

interface MotionEventHandlerEventsListener
{
    void onKeyDown(View view, int keyCode, KeyEvent event);

    void onKeyUp(View view, int keyCode, KeyEvent event);

    void onSetLeftStickPosition(float x, float y);

    void onSetRightStickPosition(float x, float y);
}
