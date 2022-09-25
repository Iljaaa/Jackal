package com.a530games.jackal.menu;

import android.graphics.Color;
import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Controller;
import com.a530games.jackal.Sprite;

public class GameOverLoseMenu extends Menu
{
    enum MenuState {

    }

    private Vector2 position;

    private Sprite pointer;

    float flipDelay = 0;

    private boolean blink = false;

    private final float blinkDefaultDelay = 0.05f;

    private float blinkFullTimer = 0;

    private float blinkDelay = 0;

    public GameOverLoseMenu(int x, int y)
    {
        super();

        this.position = new Vector2(x, y);

        this.pointer = new Sprite(Assets.player);
        this.pointer.set(2, 1);

        this.items.add("RESTART");
        this.items.add("OTHER RESTART");
        this.items.add("OTHER RESTART 1");
        this.items.add("OTHER RESTART 2");
        this.items.add("OTHER RESTART 3");
    }

    @Override
    public void update(Controller controller, float deltaTime)
    {
        //
        if (this.blinkFullTimer > 0)
        {
            this.blinkFullTimer -= deltaTime;

            // if summary blink time is finish
            if (this.blinkFullTimer < 0)
            {
                this.blink = false;
                Log.d("GameOverLoseMenu", "Item selected " + String.valueOf(this.selectedIndex));
                return;
            }

            this.blinkDelay -= deltaTime;
            if (this.blinkDelay <= 0){
                this.blinkDelay = this.blinkDefaultDelay;
                this.blink = !this.blink;
            }

            return;
        }

        //
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
                this.selectedIndex++;
                if (this.selectedIndex >= this.items.size()) this.selectedIndex = 0;
                this.flipDelay = 0.2f;
            }

            if (leftStick.y < 0) {
                this.selectedIndex--;
                if (this.selectedIndex < 0) this.selectedIndex = this.items.size() - 1;
                this.flipDelay = 0.2f;
            }
        }
    }

    @Override
    public void present(Graphics g)
    {
        // draw pointer
        // todo: fix magic numbers

        int pointerTop = (int) Math.ceil(this.position.y)
                + (this.selectedIndex * (this.buttonHeight + this.buttonMargin));

        g.drawPixmap (
            this.pointer.image,
            (int) Math.ceil(this.position.x) + 10,
            pointerTop - 16,
                this.pointer.getLeft(),
                this.pointer.getTop(),
                this.pointer.width,
                this.pointer.height
        );

        // draw points
        int count = this.items.size();
        for (int i = 0; i < count; i++)
        {
            String it = this.items.get(i);

            int buttonTop = (int) Math.ceil(this.position.y)
                    + (this.buttonHeight + this.buttonMargin) * i;


            if (this.selectedIndex == i)
            {
                // wrap
                g.drawRect(
                        (int) Math.ceil(this.position.x) + 90,
                        buttonTop,
                        this.buttonWidth - 90,
                        this.buttonHeight,
                        this.rectPaint
                );

                if (this.blink){
                    g.drawText(
                            it,
                            (int) Math.ceil(this.position.x) + 100,
                            buttonTop + 35,
                            this.blinkFont
                    );
                }
                else {

                    g.drawText(
                            it,
                            (int) Math.ceil(this.position.x) + 100,
                            buttonTop + 35,
                            this.font
                    );
                }

            }
            else {
                g.drawText(
                        it,
                        (int) Math.ceil(this.position.x) + 100,
                        buttonTop + 35,
                        this.font
                );
            }

        }


    }

    private void selectItem() {
        this.blinkFullTimer = 2;
        this.blinkDelay = this.blinkDefaultDelay;
        this.blink = true;
    }

    private void presentString() {

    }
}
