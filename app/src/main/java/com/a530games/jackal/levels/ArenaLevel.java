package com.a530games.jackal.levels;

import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.items.Beach;
import com.a530games.jackal.map.items.Rock;

public class ArenaLevel implements Level
{
    @Override
    public String getCode() {
        return "arena";
    }

    @Override
    public int getMapWidthInCols() {
        return 30;
    }

    @Override
    public int getMapHeightInCols() {
        return 30;
    }

    @Override
    public Map.Cell getPlayerDropPointCell() {
        return new Map.Cell(15, 15);
    }

    @Override
    public void addObjectsOnMap(Map map)
    {
        map.addObjectToMap(new Rock(3, 2, Rock.MOVE_ROCK_1), 3, 2);

        // bottom line
        for (int col = 0; col < map.mapCols; col++) {
            map.addObjectToMap(new Beach(map.mapRows - 1, col), map.mapRows - 1, col);
        }

    }
}
