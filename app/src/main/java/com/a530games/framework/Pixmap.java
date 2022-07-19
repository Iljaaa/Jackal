package com.a530games.framework;

public interface Pixmap
{
    int getWidth();

    int getHeight();

    Graphics.PixmapFormat getFormat();

    void dispose();
}
