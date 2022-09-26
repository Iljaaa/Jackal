package com.a530games.jackal;

import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;
import com.a530games.framework.math.Vector2;

public class SpriteWithAnimation extends Sprite
{
    private float timer = 0;

    private float animationDelay = 0.1f;

    public boolean isStart = false;

    private Vector2 position;

    public SpriteWithAnimation(Pixmap image) {
        super(image);
        this.position = new Vector2();
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

    public void present (Graphics g, World world)
    {
        g.drawPixmap(
                this.image,
                world.map.screenLeftPotion(this.position.x) + this.screenMarginLeft,
                world.map.screenTopPotion(this.position.y) + this.screenMarginTop,
                this.getLeft(),
                this.getTop(),
                this.width,
                this.height);
    }
}
