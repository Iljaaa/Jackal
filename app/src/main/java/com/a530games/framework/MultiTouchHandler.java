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

    TouchEventsCollection eventsCollection;

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

        this.eventsCollection = new TouchEventsCollection();

        this.pool = new Pool<Input.TouchEvent>(factory,20);

        view.setTouchHandler(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }


    @Override
    public boolean onTouch(MotionEvent motionEvent)
    {
        int action = motionEvent.getActionMasked(); // motionEvent.getAction();
        // Log.d("touch->action", String.valueOf(action));

        // int actionIndex = motionEvent.getActionIndex();
        // Log.d("touch->action index", String.valueOf(actionIndex));

        //int pointerIndex = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
        // int pointerId = motionEvent.getPointerId(pointerIndex);

        // int pointerId = motionEvent.getPointerId(actionIndex);
        // Log.d("touch->pointerId", String.valueOf(pointerId));

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                this.processDown(motionEvent);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                this.processUp(motionEvent);
                break;
            case MotionEvent.ACTION_MOVE:
                this.processMove(motionEvent);
                break;
        }
        return false;
    }

    private void processDown(MotionEvent motionEvent)
    {
        // Log.d("touch", "down");

        /*int actionIndex = motionEvent.getActionIndex();
        Log.d("processDown->aid", String.valueOf(actionIndex));*/


        int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
        // int pointerId = motionEvent.getPointerId(actionIndex);
        // Log.d("processDown->pid", String.valueOf(pointerId));

        Input.TouchEvent event = this.eventsCollection.getEventByPointer(pointerId);

        if (event == null){
            event = new Input.TouchEvent();
            this.eventsCollection.set(pointerId, event);
        }

        try {
            event.type = Input.TouchEvent.TOUCH_DOWN;
            event.pointer = pointerId;
            event.x = (int) Math.ceil(motionEvent.getX(pointerId) * scaleX);
            event.y = (int) Math.ceil(motionEvent.getY(pointerId) * scaleY);
        } catch (IllegalArgumentException ex) {
            Log.e("MultiTouchHandler", "IllegalArgumentException for pointer" +String.valueOf(pointerId));
        }

        /*this.events[pointerId].type = Input.TouchEvent.TOUCH_DOWN;
        this.events[pointerId].pointer = pointerId;
        this.events[pointerId].x = (int) Math.ceil(motionEvent.getX(pointerId));
        this.events[pointerId].y = (int) Math.ceil(motionEvent.getY(pointerId));*/


        /*touchEvent = this.pool.newObject();
        touchEvent.type = Input.TouchEvent.TOUCH_DOWN;
        touchEvent.pointer = pointerId;
        touchEvent.x = touchX[pointerId] = (int) (motionEvent.getX(pointerId) * scaleX);
        touchEvent.y = touchY[pointerId] = (int) (motionEvent.getY(pointerId) * scaleY);
        this.isTouched[pointerId] = true;
        this.touchEventsBuffer.add(touchEvent);*/
    }

    private void processUp (MotionEvent motionEvent)
    {
        // Log.d("touch", "up");

        /*int actionIndex = motionEvent.getActionIndex();
        Log.d("processDown->aid", String.valueOf(actionIndex));

        int pointerId = ;
        Log.d("processDown->pid", String.valueOf(pointerId));

        Input.TouchEvent event = this.eventsCollection.getEventByPointer(pointerId);*/
        Input.TouchEvent event = this.eventsCollection.getEventByPointer(motionEvent.getPointerId( motionEvent.getActionIndex()));
        if (event == null) return;

        event.type = Input.TouchEvent.TOUCH_UP;
        // event.pointer = pointerId;
        // event.x = 0; // (int) Math.ceil(motionEvent.getX(pointerId) * scaleX);
        // event.y = 0; // (int) Math.ceil(motionEvent.getY(pointerId) * scaleY);

        /*touchEvent = this.pool.newObject();
        touchEvent.type = Input.TouchEvent.TOUCH_UP;
        touchEvent.pointer = pointerId;
        touchEvent.x = touchX[pointerId] = (int) (motionEvent.getX(pointerId) * scaleX);
        touchEvent.y = touchY[pointerId] = (int) (motionEvent.getY(pointerId) * scaleY);
        this.isTouched[pointerId] = false;
        this.touchEventsBuffer.add(touchEvent);*/
    }

    private void processMove(MotionEvent motionEvent)
    {
        // Log.d("touch", "move");

        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = motionEvent.getPointerId(i);
            Input.TouchEvent event = this.eventsCollection.getEventByPointer(pointerId);

            try {
                // event.type = Input.TouchEvent.TOUCH_DOWN;
                // event.pointer = pointerId;
                event.x = (int) Math.ceil(motionEvent.getX(pointerId) * scaleX);
                event.y = (int) Math.ceil(motionEvent.getY(pointerId) * scaleY);
            } catch (IllegalArgumentException ex) {
                Log.e("MultiTouchHandler", "IllegalArgumentException for pointer" +String.valueOf(pointerId));
            }

            /*touchEvent = this.pool.newObject();
            touchEvent.type = Input.TouchEvent.TOUCH_DRAGGED;
            touchEvent.pointer = pointerId;
            touchEvent.x = touchX[pointerId] = (int) (motionEvent.getX(pointerId) * scaleX);
            touchEvent.y = touchY[pointerId] = (int) (motionEvent.getY(pointerId) * scaleY);
            this.touchEventsBuffer.add(touchEvent);*/
        }
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
    public TouchEventsCollection getTouchEvents()
    {
        return this.eventsCollection;
        /*synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++)this.pool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }*/
    }
}
