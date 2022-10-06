package com.a530games.jackal.levels;

import com.a530games.framework.math.Vector2;

public interface Level
{
    /**
     * Map width in blocks
     */
    int getMapWidth();

    /**
     * Map height in blocks
     */
    int getMapHeight();

    /**
     * Player start posotion
     */
    Vector2 getPlayerStartPosition();
}
