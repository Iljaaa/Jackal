package com.a530games.jackal.menu;

import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.Input;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.math.Vector2;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Controller;
import com.a530games.jackal.Sprite;

import java.util.ArrayList;

public abstract class BasicMenu implements Menu
{

    /**
     * Menu items
     */
    protected ArrayList<MenuItem> items;

    /**
     * Position of menu
     */
    protected Vector2 position;

    /**
     * Pointer image
     */
    private final Sprite pointer;

    /**
     * Current active menu item
     */
    protected int activeIndex = 0;

    /**
     * User select index
     */
    protected int selectedIndex = -1;

    /**
     * Delay before rise select event
     */
    private float beforeSelectCallbackEventTimer = 0;

    /**
     * Select event handler
     */
    private MenuEventHandler eventHandler = null;

    /**
     * Next item select delay
     */
    float flipDelay = 0;

    public BasicMenu(int x, int y)
    {
        //
        this.position = new Vector2(x, y);

        this.items = new ArrayList<>();

        this.pointer = new Sprite(Assets.player);
        this.pointer.set(2, 1);
    }

    public void setEventHandler (MenuEventHandler menuEventHandler){
        this.eventHandler = menuEventHandler;
    }


    @Override
    public void addItem(MenuItem item)
    {
        // set on menu item pointer sprite
        item.setPointerSprite(this.pointer);

        item.setPosition(
            this.position.x,
            this.position.y + this.items.size() * MenuItem.HEIGHT
        );

        this.items.add(item);
    }

    @Override
    public void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime)
    {
        // if user select item and it blink
        if (this.beforeSelectCallbackEventTimer > 0)
        {
            this.beforeSelectCallbackEventTimer -= deltaTime;

            // if summary blink time ends
            if (this.beforeSelectCallbackEventTimer < 0) {
                this.clearSelectAndRiseEvent();
                return;
            }
        }

        // process user input
        if (this.selectedIndex < 0)
        {
            // update menu select flip by controller
            this.updateFlip(controller, deltaTime);

            //
            this.updateControls(controller, touchEvents);
        }

        // update items
        int count = this.items.size();
        for (int i = 0; i < count; i++) {
            MenuItem it = this.items.get(i);
            it.update(deltaTime, (this.selectedIndex == i));
        }

    }

    /**
     * update user input
     */
    private void updateControls (Controller controller, TouchEventsCollection touchEvents)
    {
        // update select by touch
        // move player by touch events
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i); //.get(i);
            if (event == null) continue;

            // if (Input.TouchEvent.TOUCH_DOWN == event.type)
            if (Input.TouchEvent.TOUCH_UP == event.type)
            {
                // draw points
                int count = this.items.size();
                for (int menuIndex = 0; menuIndex < count; menuIndex++) {
                    MenuItem it = this.items.get(menuIndex);
                    if (it.isPointInside(event.x, event.y)) {
                        this.onSelectItem(menuIndex);
                    }
                }
            }
        }

        // check controller events
        if (controller.isA() || controller.isStart()) {
            this.onSelectItem(this.activeIndex);
        }
    }

    /**
     * Update menu flip by controller axis
     */
    private void updateFlip (Controller controller, float deltaTime)
    {
        // move item
        this.flipDelay -= deltaTime;
        if (flipDelay > 0) return;

        Vector2F leftStick = controller.getLeftStickDirection();
        if (leftStick.y != 0)
        {
            if (leftStick.y > 0) {
                this.activeIndex++;
                if (this.activeIndex >= this.items.size()) this.activeIndex = 0;
                this.flipDelay = 0.2f;
            }

            if (leftStick.y < 0) {
                this.activeIndex--;
                if (this.activeIndex < 0) this.activeIndex = this.items.size() - 1;
                this.flipDelay = 0.2f;
            }
        }
    }

    /**
     * On all animated finished
     */
    private void clearSelectAndRiseEvent ()
    {
        // this.blink = false;
        Log.d("GameOverLoseMenu", String.format("Item selected %d", this.activeIndex));

        if (this.eventHandler != null) {
            this.eventHandler.onMenuItemSelect(this.items.get(this.selectedIndex).getCode());
        }

        // drop selected ints
        this.selectedIndex = -1;
    }

    @Override
    public void present(Graphics g)
    {
        // draw points
        int count = this.items.size();
        for (int i = 0; i < count; i++)
        {
            MenuItem it = this.items.get(i);

            it.present(
                    g,
                    (this.activeIndex == i) // , // is active
                    // (this.activeIndex == this.selectedIndex)
            );

        }
    }

    /**
     * Start select menu item
     * @param index index of select item
     */
    private void onSelectItem(int index)
    {
        if (this.selectedIndex > -1) {
            return;
        }

        this.beforeSelectCallbackEventTimer = 2;

        this.selectedIndex = index;

        // set as active menu item
        this.activeIndex = index;
    }

    /**
     * Calculated menu height
     */
    public int getMenuHeight() {
        return this.items.size() * MenuItem.HEIGHT;
    }

    /**
     * Menu width
     * is width of item elements
     */
    public int getMenuWidth() {
        return this.items.get(0).getWidth();
    }
}
