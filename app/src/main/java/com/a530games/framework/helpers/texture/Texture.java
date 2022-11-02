package com.a530games.framework.helpers.texture;

import android.graphics.ColorSpace;
import android.graphics.Rect;

import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;

public abstract class Texture
{
    /**
     * image draw rect
     */
    TextureRegion region;

    /**
     * Image
     */
    Pixmap image;

    public Texture(Pixmap image, int left, int top, int width, int height)
    {
        this.image = image;
        this.region = new TextureRegion(left, top, width, height);
    }

    private TextureRegion getRegion() {
        return this.region;
    }

    /**
     * Offset to with frame size
     */
    public void offsetToFrames(int newCol, int newRow) {
        this.region.offsetTo(
            newCol * this.region.width(),
            newRow * this.region.height()
        );
    }


    /**
     * Draw on position
     */
    public void draw(Graphics g, int x, int y)
    {
        this.region.draw(g, this.image, x, y);
    }
}
