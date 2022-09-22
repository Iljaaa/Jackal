package com.a530games.framework;

import android.content.Context;
import android.view.InputDevice;

import java.util.List;

public class AndroidInput implements Input
{
    private KeyboardHandler keyHandler;

    private TouchHandler touchHandler;

    private MotionEventHandler motionEventsHandler;

    /**
     * Controller input handler
     */
    private ControllerHandler controller;

    /**
     *
     * @param context content
     * @param view view
     * @param scaleX Screen scale by X axe
     * @param scaleY Screen scale by Y axe
     */
    public AndroidInput(Context context, AndroidFastRenderView view, float scaleX, float scaleY)
    {
        this.keyHandler = new KeyboardHandler(view);

        this.touchHandler = new MultiTouchHandler(view, scaleX, scaleY);

        this.motionEventsHandler = new AndroidMotionEventHandler(view);

        // https://developer.android.com/training/game-controllers/controller-input
        this.controller = new AndroidControllerInput(this.motionEventsHandler);

        // bind key listener
        // view.setOnKeyListener(this.controller);


        // тут мы определяем подключен ли джлйстик
        // todo: здесь надо дописать сохранения ид девайса что бы сделать несколько джойстиков
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            int sources = dev.getSources();

            if (((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)
                    || ((sources & InputDevice.SOURCE_JOYSTICK)
                    == InputDevice.SOURCE_JOYSTICK)) {
                // This device is a game controller. Store its device ID.
                /*if (!gameControllerDeviceIds.contains(deviceId)) {xzdcweaqdc
                    gameControllerDeviceIds.add(deviceId);
                }*/
                int a = 3;
            }
        }
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return this.keyHandler.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return this.touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return this.touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return this.touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return 0;
    }

    @Override
    public float getAccelY() {
        return 0;
    }

    @Override
    public float getAccelZ() {
        return 0;
    }

    @Override
    public ControllerHandler getController() {
        return this.controller;
    }

    @Override
    public List<KeyEvent> getKeyEvents() {
        if (this.keyHandler == null) return null;
        return this.keyHandler.getKeyEvents();
    }

    @Override
    public TouchEventsCollection getTouchEvents() {
        return this.touchHandler.getTouchEvents();
    }
}
