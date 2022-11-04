package com.a530games.jackal.screens;

import android.graphics.Color;
import android.graphics.Paint;

import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.Screen;
import com.a530games.jackal.Assets;
import com.a530games.jackal.levels.ArenaLevel;
import com.a530games.jackal.levels.FirstLevel;
import com.a530games.jackal.levels.Level;

public class LoadingLevelScreen extends Screen
{
    /**
     * Emulate loading timer
     */
    private float timer = 2f;

    /**
     * Summry loading time
     */
    private final float sumLoadTime = 2f;

    boolean isAssetsIsLoaded;

    /**
     * Plaey hp on level start
     */
    int startPlayerHp;

    /**
     * Loading level
     */
    Level level;

    /**
     * Screen titles paint
     */
    Paint titlePaint, subTitlePaint, progressbarPaint, progressbarRectPaint;

    /**
     * Half of progressbar width
     */
    private final int progressbarHalfWidth = 200;

    /**
     * Current loading progress
     */
    private int _currentProgressbarWidth = 0;

    public LoadingLevelScreen(Game game, int playerStartHp, String level)
    {
        super(game);
        this.isAssetsIsLoaded = false;

        this.startPlayerHp = playerStartHp;

        this.level = this.factory(level);

        this.titlePaint = new Paint();
        this.titlePaint.setColor(Color.WHITE);
        this.titlePaint.setTextAlign(Paint.Align.CENTER);
        this.titlePaint.setTextSize(150);

        this.subTitlePaint = new Paint();
        this.subTitlePaint.setColor(Color.WHITE);
        this.subTitlePaint.setTextAlign(Paint.Align.CENTER);
        this.subTitlePaint.setTextSize(30);

        this.progressbarRectPaint = new Paint();
        this.progressbarRectPaint.setColor(Color.WHITE);
        this.progressbarRectPaint.setStyle(Paint.Style.STROKE);
        this.progressbarRectPaint.setStrokeWidth(2);

        this.progressbarPaint = new Paint();
        this.progressbarPaint.setColor(Color.WHITE);
        this.progressbarPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private Level factory(String levelCode)
    {
        switch (levelCode) {
            case "first": return new FirstLevel();
            case "arena": return new ArenaLevel();
            default: return  null;
        }
    }

    @Override
    public void update(float deltaTime)
    {
        this.timer -= deltaTime;

        // loading map assets
        if (this.timer > 0 && !this.isAssetsIsLoaded)
        {
            // here load level sprite
            Assets.mapSprite = this.game.getGraphics().newPixmap("images/map.png", Graphics.PixmapFormat.RGB565);

            // this.isAssetsIsLoaded = true;

            float percent = 1 - (this.timer / this.sumLoadTime);
            this._currentProgressbarWidth =  (int) (this.progressbarHalfWidth * 2 * percent);

            return;
        }

        // load finish goto level
        this.goToLevel();
    }

    private void goToLevel()
    {
        // start loading
        GameScreen gs = new GameScreen(this.game, this.startPlayerHp);

        // test init map
        // gs.world.map.init(100, 100, this.game.getGraphics().getAssetManager(), gs.world.player);
        // gs.world.map.init(new FirstLevel(), this.game.getGraphics().getAssetManager(), gs.world.player);
        /*gs.world.initByLevel(
                new FirstLevel(),
                this.game.getGraphics(),
                gs.world.player,
                gs.world.dropPad,
                gs.mapScreenWidthInPixels,
                gs.mapScreenHeightInPixels
        );*/

        gs.initByLevel(this.level); //,
                // this.game.getGraphics(),
                // gs.mapScreenWidthInPixels,
                // gs.mapScreenHeightInPixels

        // pre draw map
        gs.world.map.drawBackground();

        // save map to file
        // this.saveMapTofile(gs);

        this.game.setScreen(gs);
    }

    /**
     * Save map image to file

    private void saveMapTofile(GameScreen gs)
    {
        try {
            // String.format("", )


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

    }*/

    @Override
    public void present(float deltaTime)
    {
        Graphics g = game.getGraphics();
        int screenCenterX = (int) Math.ceil(g.getFrameBufferWidth() * 0.5);

        g.clear(Color.BLACK);
        g.drawText(this.level.getName(), screenCenterX, 250, this.titlePaint);
        g.drawText("loading level", screenCenterX, 100, this.subTitlePaint);

        g.drawRect(
                screenCenterX - this.progressbarHalfWidth,
                500,
                this.progressbarHalfWidth * 2,
                50,
                this.progressbarRectPaint
        );

        g.drawRect(
                screenCenterX - this.progressbarHalfWidth,
                500,
                this._currentProgressbarWidth,
                50,
                this.progressbarPaint
        );
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
