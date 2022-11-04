package com.a530games.jackal.levels;

import com.a530games.jackal.map.Map;

public interface Level
{
    /**
     * Code of level
     */
    String getCode();

    /**
     * Name of level
     */
    String getName();

    /**
     * Map width in blocks
     */
    int getMapCols();

    /**
     * Map height in blocks
     */
    int getMapRows();

    /**
     * Map start position
     */
    Map.Cell getPlayerDropPointCell();

    /**
     * Add objects on map
     */
    void addObjectsOnMap(Map map);
}
