package com.a530games.framework;

import android.view.View;

import java.util.List;

public interface TouchHandler extends View.OnTouchListener
{
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pinter);

    public List<Input.TouchEvent> getTouchEvents();
}
