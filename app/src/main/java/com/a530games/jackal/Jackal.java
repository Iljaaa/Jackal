package com.a530games.jackal;

import com.a530games.framework.AndroidGame;
import com.a530games.framework.Screen;

public class Jackal extends AndroidGame
{

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}