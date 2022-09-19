package com.a530games.jackal;

import com.a530games.framework.AndroidGame;
import com.a530games.framework.Screen;
import com.a530games.jackal.screens.LoadingScreen;

import java.util.Random;

public class Jackal extends AndroidGame
{
    private static Random random = null;

    private static Controller controller;

    public static Random getRandom(){
        if (Jackal.random == null) Jackal.random = new Random();
        return Jackal.random;
    }

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }

    public static void setController(Controller controller) {
        Jackal.controller = controller;
    }

    public static Controller getController() {
        return Jackal.controller;
    }
}
