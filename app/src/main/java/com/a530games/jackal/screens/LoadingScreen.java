package com.a530games.jackal.screens;

import android.graphics.Color;

import com.a530games.framework.Graphics;
import com.a530games.framework.Screen;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.levels.ArenaLevel;
import com.a530games.jackal.levels.Level;

public class LoadingScreen extends Screen
{
    float timer = 0;

    String loadingLevelCode;

    public LoadingScreen(Jackal game, String levelCode)
    {
        super(game);

        this.loadingLevelCode = levelCode;

        // todo: load only this this map sprite
        Assets.bg = this.game.getGraphics().newPixmap("images/bg.jpg", Graphics.PixmapFormat.RGB565);
    }

    @Override
    public void update(float deltaTime)
    {
        // пропускаем пару секунд для промо видел

        if (this.timer > 1) {
            Assets.loadPart1(this.game.getGraphics(), this.game.getAudio());
        }

        if (this.timer > 2) {
            Assets.loadPart2(this.game.getGraphics(), this.game.getAudio());
        }

        if (this.timer > 3) {
            Assets.loadPart3(this.game.getGraphics(), this.game.getAudio());
        }

        if (this.timer > 5) {
            // start loading
            this.game.setScreen(new LoadingLevelScreen(this.game, Jackal.getUerStartHp(), this.loadingLevelCode));
            return;
        }

        this.timer += deltaTime;
    }

    @Override
    public void present(float deltaTime)
    {
        this.game.getGraphics().drawLine(0, 0, this.game.getGraphics().getFrameBufferWidth(), this.game.getGraphics().getFrameBufferHeight(), Color.YELLOW);
        this.game.getGraphics().drawText("530 games", 100, 150, 100, Color.MAGENTA);
        this.game.getGraphics().drawPixmap(Assets.bg, 10, 10);
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
