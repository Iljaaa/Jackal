package com.a530games.jackal.menu;

import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Controller;
import com.a530games.jackal.Sprite;

public class GameOverLoseMenu extends BasicMenu
{

    public GameOverLoseMenu(int x, int y)
    {
        super(x, y);

        this.addItem(new MenuItem(
                "RESTART",
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 50)
        ));

        this.addItem(new MenuItem(
                "OTHER RESTART",
                (int) Math.ceil(this.position.x),
                (int) Math.ceil(this.position.y + 50),
                (int) Math.ceil(this.position.x + 400),
                (int) Math.ceil(this.position.y + 100)
        ));
        this.addItem(new MenuItem(
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
        ));
    }




    private void presentString() {

    }
}
