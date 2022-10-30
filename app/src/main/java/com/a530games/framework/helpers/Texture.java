package com.a530games.framework.helpers;

import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;

public abstract class Texture
{

    Rect frame;

    Pixmap image;

    public Texture(Pixmap image, int left, int top, int width, int height)
    {
        this.image = image;
        this.frame = new Rect(left, top, left + width, top + height);
    }

    /**
     * Draw on position
     */
    public void draw(Graphics g, int x, int y){
        g.drawPixmap(
                this.image,
                x,
                y,
                this.frame.left,
                this.frame.top,
                this.frame.width(),
                this.frame.height()
        );
    }
}
