package com.a530games.jackal;

public class Sidebar {

        private boolean isNeedRedraw = true;

    int fps = 0;

    public double playerAngle;

    int playerX = 0;
    int playerY = 0;

    int mapX = 0;
    int mapY = 0;

    public Sidebar() {

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

    public void setPlayerAngle(double angle) {
        if (this.playerAngle != angle){
            this.playerAngle = angle;
            this.isNeedRedraw = true;
        }
    }
}
