package com.a530games.framework;

public class TouchEventsCollection {

    Input.TouchEvent[] events = new Input.TouchEvent[20];

    public Input.TouchEvent getEventByPointer(int pointer)
    {
        // if (pointer >= this.events.length) pointer = 0;
        return this.events[pointer];
    }

    public Input.TouchEvent get(int index) {
        return this.events[index];
    }

    public void set(int pointerId, Input.TouchEvent event) {
        this.events[pointerId] = event;
    }

    public int size() {
        return this.events.length;
    }

    public boolean hasDown()
    {
        for (int i = 0; i < this.events.length; i++)
        {
            if (this.events[i] == null) continue;
            if (this.events[i].type == Input.TouchEvent.TOUCH_DOWN) {
                return true;
            }
        }

        return false;
    }
}
