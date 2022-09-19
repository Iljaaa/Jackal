package com.a530games.jackal;

public class Settings
{
    public static final int INPUT_TYPE_JOYPAD = 0;
    public static final int INPUT_TYPE_ONSCREEN_JOYPAD = 1;

    /**
     * Master input type
     */
    public static int inputType = Settings.INPUT_TYPE_JOYPAD;


    public static final int GAME_TYPE_CASUAL = 0;
    public static final int GAME_TYPE_ARCADE = 1;

    /**
     * Game type
     * looks like it's temporary
     */
    public static int gameType = Settings.GAME_TYPE_CASUAL;

    /**
     * Sound flag
     */
    public static boolean soundEnabled = true;

    /**
     * Play music
     */
    public static boolean playMusic = false;
}
