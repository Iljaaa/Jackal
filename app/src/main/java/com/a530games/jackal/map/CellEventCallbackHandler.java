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
     * @return Enemy Spown enemy
     */
    Enemy spownEnemy(MapCell spownCell);
}
