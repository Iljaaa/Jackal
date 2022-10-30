package com.a530games.framework;

import android.graphics.Rect;

import com.a530games.jackal.map.Map;

public class Camera2D {

    // public final Vector2F position;

    public float zoom = 1f;

    /**
     * half screen in blocks
     */
    private final int halfWidthInBlocks;
    private int halfHeightInBlocks;

    /**
     * Whiew rect
     */
    private final Rect _viewRect;

    public Camera2D(int screenWidth, int screenHeight, int mapBlockWith, int mapBlockHeight)
    {
        this._viewRect = new Rect();

        //
        this.halfWidthInBlocks = (int) Math.ceil(screenWidth * 0.5/ mapBlockWith); // <--
        this.halfHeightInBlocks = (int) Math.ceil(screenHeight * 0.5/ mapBlockHeight);

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
