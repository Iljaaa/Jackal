package com.a530games.jackal.screens;

import android.graphics.Color;
import android.graphics.Paint;

import com.a530games.framework.Graphics;
import com.a530games.framework.Screen;
import com.a530games.framework.helpers.cor.Handler;
import com.a530games.framework.helpers.cor.Step;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.levels.ArenaLevel;
import com.a530games.jackal.levels.Level;

import java.util.function.Function;

public class LoadingScreen extends Screen
{
    float timer = 0;

    String loadingLevelCode;

    public float titlePosition = 200;

    Paint titlePaint;

    private boolean part1Loaded = false;
    private boolean part2Loaded = false;
    private boolean part3Loaded = false;

    public LoadingScreen(Jackal game, String levelCode)
    {
        super(game);

        this.loadingLevelCode = levelCode;

        this.titlePaint = new Paint();
        this.titlePaint.setColor(Color.WHITE);
        this.titlePaint.setTextAlign(Paint.Align.CENTER);
        this.titlePaint.setTextSize(100);
    }

    @Override
    public void update(float deltaTime)
    {
        this.titlePosition += 2 * deltaTime;
        // if (this.titlePosition > 200) this.titlePosition = 200;

        if (this.timer > 1 && !this.part1Loaded) {
            this.loadingPart1();
        }

        if (this.timer > 2 && !this.part2Loaded) {
            this.loadingPart2();
        }

        if (this.timer > 3 && !this.part3Loaded) {
            this.loadingPart3();
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
        Graphics g = this.game.getGraphics();

        // g.clear(Color.BLACK);

        int screenCenterX = (int) Math.ceil(g.getFrameBufferWidth() * 0.5);
        g.drawText("530 games", screenCenterX, (int) this.titlePosition, this.titlePaint);
        // this.game.getGraphics().drawPixmap(Assets.bg, 10, 10);
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

    private void loadingPart1 ()
    {
        Assets.loadPart1(this.game.getGraphics(), this.game.getAudio());
        this.part1Loaded = true;
    }

    private void loadingPart2 ()
    {
        Assets.loadPart2(this.game.getGraphics(), this.game.getAudio());
        this.part2Loaded = true;
    }

    private void loadingPart3 ()
    {
        Assets.loadPart3(this.game.getGraphics(), this.game.getAudio());
        this.part3Loaded = true;
    }
}
