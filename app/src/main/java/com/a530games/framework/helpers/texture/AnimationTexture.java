package com.a530games.framework.helpers.texture;

import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;

abstract public class AnimationTexture
{

    /**
     * Frames array
     */
    protected TextureRegion[] frames;

    /**
     * Image
     */
    private final Pixmap image;

    /**
     * Frame index
     */
    private int frame = 0;

    /**
     *
     */
    private float timer = 0.05f;

    /**
     * Frame time
     */
    protected float frameTime = 0.05f;

    /**
     * Is animation over
     */
    private boolean isOver = false;

    /**
     * Is animation in circus
     */
    private boolean isCircle = false;

    public AnimationTexture(Pixmap image)
    {
        this.image = image;
    }

    /*public void addFrame(TextureRegion frame){
        this.frames.add(frame);
    }*/

    public boolean isOver(){
        return this.isOver;
    }

    /**
     * Reset animation
     */
    public void reset() {
        this.frame = 0;
        this.isOver = false;
    }

    public void setCircle(boolean circle) {
        isCircle = circle;
    }

    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
        this.timer = frameTime;
    }

    /**
     * Update animation by delta time
     * @param deltaTime time delay
     */
    public void update(float deltaTime)
    {
        if (this.isOver) return;

        this.timer -= deltaTime;
        if (timer <= 0)
        {
            this.timer = this.frameTime;

            this.frame++;

            if (this.frame >= this.frames.length)
            {
                // set last frame
                // this.frame = this.frames.length - 1;

                if (this.isCircle) {
                    this.frame = 0;
                }
                else {
                    // the end
                    this.isOver = true;
                }
            }
        }
    }

    public void present(Graphics g, int x, int y)
    {
        if (this.isOver) return;
        this.frames[this.frame].draw(g, this.image, x, y);
    }

}
