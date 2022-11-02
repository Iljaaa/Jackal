package com.a530games.jackal.map;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.HitBox;

public interface MapObject
{

    /**
     * Draw block on background
     * @param g Graphic object
     * @param map map object
     */
    void drawOnBackground(Graphics g, Map map);

    /**
     * Update not static block before craw
     */
    void update(float deltaTime, CellEventCallbackHandler callbackHandler);

    /**
     * Draw block
     * @param g Graphic object
     */
     void draw(Graphics g, Map map);

    /**
     * Draw hitbox
     * @param g Graphic object
     * @param map Map object
     */
    void drawHitBox(Graphics g, Map map);

    /**
     * Draw objects over plyer
     */
    void drawTopLayout (Graphics g, Map map);

    /**
     * Return not exists hitbox map
     * @return Hitbox rect
     */
    MapHitBox getHitBox(); //  {return null;}

    /**
     * Check intersect inside rect
     * @param mapLeft left position on map
     * @param mapTop top position on map
     * @return is has intersect pint inside block
     */
    boolean isIntersectPointInsideCell(float mapLeft, float mapTop);

    /**
     * Check intersect inside rect
     * @param rectOnMap Rect in map coordinate system to check
     * @return is intersected
     */
    boolean isIntersectRectInsideCell (HitBox rectOnMap);

    /**
     * Is cell conditions success for win
     * @return boolean is success
     */
    boolean isWin(); // { return true; }
}
