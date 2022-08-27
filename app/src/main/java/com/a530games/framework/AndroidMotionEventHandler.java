package com.a530games.framework;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Motion event handler for joypad
 */
public class AndroidMotionEventHandler implements  MotionEventHandler, View.OnGenericMotionListener, View.OnKeyListener // implements todo: interface
{
    private float motionStickX = 0, motionStickY = 0;

    private MotionEventHandlerEventsListener listener = null;

    /**
     * Action on key press event function
     */
    private int _keyPressAction = 0;

    public AndroidMotionEventHandler(View view)
    {
        view.setOnGenericMotionListener(this);
        view.setOnKeyListener(this);
    }

    /*public boolean onGenericMotionEvent(MotionEvent event)
    {
        //if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK && event.getAction() == MotionEvent.ACTION_MOVE) {

        //}
    }*/

    @Override
    public void setListener (MotionEventHandlerEventsListener listener)
    {
        this.listener = listener;
    }

    @Override
    public boolean onGenericMotion(View view, MotionEvent motionEvent)
    {

        //if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK && event.getAction() == MotionEvent.ACTION_MOVE) {

        //}

        //otionEvent.AXIS_HAT_X, historyPos);
        //        // if (x == 0) x = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_Z, historyPos);
        //
        //        float x = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_Z, historyPos);
        //
        //        // Calculate the vertical distance to move by
        //        // using the input value from one of these physical controls:
        //        // the left control stick, hat switch, or the right control stick.
        //
        //        // float y = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_Y, historyPos);
        //        // if (y == 0) y = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_HAT_Y, historyPos);
        //        // if (y == 0) y = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_RZ,

        int action = motionEvent.getAction();
        // Log.d("action", String.valueOf(action));

        float up = motionEvent.getAxisValue(KeyEvent.KEYCODE_DPAD_UP); //, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT
        // Log.d("up", String.valueOf(up));

        float leftStickX = motionEvent.getAxisValue(MotionEvent.AXIS_X);
        float leftStickY = motionEvent.getAxisValue(MotionEvent.AXIS_Y);
        // Log.d("left stick", "x: "+String.valueOf(leftStickX)+" y: "+String.valueOf(leftStickY));


        float lx = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
        float ly = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);
        // Log.d("left cross", "x: "+String.valueOf(lx)+" y: "+String.valueOf(ly));

        if (lx != 0 || ly != 0) {
            leftStickX = lx;
            leftStickY = ly;
        }
        this.listener.onSetLeftStickPosition(leftStickX, leftStickY);

        this.motionStickX = motionEvent.getAxisValue(MotionEvent.AXIS_Z);
        this.motionStickY = motionEvent.getAxisValue(MotionEvent.AXIS_RZ);
        // Log.d("right stick", "x: "+String.valueOf(this.motionStickX)+" y: "+String.valueOf(this.motionStickY));
        this.listener.onSetRightStickPosition(this.motionStickX, this.motionStickY);

        // Process all historical movement samples in the batch
        /*final int historySize = motionEvent.getHistorySize();

        // Process the movements starting from the
        // earliest historical position in the batch
        for (int i = 0; i < historySize; i++) {
            // Process the event at historical position i
            processJoystickInput(motionEvent, i);
        }

        // Process the current movement sample in the batch (position -1)
        processJoystickInput(motionEvent, -1);*/

        return true; //super.onGenericMotion(view, event);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event)
    {
        this._keyPressAction = event.getAction();

        if (this._keyPressAction ==  KeyEvent.ACTION_DOWN) {
            this.listener.onKeyDown(view, keyCode, event);
        }

        if (this._keyPressAction ==  KeyEvent.ACTION_UP) {
            this.listener.onKeyUp(view, keyCode, event);
        }

        return true;
    }

    /**
     * Process right axe
     * @param event
     * @param historyPos

    private void processJoystickInput(MotionEvent event, int historyPos)
    {
        InputDevice inputDevice = event.getDevice();

        // Calculate the horizontal distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat axis, or the right control stick.

        // float x = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_X, historyPos);
        // if (x == 0) x = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_HAT_X, historyPos);
        // if (x == 0) x = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_Z, historyPos);

        float x = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_Z, historyPos);

        // Calculate the vertical distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat switch, or the right control stick.

        // float y = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_Y, historyPos);
        // if (y == 0) y = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_HAT_Y, historyPos);
        // if (y == 0) y = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_RZ, historyPos);
        
        float y = getCenteredAxis(event, inputDevice, MotionEvent.AXIS_RZ, historyPos);

        // Update the ship object based on the new x and y values
        Log.d("Axis", "x: "+String.valueOf(x)+" y: "+String.valueOf(y));

    }*/

    /*
    private static float getCenteredAxis(MotionEvent event, InputDevice device, int axis, int historyPos)
    {
        final InputDevice.MotionRange range = device.getMotionRange(axis, event.getSource());

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        if (range != null) {
            final float flat = range.getFlat();
            final float value = historyPos < 0
                    ? event.getAxisValue(axis)
                    : event.getHistoricalAxisValue(axis, historyPos);

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value;
            }
        }

        return 0;
    }*/
}
