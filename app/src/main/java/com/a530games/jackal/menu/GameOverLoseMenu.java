package com.a530games.jackal.menu;

public class GameOverLoseMenu extends BasicMenu
{
    public static final String GLM_RESTART = "GLM_RESTART";
    public static final String GLM_BACK_TO_MAIN_MENU = "GLM_BACK_TO_MAIN_MENU";

    public GameOverLoseMenu(int x, int y)
    {
        super(x, y);

        // todo: move strings to constants
        this.addItem(new MenuItem(
                "RESTART",
                GameOverLoseMenu.GLM_RESTART
        ));

        this.addItem(new MenuItem(
                "BACK TO MAIN MENU",
                GameOverLoseMenu.GLM_BACK_TO_MAIN_MENU
        ));

    }
}
