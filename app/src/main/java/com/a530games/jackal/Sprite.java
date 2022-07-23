package com.a530games.jackal;

public class Sprite
{
    public int col;
    public int row;

    public final int spriteWidth = 64;
    public final int spriteHeight = 64;

    public Sprite(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getLeft () {
        return this.col * this.spriteWidth;
    }

    public int getTop() {
        return this.row * this.spriteHeight;
    }
}
