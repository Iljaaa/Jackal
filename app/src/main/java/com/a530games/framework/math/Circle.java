package com.a530games.framework.math;

public class Circle {

    public Vector2F center;

    public float radius;

    public Circle(float x, float y, float radius) {
        this.center = new Vector2F(x, y);
        this.radius = radius;
    }

    public boolean isPointInside (float x, float y) {
        return this.center.dist(x, y) < this.radius;
    }
}
