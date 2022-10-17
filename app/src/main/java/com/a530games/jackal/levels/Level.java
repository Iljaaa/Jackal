package com.a530games.jackal.levels;

import android.graphics.Point;
import android.graphics.PointF;

import com.a530games.framework.math.Vector2;
import com.a530games.jackal.map.Cell;
import com.a530games.jackal.map.Map;

public interface Level
{
    /**
     * Map width in blocks
     */
    int getMapWidthInCols();

    /**
     * Map height in blocks
     */
    int getMapHeightInCols();

    /**
     * Player start position
     */
    Vector2 getPlayerStartPosition();

    /**
     * Map start position
     */
    Cell getMapStartPosition();

    /**
     * Add objects on map
     */
    void addObjectsOnMap(Map map);
}
