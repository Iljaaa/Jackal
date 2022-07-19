package com.a530games.framework;

public interface Game
{
    // обертка вокруг ввода
    public Input getInput();

    public FileIO getFileIO();
    public Graphics getGraphics();
    public Audio getAudio();

    // получаем штуку из рендера для того что бы получить fps
    public AndroidFastRenderView getRenderView();

    // ориентацию экрана
    public int getScreenOrientation ();

    // а не горизонтальная ли ориентация ли, случаем
    public boolean isLandscape();

    public void setScreen(Screen screen);
    public Screen getCurrentScreen();
    public Screen getStartScreen();
}
