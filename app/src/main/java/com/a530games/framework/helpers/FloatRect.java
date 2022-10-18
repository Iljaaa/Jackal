package com.a530games.framework.helpers;

import android.graphics.PointF;
import android.graphics.Rect;

import com.a530games.framework.math.Vector2F;

public class FloatRect
{
    public float left;
    public float top;
    public float right;
    public float bottom;

    private final Rect _drawRect;
    private final PointF _center;

    public FloatRect(float left, float top, float right, float bottom)
    {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        this._drawRect = new Rect();
        this._center = new PointF();
    }

    /**
     * Check intersect two FLOAT rect
     */
    public static boolean isIntersectsTwoRectF(FloatRect r1, FloatRect r2)
    {
        //
        if (r1.bottom < r2.top){
            return false;
        }

        if (r1.top > r2.bottom){
            return false;
        }

        if (r1.right < r2.left){
            return false;
        }

        if (r1.left > r2.right) {
            return false;
        }

        return true;
    }

    /**
     * Check intersect two NOT rect
     */
    public static boolean isIntersectsTwoRect(Rect r1, FloatRect r2)
    {
        //
        if (r1.bottom < r2.top){
            return false;
        }

        if (r1.top > r2.bottom){
            return false;
        }

        if (r1.right < r2.left){
            return false;
        }

        if (r1.left > r2.right) {
            return false;
        }

        return true;
    }

    public void move(float deltaX, float deltaY)
    {
        this.right = (this.left + deltaX) + this.getWidth();
        this.left = (this.left + deltaX);

        this.bottom = (this.top + deltaY) + this.getHeight();
        this.top = (this.top + deltaY);
    }

    public void moveTo (float x, float y)
    {
        this.right = x + this.getWidth();
        this.left = x;

        this.bottom = y + this.getHeight();
        this.top = y;
    }

    public void moveTo(Vector2F vector) {
        this.moveTo(vector.x, vector.y);
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

    public PointF getCenter () {
        this._center.x = this.getCenterLeft();
        this._center.y = this.getCenterTop();
        return _center;
    }

    /**
     * @return bool

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
    }*/

    /*public Rect get_drawRect()
    {
        this._drawRect.left = (int) Math.ceil(this.left);
        this._drawRect.top = (int) Math.round(this.top);
        this._drawRect.right = (int) Math.round(this.right);
        this._drawRect.bottom = (int) Math.round(this.bottom);
        return this._drawRect;
    }*/
}
