package com.a530games.framework.math;

public class Vector2F
{
    public static float TO_RADIANS = (1 / 180.0f) * (float) Math.PI;
    public static float TO_DEGREES = (1 / (float) Math.PI) * 180;

    public float x, y;

    public Vector2F() {
    }

    public Vector2F(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2F(Vector2F other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2F cpy() {
        return new Vector2F( this.x, this.y );
    }

    /**
     * @param x x position
     * @param y y position
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2F other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void add(Vector2F other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void sub(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void sub(Vector2F other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public float len() {
        // new FloatMath()
        return (float) Math.sqrt(x * x + y * y);
    }

    public void nor()
    {
        float len = this.len();
        if (len != 0) {
            this.x /= len;
            this.y /= len;
        }
    }

    public float angle()
    {
        return (float) Math.atan2(this.y, this.x);
    }

    public float angleInDegrees()
    {
        float angle = (float) Math.atan2(this.y, this.x) * TO_DEGREES;
        if (angle < 0) angle += 360;
        return angle;
    }

    public void rotate(float angleInDegrees)
    {
        float rad = angleInDegrees * TO_RADIANS;
        // float cos = FloatMath.cos(rad);
        // float sin = FloatMath.sin(rad);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        // float newX = this.x * cos - this.y * sin;
        // float newY = this.x * sin + this.y * cos;
        // this.x = newX;
        // this.y = newY;
        this.x = (this.x * cos) - (this.y * sin);
        this.y = (this.x * sin) + (this.y * cos);
    }

    public int getQuater ()
    {
        float angle = this.angleInDegrees();
        if (angle > 335) return 0;
        if (angle > 290) return 7;
        if (angle > 245) return 6;
        if (angle > 200) return 5;
        if (angle > 155) return 4;
        if (angle > 110) return 3;
        if (angle > 65) return 2;
        if (angle > 20) return 1;

        return 0;
    }

    /*public void setAngleInDegrees(float angleInDegrees)
    {
        float rad = angleInDegrees * TO_RADIANS;
        // float cos = FloatMath.cos(rad);
        // float sin = FloatMath.sin(rad);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;
        this.x = newX;
        this.y = newY;
    }*/

    public float dist(Vector2F other) {
        float distX = this.x - other.x;
        float distY = this.y - other.y;
        // return FloatMath.sqrt(distX * distX + distY * distY);
        return (float) Math.sqrt(distX * distX + distY * distY);
    }

    public float dist(float x, float y) {
        float distX = this.x - x;
        float distY = this.y - y;
        // return FloatMath.sqrt(distX * distX + distY * distY);
        return (float) Math.sqrt(distX * distX + distY * distY);
    }
}
