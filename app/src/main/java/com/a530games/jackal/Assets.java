package com.a530games.jackal;

import com.a530games.framework.Audio;
import com.a530games.framework.Graphics;
import com.a530games.framework.Music;
import com.a530games.framework.Pixmap;
import com.a530games.framework.Sound;

public class Assets {

    public static Pixmap bg;

    public static Pixmap player;
    public static Pixmap gun;

    public static Pixmap tank;

    public static Pixmap man;

    // player items
    public static Pixmap bullet;
    public static Pixmap bullet2;
    public static Pixmap playerFire;

    public static Pixmap mapSprite;

    // temp object must move on map sprite
    public static Pixmap rock;
    public static Pixmap bigStone;
    public static Pixmap bigPillar;
    public static Pixmap bush1;
    public static Pixmap bush2;

    public static Pixmap boom;

    public static Pixmap botLine;


    public static Sound fire;
    public static Sound playerHit;
    public static Sound playerBlow;

    public static Sound tankHit1;
    public static Sound tankHit2;
    public static Sound tankFire;

    public static Music music;

    private static boolean isPart1Loaded = false;
    private static boolean isPart2Loaded = false;

    public static void loadPart1(Graphics g, Audio a)
    {
        if (Assets.isPart1Loaded) return;

        Assets.tank = g.newPixmap("images/tank.png", Graphics.PixmapFormat.RGB565);
        Assets.player = g.newPixmap("images/player.png", Graphics.PixmapFormat.RGB565);
        Assets.bullet = g.newPixmap("images/bullet.png", Graphics.PixmapFormat.RGB565);
        Assets.bullet2 = g.newPixmap("images/bullet2.png", Graphics.PixmapFormat.RGB565);
        Assets.playerFire = g.newPixmap("images/player_fire.png", Graphics.PixmapFormat.RGB565);
        Assets.gun = g.newPixmap("images/gun.png", Graphics.PixmapFormat.RGB565);
        Assets.man = g.newPixmap("images/man.png", Graphics.PixmapFormat.RGB565);

        Assets.rock = g.newPixmap("images/rock.png", Graphics.PixmapFormat.RGB565);
        Assets.bigStone = g.newPixmap("images/big_stone.png", Graphics.PixmapFormat.RGB565);
        Assets.botLine = g.newPixmap("images/bot_line.png", Graphics.PixmapFormat.RGB565);
        Assets.bigPillar = g.newPixmap("images/big_pillar.png", Graphics.PixmapFormat.RGB565);
        Assets.bush1 = g.newPixmap("images/bush1.png", Graphics.PixmapFormat.RGB565);
        Assets.bush2 = g.newPixmap("images/bush2.png", Graphics.PixmapFormat.RGB565);

        Assets.boom = g.newPixmap("images/boom.png", Graphics.PixmapFormat.RGB565);

        Assets.fire = a.newSound("sound/fire.mp3");
        Assets.playerHit = a.newSound("sound/player_hit.wav");
        Assets.playerBlow = a.newSound("sound/player_blow.wav");


        // Assets.music = a.newMusic("music/jj.wav");

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
