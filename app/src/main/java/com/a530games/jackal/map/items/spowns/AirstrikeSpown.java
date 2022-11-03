package com.a530games.jackal.map.items.spowns;

import android.graphics.Color;
import android.graphics.Paint;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.textures.BigBlowAnimation;

public class AirstrikeSpown extends MapCell
{
    private final BigBlowAnimation animation;

    private final Paint crossPaint;

    public AirstrikeSpown(int col, int row) {
        super(col, row);
        this.animation = new BigBlowAnimation();
        this.animation.setCircle(true);
        this.animation.setFrameTime(0.09f);

        this.crossPaint = new Paint();
        this.crossPaint.setStyle(Paint.Style.STROKE);
        this.crossPaint.setStrokeWidth(2);
        this.crossPaint.setColor(Color.RED);
        // this.crossPaint.setAlpha(90);
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
    {
        int centerX = this.rect.centerX();
        int centerY = this.rect.centerY();

        g.drawCircle(centerX, centerY, 15, this.crossPaint);

        g.drawLine(centerX - 25, centerY, centerX + 25, centerY, this.crossPaint);
        g.drawLine(centerX, centerY - 25, centerX, centerY+ 25, this.crossPaint);
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {
        this.animation.update(deltaTime);
    }

    @Override
    public void draw(Graphics g, Map map)
    {
        this.animation.present(g,
            map.screenLeftPotion(this.rect.left - 16),
            map.screenTopPotion(this.rect.top - 45)
        );
    }

    @Override
    public void drawHitBox(Graphics g, Map map) {

    }

    @Override
    public void drawTopLayout(Graphics g, Map map) {

    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox rectOnMap) {
        return false;
    }
}
