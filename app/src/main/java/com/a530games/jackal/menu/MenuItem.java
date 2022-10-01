package com.a530games.jackal.menu;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.jackal.Sprite;

public class MenuItem
{
    /**
     * Text font size
     */
    private final int fontSize = 30;

    /**
     * Text on button
     */
    private final String text;

    /**
     * String code
     */
    private final String code;

    /**
     * Wrap rect for touch select
     */
    private final Rect wrap;

    /**
     * Pinter image
     */
    private Sprite pointerSprite;

    private boolean blink = false;

    private float blinkDelay = 0;

    private final Paint font;
    private final Paint blinkFont;
    private final Paint rectPaint;

    public MenuItem(String text, String code, int left, int top, int right, int bottom)
    {
        this.text = text;
        this.code = code;

        this.wrap = new Rect(left, top, right, bottom);

        this.font = new Paint();
        // this.font.setStyle(Paint.Style.FILL_AND_STROKE);
        // this.font.setStrokeWidth(2);
        this.font.setColor(Color.WHITE);
        this.font.setTextSize(this.fontSize);

        this.blinkFont = new Paint();
        this.blinkFont.setColor(Color.RED);
        this.blinkFont.setTextSize(this.fontSize);

        this.rectPaint = new Paint();
        this.rectPaint.setColor(Color.RED);
        this.rectPaint.setStyle(Paint.Style.STROKE);
        this.rectPaint.setStrokeWidth(2);
    }

    public String getCode () {
        return this.code;
    }

    /**
     * Set image of pointer
     */
    public void setPointerSprite(Sprite pointerSprite) {
        this.pointerSprite = pointerSprite;
    }

    public void update (float deltaTime, boolean isSelect)
    {
        if (isSelect) {
            this.blinkDelay -= deltaTime;
            if (this.blinkDelay <= 0) {
                this.blink = !this.blink;
                this.blinkDelay = 0.05f;
            }
        }
        else {
            // fixme: not good place for clear this flag
            this.blink = false;
        }

    }

    /**
     * @param isActive is this active item in menu
     */
    public void present(Graphics g, boolean isActive)
    {
        // draw string
        if (this.blink) {
            this.presentText(g, this.blinkFont);
        }
        else {
            this.presentText(g, this.font);
        }


        // draw active point wrap
        if (isActive)
        {
            // wrap
            g.drawRect(
                    this.wrap.left + 90,
                    this.wrap.top,
                    this.wrap.width() - 90,
                    this.wrap.height(),
                    this.rectPaint
            );

            // pointer
            g.drawPixmap (
                    this.pointerSprite.image,
                    this.wrap.left + 10,
                    this.wrap.top - 16,
                    this.pointerSprite.getLeft(),
                    this.pointerSprite.getTop(),
                    this.pointerSprite.width,
                    this.pointerSprite.height
            );
        }

        // draw temp wrap
        // g.drawRect(this.wrap, Color.GREEN);
    }

    public void presentText (Graphics g, Paint paint)
    {
        g.drawText(
                this.text,
                this.wrap.left + 100,
                this.wrap.top + 35,
                paint
        );
    }

    public boolean isPointInside(int x, int y) {
        return this.wrap.contains(x, y);
    }
}
