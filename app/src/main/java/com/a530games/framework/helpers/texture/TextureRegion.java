package com.a530games.framework.helpers.texture;

import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;

public class TextureRegion
{

    private final Rect frame;

    public TextureRegion(int left, int top, int width, int height) {
        this.frame = new Rect(left, top, left + width, top + height);
    }

    public void offsetTo(int newLeft, int newTop) {
        this.frame.offsetTo(newLeft, newTop);
    }

    public int width() {
        return this.frame.width();
    }

    public int height() {
        return this.frame.height();
    }

    /**
     * Draw region
     */
    public void draw(Graphics g, Pixmap image, int x, int y)
    {

    }
}
