package com.a530games.jackal.objects;

import com.a530games.framework.Graphics;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.textures.BlowAnimation;

public final class SmallBlowsSwarm
{
    /**
     * Blows radius
     */
    private final int radius = 17;

    /**
     * Blows after death
     */
    // private final SpriteWithAnimation[] blows;
    private final Blow[] blows;

    /**
     * Next blow timer
     */
    private float nextBlowDelay; //  = 0.5f;

    /**
     * One blow delay
     */
    private final float oneBlowDelay;

    /**
     * Blow animate rate
     */
    private static class Blow
    {
        BlowAnimation animation;

        private int deltaX = 0, deltaY = 0;

        public Blow() {
            this.animation = new BlowAnimation();
        }

        public boolean isOver(){
            return this.animation.isOver();
        }

        public void reset(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.animation.reset();
        }

        public void update(float deltaTime){
            this.animation.update(deltaTime);
        }

        public void present(Graphics g, int screenX, int screenY){
            this.animation.present(g, screenX + this.deltaX, screenY + this.deltaY);
        }
    }

    public SmallBlowsSwarm(int blowsCount)
    {
        this.blows = new Blow[blowsCount];

        for (int i = 0; i < this.blows.length; i++) {
            this.blows[i] = new Blow();
        }

        // calculate blow times
        this.oneBlowDelay = (0.2f / (this.blows.length + 1));

        // start right now
        this.nextBlowDelay = 0;
    }

    public void update(float deltaTime)
    {
        //
        for (Blow blow : this.blows) {
            blow.update(deltaTime);
        }

        // start new blow
        this.nextBlowDelay -= deltaTime;
        if (nextBlowDelay <= 0)
        {
            // start blows
            do {
                this.startNextFreeBlow();

                // this.nextBlowIn += 0.05f; // <---- calculate this
                // this.nextBlowDelay += (0.2f / (this.blows.length + 1));
                this.nextBlowDelay += this.oneBlowDelay;

            }
            while (this.nextBlowDelay <= 0);
        }
    }

    /**
     * Start blow
     */
    private void startNextFreeBlow()
    {
        for (Blow blow : this.blows) {
            // if (this.blows[i].isStart) continue;
            if (!blow.isOver()) continue;

            blow.reset (this.getRandom(), this.getRandom());


            /*this.blows[i].start(
                    this.hitBox.getCenterX() + (Jackal.getRandom().nextFloat() * 17 * (Jackal.getRandom().nextBoolean() ? -1 : 1)) - 20,
                    this.hitBox.getCenterY() + (Jackal.getRandom().nextFloat() * 17 * (Jackal.getRandom().nextBoolean() ? -1 : 1)) - 20
            );*/

            break;
        }
    }

    private int getRandom()
    {
        return (int) (
                (Jackal.getRandom().nextBoolean() ? -1 : 1)
                * (Jackal.getRandom().nextFloat() * this.radius)
        )
        + 11; // <- sprite factor
    }

    public void present (Graphics g, int screenX, int screenY)
    {
        for (Blow blow : this.blows) {
            blow.present(g, screenX, screenY);
        }
    }
}
