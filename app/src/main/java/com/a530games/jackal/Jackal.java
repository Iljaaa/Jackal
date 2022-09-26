package com.a530games.jackal;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.a530games.framework.AndroidGame;
import com.a530games.framework.Screen;
import com.a530games.jackal.screens.LoadingScreen;

import java.util.Random;

public class Jackal extends AndroidGame
{
    private static Random random = null;

    private static Controller controller;

    /**
     * mp comtinues count
     */
    private static int continues = 3;


    public static Random getRandom(){
        if (Jackal.random == null) Jackal.random = new Random();
        return Jackal.random;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        // не подходящее мето для этого кода
        Jackal.setController(new Controller());

        super.onCreate(savedInstanceState);
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

    public static int getUerStartHp(){
        return Jackal.continues;
    }

    public static int pickContinue()
    {
        if (Jackal.continues > 1) Jackal.continues--;

        return Jackal.continues;
    }
}
