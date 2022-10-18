package com.a530games.jackal.screens;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.Screen;
import com.a530games.jackal.Assets;
import com.a530games.jackal.levels.FirstLevel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
            // here load level sprite
            Assets.mapSprite = this.game.getGraphics().newPixmap("images/map.png", Graphics.PixmapFormat.RGB565);

            this.isAssetsIsLoaded = true;
        }

        if (this.timer > 2)
        {
            this.loadLevel();
            return;
        }

        this.timer += deltaTime;
    }

    private void loadLevel()
    {
        // start loading
        GameScreen gs = new GameScreen(this.game, this.startPlayerHp);


        // test init map
        // gs.world.map.init(100, 100, this.game.getGraphics().getAssetManager(), gs.world.player);
        // gs.world.map.init(new FirstLevel(), this.game.getGraphics().getAssetManager(), gs.world.player);
        gs.world.map.init(
                new FirstLevel(),
                this.game.getGraphics(),
                gs.world.player,
                gs.world.dropPad,
                gs.mapScreenWidthInPixels,
                gs.mapScreenHeightInPixels
        );

        // pre draw map
        gs.world.map.drawBackground();

        // save map to file
        // this.saveMapTofile(gs);

        this.game.setScreen(gs);
    }

    /**
     * Save map image to file
     */
    private void saveMapTofile(GameScreen gs)
    {
        try {
            OutputStream osw = this.game.getFileIO().writeFile("map22.jpg");
            // Bitmap pictureBitmap = getImageBitmap(myurl); // obtaining the Bitmap
            gs.world.map.drawBitmap.compress(Bitmap.CompressFormat.JPEG, 85, osw); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            osw.flush();
            osw.close();

            FileOutputStream fs = new FileOutputStream("/storage/emulated/0/Android/data/com.a530games.jackal/files/Pictures/map3.jpg");
            gs.world.map.drawBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fs); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fs.flush(); // Not really required
            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
