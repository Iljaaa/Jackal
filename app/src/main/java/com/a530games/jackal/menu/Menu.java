package com.a530games.jackal.menu;

import com.a530games.framework.Graphics;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.jackal.Controller;

public interface Menu {

    void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime);

    void present(Graphics graphics);

    /**
     * Append menu item
     */
    void addItem(MenuItem item);
}
