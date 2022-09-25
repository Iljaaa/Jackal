package com.a530games.jackal.screens;

import android.graphics.Color;

import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.Screen;
import com.a530games.jackal.Assets;

public class LoadingLevelScreen extends Screen
{
    float timer = 0;

    boolean isAssetsIsLoaded;

    /**
     * Plaey hp on level start
     */
    int startPlayerHp;

    public LoadingLevelScreen(Game game, int playerStartHp) {
        super(game);
        this.isAssetsIsLoaded = false;

        this.startPlayerHp = playerStartHp;
    }

    @Override
    public void update(float deltaTime)
    {

        // loading map assets
        if (this.timer < 2 && !this.isAssetsIsLoaded)
        {
            Assets.mapSprite = this.game.getGraphics().newPixmap("images/map.png", Graphics.PixmapFormat.RGB565);

            this.isAssetsIsLoaded = true;
        }

        if (this.timer > 2)
        {
            // start loading
            GameScreen gs = new GameScreen(this.game, this.startPlayerHp);

            // test init map
            // gs.world.map.init(100, 100, this.game.getGraphics().getAssetManager(), gs.world.player);
            gs.world.map.init(30, 30, this.game.getGraphics().getAssetManager(), gs.world.player);

            // draw map
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
