package com.a530games.jackal;

import com.a530games.framework.Pixmap;

public class Sprite
{
    protected int col = 0;
    protected int row = 0;

    public final int spriteWidth = 64;
    public final int spriteHeight = 64;

    public Pixmap image;

    public Sprite(Pixmap image) {
        // this.col = col;
        // this.row = row;
        this.image = image;
    }

    public int getLeft () {
        return this.col * this.spriteWidth;
    }

    public int getTop() {
        return this.row * this.spriteHeight;
    }

    public void set(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
