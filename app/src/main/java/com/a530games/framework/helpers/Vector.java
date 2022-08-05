package com.a530games.framework.helpers;

public class Vector {

    // position
    public float x;
    public float y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void updateByAngle(float angle) {
        this.x = (float) Math.sin(angle * Math.PI);
        this.y = (float) Math.cos(angle * Math.PI);
    }
}
