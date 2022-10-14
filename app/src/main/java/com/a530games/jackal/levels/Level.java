package com.a530games.jackal.levels;

import com.a530games.framework.math.Vector2;
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
     * Player start posotion
     */
    Vector2 getPlayerStartPosition();

    /**
     * Add objects on map
     */
    void addObjectsOnMap(Map map);
}
