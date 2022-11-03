package com.a530games.jackal;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.Sprite;
import com.a530games.framework.math.Vector2F;

public class SpriteWithAnimation extends Sprite
{
    private float timer = 0;

    private float animationDelay = 0.1f;

    public boolean isStart = false;

    private Vector2F position;

    public SpriteWithAnimation(Pixmap image) {
        super(image);
        this.position = new Vector2F();
    }

    /**
     * Start animation
     */
    public void start (float mapPosX, float mapPosY)
    {
        this.position.x = mapPosX;
        this.position.y = mapPosY;

        this.col = 0;

        this.timer = this.animationDelay;
        this.isStart = true;
    }

    public void update (float deltaTime)
    {
        if (!this.isStart) return;
        this.timer -= deltaTime;

        if (this.timer <= 0) {
            this.timer = this.animationDelay;
            this.col++;
            if (this.col >= 3) {
                this.col = 0;
                this.isStart = false;
            }
        }
    }

    public void present (Graphics g, Camera2D camera)
    {
        g.drawPixmap(
                this.image,
                camera.screenLeft(this.position.x) + this.screenMarginLeft,
                camera.screenTop(this.position.y) + this.screenMarginTop,
                this.getLeft(),
                this.getTop(),
                this.width,
                this.height);
    }
}
