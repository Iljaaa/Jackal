package com.a530games.jackal.menu;

import android.graphics.Color;
import android.graphics.Paint;

import com.a530games.framework.Graphics;
import com.a530games.jackal.Controller;

import java.util.ArrayList;

public abstract class Menu
{
    /**
     * Text font size
     */
    protected final int fontSize = 30;

    protected final int buttonWidth = 400;

    protected final int buttonHeight = 50;

    protected final int buttonMargin = 10;

    protected Paint font;
    protected Paint blinkFont;
    protected Paint rectPaint;

    ArrayList<String> items;

    protected int selectedIndex = 1;

    public Menu()
    {
        this.items = new ArrayList<>();

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

    /**
     * Update menu
     */
    public abstract void update (Controller controller, float deltaTime);

    /**
     * Draw menu
     */
    public abstract void present (Graphics g);
}
