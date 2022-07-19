package com.a530games.framework;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHandler implements View.OnKeyListener
{
    boolean[] pressedKeys = new boolean[128];
    Pool<Input.KeyEvent> pool;
    List<Input.KeyEvent> keyEventBuffer = new ArrayList<Input.KeyEvent>();
    List<Input.KeyEvent> keyEvents = new ArrayList<Input.KeyEvent>();

    public KeyboardHandler(View view)
    {
        PoolObjectFactory<Input.KeyEvent> factory = new PoolObjectFactory() {
            @Override
            public Input.KeyEvent createObject (){
                return new Input.KeyEvent();
            }
        };

        this.pool = new Pool<Input.KeyEvent>(factory, 100);

        view.setOnKeyListener(this);
        // view.setOnKeyListener(this);
        // view.setFocusableInTouchMode(true);
        view.setFocusable(true);
        view.requestFocus();
    }



    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event)
    {
        int action = event.getAction();
        Log.d("action", String.valueOf(action));
        Log.d("is long press", String.valueOf(event.isLongPress()));
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE) return false;

        // какая то ебана хуйня почему сразу два события приходит нажатия и отпускания
        synchronized (this)
        {

            Input.KeyEvent myEvent = this.pool.newObject();
            myEvent.keyCode = keyCode;
            myEvent.keyChar = (char) event.getUnicodeChar();
            if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
                Log.d("action2", "down");
                myEvent.type = Input.KeyEvent.KEY_UP;
                if(keyCode > 0 && keyCode < 127) pressedKeys[keyCode] = false;
            }
            if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                Log.d("action2", "up");
                myEvent.type = Input.KeyEvent.KEY_DOWN;
                if(keyCode > 0 && keyCode < 127) pressedKeys[keyCode] = true;
            }

            // this.keyEventBuffer.
            this.keyEventBuffer.add(myEvent);
        }

        return false;
    }

    /*@Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int i, int i1, KeyEvent keyEvent) {
        return false;
    }*/

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode > 127)return false;
        return this.pressedKeys[keyCode];
    }

    public List<Input.KeyEvent> getKeyEvents()
    {
        synchronized (this)
        {
            int len = this.keyEvents.size();
            for (int i = 0; i < len; i++) this.pool.free(keyEvents.get(i));
            this.keyEvents.clear();
            this.keyEvents.addAll(this.keyEventBuffer);
            this.keyEventBuffer.clear();

        }
        return this.keyEvents;
    }
}
