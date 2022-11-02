package com.a530games.jackal.map;

import com.a530games.jackal.Jackal;

public abstract class MapCell extends RectCell implements MapObject
{

    public MapCell(int col, int row)
    {
        super(col, row, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);
    }

    /**
     * Return not exists hitbox map
     * @return Hitbox rect
     */
    public MapHitBox getHitBox() {
        return null;
    }

    /**
     * Is cell conditions success for win
     * @return boolean is success
     */
    public boolean isWin() {
        return true;
    }

    /*
     * Draw block on background
     * @param g Graphic object
     * @param map

    public abstract void drawOnBackground(Graphics g, Map map);

    /**
     * Update not static block before craw
     *
    public abstract void update(float deltaTime, CellEventCallbackHandler callbackHandler);

    /**
     * Draw block
     * @param g Graphic object
     *
    public abstract void draw(Graphics g, Map map);

    /**
     * Draw hitbox
     * @param g Graphic object
     * @param map Map object
     *
    public abstract void drawHitBox(Graphics g, Map map);

    /**
     * Draw objects over plyer
     *
    public abstract void drawTopLayout (Graphics g, Map map);

    /**
     * Check intersect inside rect
     * @param mapLeft left position on map
     * @param mapTop top position on map
     * @return is has intersect pint inside block
     *
    public abstract boolean isIntersectPointInsideCell(float mapLeft, float mapTop);

    /**
     * Check intersect inside rect
     * @param rectOnMap Rect in map coordinate system to check
     * @return is intersected

    public abstract boolean isIntersectRectInsideCell (FloatRect rectOnMap);*/

}
