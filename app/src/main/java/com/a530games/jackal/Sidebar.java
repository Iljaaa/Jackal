package com.a530games.jackal;

import com.a530games.framework.math.Vector2;

public class Sidebar {

    private boolean isNeedRedraw = true;

    public int fps = 0;

    public Vector2 playerAngle;

    public int playerX = 0;
    public int playerY = 0;

    public int mapX = 0;
    public int mapY = 0;

    public Sidebar() {
        this.playerAngle = new Vector2();
    }

    /**
     * Надо ли обновлять сайдбар
     */
    public boolean isNeedRedraw(){
        return this.isNeedRedraw;
    }

    public void setFps(int fps){
        if (this.fps != fps) {
            this.isNeedRedraw = true;
            this.fps = fps;
        }
    }

    public void setPlayerPos(int x, int y) {
        if (this.playerX != x) {
            this.playerX = x;
            this.isNeedRedraw = true;
        }
        if (this.playerY != y) {
            this.playerY = y;
            this.isNeedRedraw = true;
        }
    }

    public void setMapPos(int x, int y) {
        if (this.mapX != x) {
            this.mapX = x;
            this.isNeedRedraw = true;
        }
        if (this.mapY != y) {
            this.mapY = y;
            this.isNeedRedraw = true;
        }
    }

    public void setPlayerAngle(Vector2 angle) {
        if (this.playerAngle.x != angle.x || this.playerAngle.y != angle.y) {
            this.playerAngle = angle;
            this.isNeedRedraw = true;
        }
    }
}
