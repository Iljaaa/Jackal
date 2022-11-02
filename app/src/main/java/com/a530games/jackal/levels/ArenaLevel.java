package com.a530games.jackal.levels;

import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.items.Beach;
import com.a530games.jackal.map.items.Spown;

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

        // bottom line
        for (int col = 0; col < map.mapCols; col++) {
            map.addObjectToMap(new Beach(map.mapRows - 1, col), map.mapRows - 1, col);
        }


        map.addObjectToMap(new Spown(1, 1), 1, 1);



    }
}
