package com.a530games.jackal.menu;

public class PauseMenu extends BasicMenu
{
    public static final String PAUSE_MENU_CONTINUE = "PAUSE_MENU_CONTINUE";
    public static final String PAUSE_MENU_RESTART = "PAUSE_MENU_RESTART";
    public static final String PAUSE_MENU_BACK_TO_MAIN_MENU = "PAUSE_MENU_BACK_TO_MAIN_MENU";

    public PauseMenu(int x, int y)
    {
        super(x, y);

        // todo: move strings to constants
        this.addItem(new MenuItem(
                "CONTINUE",
                PauseMenu.PAUSE_MENU_CONTINUE,
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 50)
        ));

        this.addItem(new MenuItem(
                "RESTART",
                PauseMenu.PAUSE_MENU_RESTART,
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y + 50),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 100)
        ));

        this.addItem(new MenuItem(
                "BACK TO MAIN MENU",
                PauseMenu.PAUSE_MENU_BACK_TO_MAIN_MENU,
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y + 100),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 150)
        ));


    }
}
