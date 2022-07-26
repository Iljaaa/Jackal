package com.a530games.jackal;

import android.graphics.Color;

import com.a530games.framework.Graphics;
import com.a530games.framework.Screen;
import com.a530games.jackal.screens.GameScreen;

public class LoadingScreen extends Screen
{
    float timer = 0;

    public LoadingScreen(Jackal game) {
        super(game);
        Assets.bg = this.game.getGraphics().newPixmap("images/bg.jpg", Graphics.PixmapFormat.RGB565);
    }

    @Override
    public void update(float deltaTime)
    {
        // пропускаем пару секунд для промо видел

        if (this.timer > 2) {
            Assets.loadPart1(this.game.getGraphics(), this.game.getAudio());
        }

        if (this.timer > 5) {
            // start loading
            this.game.setScreen(new GameScreen(this.game));
            return;
        }

        this.timer += deltaTime;
    }

    @Override
    public void present(float deltaTime) {
        this.game.getGraphics().drawLine(0, 0, this.game.getGraphics().getWidth(), this.game.getGraphics().getHeight(), Color.YELLOW);
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
