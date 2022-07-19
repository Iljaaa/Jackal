package com.a530games.framework;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class RenderView extends View
{
    Bitmap bitmap;
    Random rand = new Random();

    public RenderView(Context context) throws IOException {
        super(context);

        AssetManager assetManager = context.getAssets();
        InputStream it = assetManager.open("images/bg.jpg");
        this.bitmap = BitmapFactory.decodeStream(it);
        it.close();
    }

    protected void onDraw(Canvas canvas)
    {

        canvas.drawRGB(rand.nextInt(256), rand.nextInt(256),
                rand.nextInt(256));

        canvas.drawBitmap(this.bitmap, 100, 100, null);

        this.invalidate();
    }
}
