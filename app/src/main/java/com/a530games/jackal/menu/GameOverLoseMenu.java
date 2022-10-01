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
                GameOverLoseMenu.GLM_RESTART,
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 50)
        ));

        this.addItem(new MenuItem(
                "BACK TO MAIN MENU",
                GameOverLoseMenu.GLM_BACK_TO_MAIN_MENU,
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y + 50),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 100)
        ));

        /*this.addItem(new MenuItem(
                "OTHER RESTART 1",
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y + 100),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 150)
        ));
        this.addItem(new MenuItem(
                "OTHER RESTART 2",
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y + 150),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 200)
        ));
        this.addItem(new MenuItem(
                "OTHER RESTART 3",
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y + 200),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 250)
        ));*/
    }
}
