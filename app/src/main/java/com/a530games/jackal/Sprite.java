package com.a530games.jackal;

import com.a530games.framework.Pixmap;

public class Sprite
{
    protected int col = 0;
    protected int row = 0;

    public int width = 64;
    public int height = 64;

    /**
     *
     */
    public int screenMarginLeft = 0;
    public int screenMarginTop = 0;

    public Pixmap image;

    public Sprite(Pixmap image) {
        // this.col = col;
        // this.row = row;
        this.image = image;
    }

    public void setSpriteSize (int width, int height){
        this.height = height;
        this.width = width;
    }

    public void setScreenMargin (int leftMargin, int topMargin){
        this.screenMarginLeft = leftMargin;
        this.screenMarginTop = topMargin;
    }

    /**
     * left insode sprite
     */
    public int getLeft () {
        return this.col * this.width;
    }

    public int getTop() {
        return this.row * this.height;
    }

    public void set(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
