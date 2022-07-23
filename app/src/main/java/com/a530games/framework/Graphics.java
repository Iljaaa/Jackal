package com.a530games.framework;

import android.graphics.Rect;

public interface Graphics {

    static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    Pixmap newPixmap(String fileName, PixmapFormat format);
    void clear(int color);
    void drawPixel(int x, int y, int color);
    void drawLine(int x, int y, int x2, int y2, int color);
    void drawRect(int x, int y, int width, int height, int color);
    void drawRect(Rect r, int color);
    void drawCircle(int x, int y, int radius, int color);
    void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
                           int srcWidth, int srcHeight);
    void drawPixmap(Pixmap pixmap, int x, int y);
    void drawText(String text, int x, int y, int textSize, int color);
    int getWidth();
    int getHeight();

}
