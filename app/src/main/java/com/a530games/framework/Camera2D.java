package com.a530games.framework;

import android.graphics.Rect;

import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.map.Map;

public class Camera2D
{
    /**
     * Main camera position
     */
    public final Vector2F position;

    /**
     *
     */
    public float zoom = 1f;

    /**
     * Camera distance to follow object
     */
    private final int followObjectViewDistance = 200;

    /**
     * half screen in blocks
     */
    private final int halfWidthInBlocks, halfHeightInBlocks;

    /**
     * Whiew rect
     */
    private final Rect _viewRect;

    public Camera2D(int screenWidth, int screenHeight, int mapBlockWith, int mapBlockHeight)
    {
        //
        this.halfWidthInBlocks = (int) Math.ceil(screenWidth * 0.5/ mapBlockWith);
        this.halfHeightInBlocks = (int) Math.ceil(screenHeight * 0.5/ mapBlockHeight);

        this.position = new Vector2F();
        // this.playerScreenRect = new Rect();

        // calculate screen rect for move map position
        /*this.playerScreenRect.set (
                200,
                200,
                screenWidth - 200,
                screenHeight - 200
        );*/


        this._viewRect = new Rect();
    }

    /**
     * Follow by point
     */
    public void followByPoint(float mapX, float mapY)
    {
        float deltaX = this.position.x - mapX;
        float deltaY = this.position.y - mapY;

        if (deltaY < (-1 * this.followObjectViewDistance)) {
            this.position.y -= deltaY + this.followObjectViewDistance;
        }

        if (deltaY > 200) {
            this.position.y -= deltaY - this.followObjectViewDistance;
        }

        if (deltaX < (-1 * this.followObjectViewDistance)) {
            this.position.x -= deltaX + this.followObjectViewDistance;
        }

        if (deltaX > 200) {
            this.position.x -= deltaX - this.followObjectViewDistance;
        }

    }


    public void set(Vector2F newPosition){
        this.position.set(newPosition);
    }

    /**
     *
     */
    public Rect getViewRectInBlocks(Map.Cell centerCell)
    {
        // берем полицияю камеры

        this._viewRect.set(
                centerCell.col - this.halfWidthInBlocks,
                centerCell.row - this.halfHeightInBlocks,
                centerCell.col + this.halfWidthInBlocks,
                centerCell.row + this.halfHeightInBlocks
        );

        return this._viewRect;

        /*if (r.left < 0) r.left = 0;
        if (r.top < 0) r.top = 0;

        if (r.right > this.world.map.mapCols) r.left = this.world.map.mapCols;
        if (r.bottom > this.world.map.mapRows) r.bottom = this.world.map.mapRows;*/
    }



    /*
    public void setViewportAndMatrices()
    {
        GL10 gl = glGraphics.getGL();
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glMatrixMode(GL10.GL_PROJECTION);Камера в 2D
        431
        gl.glLoadIdentity();
        gl.glOrthof(position.x — frustumWidth * zoom / 2,
                position.x + frustumWidth * zoom/ 2,
                position.y — frustumHeight * zoom / 2,
                position.y + frustumHeight * zoom/ 2,
                1, -1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }*/
}
