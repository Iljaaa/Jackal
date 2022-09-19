package com.a530games.framework;

public interface Game
{
    /**
     * Input wrap
     * @return Input
     */
    Input getInput();

    FileIO getFileIO();

    /**
     * Graphics object
     */
    Graphics getGraphics();

    /**
     * Audio object
     */
    Audio getAudio();

    // получаем штуку из рендера для того что бы получить fps
    AndroidFastRenderView getRenderView();

    // screen orientation
    // int getScreenOrientation ();

    // а не горизонтальная ли ориентация ли, случаем
    boolean isLandscape();

    void setScreen(Screen screen);
    Screen getCurrentScreen();
    Screen getStartScreen();
}
