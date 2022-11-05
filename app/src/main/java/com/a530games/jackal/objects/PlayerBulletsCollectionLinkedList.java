package com.a530games.jackal.objects;

import java.util.LinkedList;
import java.util.ListIterator;

public class PlayerBulletsCollectionLinkedList
{
    private final LinkedList<Bullet> bullets;

    // max bullets size
    static final int MAX_BULLETS_SIZE = 20;

    public PlayerBulletsCollectionLinkedList()
    {
        this.bullets = new LinkedList<>();
    }

    /*public int size() {
        return this.bullets.size();
    }*/

    /**
     */
    public Bullet getFreeBullet()
    {
        if (this.bullets.size() >= MAX_BULLETS_SIZE) {
            return null;
        }

        Bullet b = new Bullet(0, 0, 0, 0);
        this.bullets.add(b);

        return b;
    }

    public ListIterator<Bullet> listIterator() {
        return this.bullets.listIterator();
    }
}
