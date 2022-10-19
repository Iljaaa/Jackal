package com.a530games.jackal.levels;

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
     * Map start position
     */
    Cell getPlayerDropPointCell();

    /**
     * Add objects on map
     */
    void addObjectsOnMap(Map map);
}
