package com.a530games.jackal.menu;

public class PauseMenu extends BasicMenu
{
    public static final String PAUSE_MENU_CONTINUE = "PAUSE_MENU_CONTINUE";
    public static final String PAUSE_MENU_RESTART = "PAUSE_MENU_RESTART";
    public static final String PAUSE_MENU_OPTIONS = "PAUSE_MENU_OPTIONS";
    public static final String PAUSE_MENU_SAVE_MAP = "PAUSE_MENU_SAVE_MAP";
    public static final String PAUSE_MENU_BACK_TO_MAIN_MENU = "PAUSE_MENU_BACK_TO_MAIN_MENU";

    public PauseMenu(int x, int y)
    {
        super(x, y);

        // todo: move strings to constants
        this.addItem(new MenuItem(
                "CONTINUE",
                PauseMenu.PAUSE_MENU_CONTINUE
        ));

        this.addItem(new MenuItem(
                "RESTART",
                PauseMenu.PAUSE_MENU_RESTART
        ));

        /*this.addItem(new MenuItem(
                "OPTIONS",
                PauseMenu.PAUSE_MENU_OPTIONS
        ));

        this.addItem(new MenuItem(
                "SAVE MAP (DO NOT USE THIS)",
                PauseMenu.PAUSE_MENU_SAVE_MAP
        ));

        this.addItem(new MenuItem(
                "BACK TO MAIN MENU",
                PauseMenu.PAUSE_MENU_BACK_TO_MAIN_MENU
        ));*/
    }


    public void reset(){
    }
}
