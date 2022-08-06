package com.a530games.jackal.objects;

import java.util.ArrayList;

public class PlayerBulletsCollection
{
    private ArrayList<Bullet> bullets;

    // max bullets size
    static final int MAX_BULLETS_SIZE = 20;

    public PlayerBulletsCollection() {
        this.bullets = new ArrayList<>(MAX_BULLETS_SIZE);
    }

    public int size() {
        return this.bullets.size();
    }

    public Bullet get (int index){
        return this.bullets.get(index);
    }

    /*public boolean add(Bullet bullet)
    {
        // find free bullet place
        int size = this.bullets.size();

        for (int i = 0; i < size; i++) {
            Bullet b = this.bullets.get(i);
            if (b.isOut()) {
                // this.bullets.set(i, bullet);
                b.reNew(bullet.getX(), bullet.getY());
                return true;
            }
        }

        if (size >= MAX_BULLETS_SIZE) return false;

        this.bullets.add(bullet);
        return true;
    }*/

    public Bullet getFreeBullet()
    {
        int size = this.bullets.size();

        for (int i = 0; i < size; i++) {
            Bullet b = this.bullets.get(i);
            if (b.isOut()) {
                // this.bullets.set(i, bullet);
                return b;
            }
        }

        if (size >= MAX_BULLETS_SIZE) return null;

        Bullet b = new Bullet(0, 0, 0);
        this.bullets.add(b);

        return b;
    }
}
