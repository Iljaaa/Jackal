package com.a530games.jackal.levels;

import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.items.Beach;
import com.a530games.jackal.map.items.spowns.Commandos2Spown;
import com.a530games.jackal.map.items.spowns.CommandowSpown;
import com.a530games.jackal.map.items.spowns.TankSpown;

public class ArenaLevel implements Level
{
    @Override
    public String getCode() {
        return "arena";
    }

    @Override
    public int getMapWidthInCols() {
        return 20;
    }

    @Override
    public int getMapHeightInCols() {
        return 20;
    }

    @Override
    public Map.Cell getPlayerDropPointCell() {
        return new Map.Cell(10, 10);
    }

    @Override
    public void addObjectsOnMap(Map map)
    {
        // bottom line
        for (int col = 0; col < map.mapCols; col++) {
            map.addObjectToMap(new Beach(map.mapRows - 1, col), col, map.mapRows - 1);
        }

        map.addObjectToMap(new TankSpown(1, 1), 1, 1);
        map.addObjectToMap(new TankSpown(19, 1), 19, 1);
        map.addObjectToMap(new TankSpown(19, 19), 19, 19);
        map.addObjectToMap(new TankSpown(1, 19), 1, 19);

        // map.addObjectToMap(new TankSpown(15, 13), 15, 13);
        // map.addObjectToMap(new CommandowSpown(13, 13), 13, 13);
        // map.addObjectToMap(new Commandos2Spown(17, 13), 17, 13);

    }
}
