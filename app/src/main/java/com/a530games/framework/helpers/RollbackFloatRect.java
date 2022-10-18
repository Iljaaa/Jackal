package com.a530games.framework.helpers;

import com.a530games.framework.math.Vector2F;

public class RollbackFloatRect extends FloatRect
{
    private float lastLeft;
    public float lastTop;
    public float lastRight;
    public float lastBottom;


    public RollbackFloatRect(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    @Override
    public void moveTo(Vector2F vector)
    {
        // save state
        this.saveState();

        super.moveTo(vector);
    }

    @Override
    public void moveTo(float x, float y)
    {
        // save state
        this.saveState();

        super.moveTo(x, y);
    }

    /**
     * roll back to saved position
     */
    public void rollback() {
        this.left = this.lastLeft;
        this.top = this.lastTop;
        this.right = this.lastRight;
        this.bottom = this.lastBottom;
    }

    private void saveState() {
        this.lastLeft = this.left;
        this.lastTop = this.top;
        this.lastRight = this.right;
        this.lastBottom = this.bottom;
    }
}
