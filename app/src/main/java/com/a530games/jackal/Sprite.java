package com.a530games.jackal;

public class Sprite {
    public int col = 0;
    public int row = 0;

    public int spriteWidth = 64;
    public int spriteHeight = 64;

    public Sprite(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getX() {
        return this.col * this.spriteWidth;
    }

    public int getY() {
        return this.row * this.spriteHeight;
    }
}
