package com.a530games.jackal;

import android.content.res.AssetManager;

import com.a530games.framework.AndroidPixmap;
import com.a530games.framework.AndroidSound;
import com.a530games.framework.Audio;
import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;
import com.a530games.framework.Sound;

public class Assets {

    public static Pixmap bg;

    public static Pixmap player;
    public static Pixmap tank;
    public static Pixmap bullet;

    public static Pixmap rock;

    public static Sound fire;

    public static void loadPart1(Graphics g, Audio a)
    {
        Assets.tank = g.newPixmap("images/tank.png", Graphics.PixmapFormat.RGB565);
        Assets.player = g.newPixmap("images/player.png", Graphics.PixmapFormat.RGB565);
        Assets.bullet = g.newPixmap("images/bullet.png", Graphics.PixmapFormat.RGB565);
        Assets.rock = g.newPixmap("images/rock.png", Graphics.PixmapFormat.RGB565);

        Assets.fire = a.newSound("sound/fire.mp3");
    }

}
