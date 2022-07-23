package com.a530games.framework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

public class AndroidGraphics implements Graphics
{
    private AssetManager assets;
    private Bitmap frameBuffer;
    private Canvas canvas;

    private Rect srcRect;
    private Rect dstRect;

    private Paint paint;

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(this.frameBuffer);
        this.paint = new Paint();
        this.srcRect = new Rect();
        this.dstRect = new Rect();
    }

    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format)
    {
        Bitmap.Config config;
        if (format == PixmapFormat.RGB565)
            config = Bitmap.Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Bitmap.Config.ARGB_4444;
        else
            config = Bitmap.Config.ARGB_8888;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap;
        try {
            in = this.assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'"); }
        catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        if (bitmap.getConfig() == Bitmap.Config.RGB_565) format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() ==  Bitmap.Config.ARGB_4444) format = PixmapFormat.ARGB4444;
        else format = PixmapFormat.ARGB8888;
        return new AndroidPixmap(bitmap, format);
    }

    @Override
    public void clear(int color) {
        this.canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        this.paint.setColor(color);
        this.canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        this.paint.setColor(color);
        this.canvas.drawLine(x, y, x2, y2, this.paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        this.paint.setColor(color);
        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    @Override
    public void drawRect(Rect r, Paint paint) {
        this.canvas.drawRect(r, paint);
    }

    @Override
    public void drawRect(Rect r, int color) {
        this.paint.setColor(color);
        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawRect(r, this.paint);
    }

    @Override
    public void drawCircle(int x, int y, int radius, int color) {
        this.paint.setColor(color);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(2);
        this.canvas.drawCircle(x, y, radius, this.paint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight)
    {
        this.srcRect.left = srcX;
        this.srcRect.top = srcY;
        this.srcRect.right = srcX + srcWidth - 1;
        this.srcRect.bottom = srcY + srcHeight - 1;

        this.dstRect.left = x;
        this.dstRect.top = y;
        this.dstRect.right = x + srcWidth - 1;
        this.dstRect.bottom = y + srcHeight - 1;

        this.canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        this.canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
    }

    @Override
    public void drawText(String text, int x, int y, int textSize, int color)
    {
        this.paint.setTextSize(textSize);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(color);
        this.canvas.drawText(text, x, y, this.paint);
    }

    @Override
    public int getWidth() {
        return this.frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return this.frameBuffer.getHeight();
    }
}
