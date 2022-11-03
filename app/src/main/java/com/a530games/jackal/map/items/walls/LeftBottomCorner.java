package com.a530games.jackal.map.items.walls;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;

public class LeftBottomCorner extends Corner
{

    public LeftBottomCorner(int row, int col)
    {
        super(row, col);
        this.sprite.set(0, 2);
    }

    @Override
    public void drawHitBox(Graphics g, Camera2D camera)
    {
        // top line
        g.drawLine(
                camera.screenLeft(this.hitBox.rect.left),
                camera.screenTop(this.hitBox.rect.top),
                camera.screenLeft(this.hitBox.rect.centerX()),
                camera.screenTop(this.hitBox.rect.top),
                this.hitBoxPaint
                );

        // bottom line
        g.drawLine(
                camera.screenLeft(this.hitBox.rect.left),
                camera.screenTop(this.hitBox.rect.bottom),
                camera.screenLeft(this.hitBox.rect.right),
                camera.screenTop(this.hitBox.rect.bottom),
                this.hitBoxPaint
        );

        // left line
        g.drawLine(
                camera.screenLeft(this.hitBox.rect.left),
                camera.screenTop(this.hitBox.rect.top),
                camera.screenLeft(this.hitBox.rect.left),
                camera.screenTop(this.hitBox.rect.bottom),
                this.hitBoxPaint
        );

        // right line
        g.drawLine(
                camera.screenLeft(this.hitBox.rect.right),
                camera.screenTop(this.hitBox.rect.centerY()),
                camera.screenLeft(this.hitBox.rect.right),
                camera.screenTop(this.hitBox.rect.bottom),
                this.hitBoxPaint
                );


        g.drawLine(
                camera.screenLeft(this.hitBox.rect.centerX()),
                camera.screenTop(this.hitBox.rect.centerY()),
                camera.screenLeft(this.hitBox.rect.right),
                camera.screenTop(this.hitBox.rect.centerY()),
                this.hitBoxPaint
                );

        g.drawLine(
                camera.screenLeft(this.hitBox.rect.centerX()),
                camera.screenTop(this.hitBox.rect.top),
                camera.screenLeft(this.hitBox.rect.centerX()),
                camera.screenTop(this.hitBox.rect.centerY()),
                this.hitBoxPaint
                );


        // super.drawHitBox(g, map);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox rectOnMap)
    {
        // check left bottom connor

        if (rectOnMap.rect.left < this.hitBox.rect.centerX()) {
            return true;
        }

        if (rectOnMap.rect.bottom > this.hitBox.rect.centerY()) {
            return true;
        }

        return false;
    }
}
