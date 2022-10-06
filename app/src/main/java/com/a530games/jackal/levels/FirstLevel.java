package com.a530games.jackal.levels;

import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Jackal;

public class FirstLevel implements Level
{

    @Override
    public int getMapWidth() {
        return 30;
    }

    @Override
    public int getMapHeight() {
        return 30;
    }

    @Override
    public Vector2 getPlayerStartPosition() {
        return new Vector2(
                5 * Jackal.BLOCK_WIDTH,
                25 * Jackal.BLOCK_HEIGHT
        );
    }
}
