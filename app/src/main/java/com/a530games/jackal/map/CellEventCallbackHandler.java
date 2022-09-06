package com.a530games.jackal.map;

import com.a530games.jackal.objects.enemies.Enemy;

/**
 * Events from cell
 */
public interface CellEventCallbackHandler
{
    /**
     * On enemy spown
     * @param spownCell cell where enemy spown
     * @param enemy enemy
     */
    void spownEnemy(MapCell spownCell, Enemy enemy);
}
