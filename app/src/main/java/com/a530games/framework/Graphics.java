package com.a530games.framework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

public interface Graphics {

    enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    Pixmap newPixmap(String fileName, PixmapFormat format);
    void clear(int color);
    void drawPixel(int x, int y, int color);
    void drawLine(int x, int y, int x2, int y2, int color);
    void drawLine(int x, int y, int x2, int y2, Paint paint);
    void drawRect(int x, int y, int width, int height, int color);
    void drawRect(int x, int y, int width, int height, Paint paint);
    void drawRect(Rect r, int color);
    void drawRect(Rect r, Paint paint);
    void drawCircle(int x, int y, int radius, int color);

    // todo: remove this methods
    void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
    void drawPixmap(Pixmap pixmap, int x, int y);

    void drawBitmap(Bitmap bitmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
    void drawBitmap(Bitmap bitmap, int x, int y);

    void drawText(String text, int x, int y, int textSize, int color);

    int getWidth();
    int getHeight();

    AssetManager getAssetManager();

}
