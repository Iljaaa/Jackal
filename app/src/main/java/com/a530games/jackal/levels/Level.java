package com.a530games.jackal.levels;

import com.a530games.jackal.map.RectCell;
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
    Map.Cell getPlayerDropPointCell();

    /**
     * Add objects on map
     */
    void addObjectsOnMap(Map map);
}
