package com.a530games.jackal;

import com.a530games.framework.Audio;
import com.a530games.framework.Graphics;
import com.a530games.framework.Pixmap;
import com.a530games.framework.Sound;

public class Assets {

    public static Pixmap bg;

    public static Pixmap player;
    public static Pixmap gun;

    public static Pixmap tank;

    public static Pixmap man;

    public static Pixmap bullet;

    public static Pixmap mapSprite;

    public static Pixmap rock;

    public static Sound fire;
    public static Sound playerHit;
    public static Sound playerBlow;

    public static Sound tankHit1;
    public static Sound tankHit2;
    public static Sound tankFire;

    private static boolean isPart1Loaded = false;
    private static boolean isPart2Loaded = false;

    public static void loadPart1(Graphics g, Audio a)
    {
        if (Assets.isPart1Loaded) return;

        Assets.tank = g.newPixmap("images/tank.png", Graphics.PixmapFormat.RGB565);
        Assets.player = g.newPixmap("images/player.png", Graphics.PixmapFormat.RGB565);
        Assets.bullet = g.newPixmap("images/bullet.png", Graphics.PixmapFormat.RGB565);
        Assets.rock = g.newPixmap("images/rock.png", Graphics.PixmapFormat.RGB565);
        Assets.gun = g.newPixmap("images/gun.png", Graphics.PixmapFormat.RGB565);
        Assets.man = g.newPixmap("images/man.png", Graphics.PixmapFormat.RGB565);

        Assets.fire = a.newSound("sound/fire.mp3");
        Assets.playerHit = a.newSound("sound/player_hit.wav");
        Assets.playerBlow = a.newSound("sound/player_blow.wav");

        Assets.isPart1Loaded = true;
    }

    public static void loadPart2(Graphics g, Audio a)
    {
        if (Assets.isPart2Loaded) return;

        Assets.tankHit1 = a.newSound("sound/tank_hit_1.wav");
        Assets.tankHit2 = a.newSound("sound/tank_hit_2.wav");
        Assets.tankFire = a.newSound("sound/tank_fire.wav");

        Assets.isPart2Loaded = true;
    }

}
