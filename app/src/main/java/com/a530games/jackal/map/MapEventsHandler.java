package com.a530games.jackal.map;

import com.a530games.jackal.objects.enemies.Enemy;

public interface MapEventsHandler
{
    /**
     *
     */
    void mapInitFinish();

    /**
     * On cell sown enemy
     * @param spownCell cell where enemy spown
     * @param enemy enemy
     */
    void spownEnemyOnCell(MapCell spownCell, Enemy enemy);

    /**
     * On map win
     */
    void mapWin ();
}
