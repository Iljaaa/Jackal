package com.a530games.framework;

import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MultiTouchHandler implements TouchHandler , AndroidFastRenderViewTouchHandler
{
    boolean[] isTouched = new boolean[20];

    int[] touchX = new int[20];

    int[] touchY = new int[20];

    Pool<Input.TouchEvent> pool;

    List<Input.TouchEvent> touchEvents = new ArrayList<>();
    List<Input.TouchEvent> touchEventsBuffer = new ArrayList<>();

    float scaleX;
    float scaleY;

    public MultiTouchHandler(AndroidFastRenderView view, float scaleX, float scaleY)
    {
        PoolObjectFactory<Input.TouchEvent> factory = new PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };

        this.pool = new Pool<Input.TouchEvent>(factory,20);

        view.setTouchHandler(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }


    @Override
    public boolean onTouch(MotionEvent motionEvent)
    {
        int action = motionEvent.getAction();
        int eventIndex = motionEvent.getActionIndex();
        //int pointerIndex = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
        // int pointerId = motionEvent.getPointerId(pointerIndex);
        int pointerId = motionEvent.getPointerId(eventIndex);

        Input.TouchEvent touchEvent;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("touch", "down");
                touchEvent = this.pool.newObject();
                touchEvent.type = Input.TouchEvent.TOUCH_DOWN;
                touchEvent.pointer = pointerId;
                touchEvent.x = touchX[pointerId] = (int) (motionEvent.getX(eventIndex) * scaleX);
                touchEvent.y = touchY[pointerId] = (int) (motionEvent.getY(eventIndex) * scaleY);
                this.isTouched[pointerId] = true;
                this.touchEventsBuffer.add(touchEvent);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d("touch", "up");
                touchEvent = this.pool.newObject();
                touchEvent.type = Input.TouchEvent.TOUCH_UP;
                touchEvent.pointer = pointerId;
                touchEvent.x = touchX[pointerId] = (int) (motionEvent.getX(eventIndex) * scaleX);
                touchEvent.y = touchY[pointerId] = (int) (motionEvent.getY(eventIndex) * scaleY);
                this.isTouched[pointerId] = false;
                this.touchEventsBuffer.add(touchEvent);

                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("move", "move");
                int pointerCount = motionEvent.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    eventIndex = i;
                    pointerId = motionEvent.getPointerId(eventIndex);
                    touchEvent = this.pool.newObject();
                    touchEvent.type = Input.TouchEvent.TOUCH_DRAGGED;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[pointerId] = (int) (motionEvent.getX(eventIndex) * scaleX);
                    touchEvent.y = touchY[pointerId] = (int) (motionEvent.getY(eventIndex) * scaleY);
                    this.touchEventsBuffer.add(touchEvent);
                }
                break;
        }
        return false;
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= 20)
                return false;
            else
                return this.isTouched[pointer];
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= 20)
                return 0;
            else
                return this.touchX[pointer];
        }
    }
    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= 20)
                return 0;
            else
                return this.touchY[pointer];
        }
    }

    @Override
    public List<Input.TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++)this.pool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }
}
