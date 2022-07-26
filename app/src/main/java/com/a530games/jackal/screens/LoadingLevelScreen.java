package com.a530games.jackal.screens;

import android.graphics.Color;

import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.Screen;
import com.a530games.jackal.Assets;

public class LoadingLevelScreen extends Screen
{
    float timer = 0;

    public LoadingLevelScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime)
    {
        if (this.timer > 2)
        {
            // start loading
            GameScreen gs = new GameScreen(this.game);

            // test init map
            gs.world.map.init(10, 10, this.game.getGraphics().getAssetManager());

            gs.world.map.draw();

            this.game.setScreen(gs);
            return;
        }

        this.timer += deltaTime;
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clear(Color.BLACK);
        this.game.getGraphics().drawText("loading level 1", 100, 150, 100, Color.MAGENTA);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
