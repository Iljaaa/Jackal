package com.a530games.framework;

import android.graphics.Rect;

import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.map.Map;

public class Camera2D
{
    /**
     * Screen size
     */
    private final int screenWidth, screenHeight;

    /**
     * Map block size
     */
    private final int mapBlockWidth, mapBlockHeight;

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
    private final int followObjectViewDistance = 100;

    /**
     * Camera max coords
     */
    private final Rect mapLimits;


    /**
     * half screen in blocks
     */
    private final int halfWidthInBlocks, halfHeightInBlocks;

    /**
     * Whiew rect in идщсл
     */
    private final Rect _viewRect;

    public Camera2D(int screenWidth, int screenHeight, int mapBlockWith, int mapBlockHeight)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.mapBlockWidth = mapBlockWith;
        this.mapBlockHeight = mapBlockHeight;

        //
        this.halfWidthInBlocks = (int) Math.ceil(screenWidth * 0.5/ mapBlockWith);
        this.halfHeightInBlocks = (int) Math.ceil(screenHeight * 0.5/ mapBlockHeight);

        this.position = new Vector2F();
        // this.playerScreenRect = new Rect();

        this.mapLimits = new Rect();

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
     *
     */
    public void updateLimitsByMapSize(int mapCols, int mapRows)
    {
        this.mapLimits.set(
                (this.screenWidth / 2),
                (this.screenHeight / 2),
                (mapCols * this.mapBlockWidth) - (this.screenWidth / 2),
                (mapRows * this.mapBlockHeight) - (this.screenHeight / 2)
        );
    }

    /**
     * Follow by point
     */
    public void followByPoint(float mapX, float mapY)
    {
        float deltaX = this.position.x - mapX;
        float deltaY = this.position.y - mapY;

        // top
        if (deltaY < (-1 * this.followObjectViewDistance)) {
            this.position.y -= deltaY + this.followObjectViewDistance;
            if (this.position.y > this.mapLimits.bottom) this.position.y = this.mapLimits.bottom;
        }

        if (deltaY > this.followObjectViewDistance) {
            this.position.y -= deltaY - this.followObjectViewDistance;
            if (this.position.y < this.mapLimits.top) this.position.y = this.mapLimits.top;
        }

        // left
        if (deltaX < (-1 * this.followObjectViewDistance)) {
            this.position.x -= deltaX + this.followObjectViewDistance;
            if (this.position.x > this.mapLimits.right) this.position.x = this.mapLimits.right;
        }

        if (deltaX > this.followObjectViewDistance) {
            this.position.x -= deltaX - this.followObjectViewDistance;
            if (this.position.x < this.mapLimits.left) this.position.x = this.mapLimits.left;
        }

    }

    /**
     * Set camera position
     */
    public void setPosition(Vector2F newPosition)
    {
        this.position.set(newPosition);

        // move camera inside limits
        if (this.position.y > this.mapLimits.bottom) this.position.y = this.mapLimits.bottom;
        if (this.position.y < this.mapLimits.top) this.position.y = this.mapLimits.top;
        if (this.position.x > this.mapLimits.right) this.position.x = this.mapLimits.right;
        if (this.position.x < this.mapLimits.left) this.position.x = this.mapLimits.left;
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

    public int screenLeft(float mapLeft)
    {
        return (int) (mapLeft - this.position.x + (this.screenWidth * 0.5));
    }

    public int screenTop(float mapTop)
    {
        return (int) (mapTop - this.position.y + (this.screenHeight * 0.5));
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
