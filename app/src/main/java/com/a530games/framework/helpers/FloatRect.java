package com.a530games.framework.helpers;

import android.graphics.Rect;

public class FloatRect
{
    public float left;
    public float top;
    public float right;
    public float bottom;

    private Rect drawRect;

    public FloatRect(float left, float top, float right, float bottom)
    {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        this.drawRect = new Rect();
    }

    /*public void move(float deltaX, float deltaY)
    {
        this.right = (this.left + deltaX) + this.getWidth();
        this.left = (this.left + deltaX);

        this.bottom = (this.top + deltaY) + this.getHeight();
        this.top = (this.top + deltaY);
    }*/

    public void moveTo (float x, float y)
    {
        // fixme: вопрос что быстрее пересичтать или условие
        this.right = x + this.getWidth();
        this.left = x;

        this.bottom = y + this.getHeight();
        this.top = y;
    }

    public float getWidth() {
        return (this.right - this.left);
    }

    public float getHeight() {
        return (this.bottom - this.top);
    }

    public float getCenterLeft (){
        return this.left + (this.getWidth() / 2);
    }

    public float getCenterTop () {
        return this.top + (this.getHeight() / 2);
    }

    /**
     * @return bool
     */
    public boolean isIntersect(FloatRect rect)
    {
        // if block higher
        if (rect.bottom < this.top) {
            return false;
        }

        // if the block bellow
        if (rect.top > this.bottom) {
            return false;
        }

        // if block is to the left
        if (rect.right < this.left) {
            return false;
        }

        // if block is to the right
        if (rect.left > this.right) {
            return false;
        }

        return true;
    }

    public Rect getDrawRect ()
    {
        this.drawRect.left = Math.round(this.left);
        this.drawRect.top = Math.round(this.top);
        this.drawRect.right = Math.round(this.right);
        this.drawRect.bottom = Math.round(this.bottom);
        return this.drawRect;
    }
}
