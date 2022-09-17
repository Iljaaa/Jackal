package com.a530games.framework;

import android.view.View;

import java.util.List;

public interface TouchHandler //  extends View.OnTouchListener
{
    boolean isTouchDown(int pointer);

    int getTouchX(int pointer);

    int getTouchY(int pinter);

    List<Input.TouchEvent> getTouchEvents();
}
