package com.a530games.jackal.menu;

import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Controller;
import com.a530games.jackal.Sprite;

import java.util.ArrayList;

public abstract class BasicMenu implements Menu
{
    /**
     *
     */
    protected final int buttonWidth = 400;

    protected final int buttonHeight = 50;

    protected final int buttonMargin = 10;

    protected ArrayList<MenuItem> items;

    /**
     * Position of menu
     */
    protected Vector2 position;

    /**
     * Pointer image
     */
    private Sprite pointer;

    /**
     * Current active menu item
     */
    protected int activeIndex = 1;

    /**
     * User select index
     */
    protected int selectedIndex = -1;

    /**
     * Delay before rise select event
     */
    private float blinkFullTimer = 0;

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


    @Override
    public void addItem(MenuItem item)
    {
        item.setPointerSprite(this.pointer);

        this.items.add(item);
    }

    @Override
    public void update(Controller controller, TouchEventsCollection touchEvents, float deltaTime)
    {
        // if user select item and it blink
        if (this.blinkFullTimer > 0)
        {
            this.blinkFullTimer -= deltaTime;

            // if summary blink time is finish
            if (this.blinkFullTimer < 0)
            {
                // this.blink = false;
                Log.d("GameOverLoseMenu", "Item selected " + String.valueOf(this.activeIndex));

                // drop selected ints
                this.selectedIndex = - 1;

                return;
            }
        }

        // if controller push button
        if (controller.isA()) {
            this.selectItem();
        }

        // move item
        this.flipDelay -= deltaTime;
        if (flipDelay > 0) return;

        Vector2 leftStick = controller.getLeftStickDirection();
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

        // update items
        int count = this.items.size();
        for (int i = 0; i < count; i++) {
            MenuItem it = this.items.get(i);
            it.update(deltaTime, (this.selectedIndex == i));
        }

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
                    (this.activeIndex == i), // is active
                    false);

        }
    }


    private void selectItem()
    {
        this.blinkFullTimer = 2;

        this.selectedIndex = this.activeIndex;

        // this.blinkDelay = this.blinkDefaultDelay;
        // this.blink = true;
    }
}
