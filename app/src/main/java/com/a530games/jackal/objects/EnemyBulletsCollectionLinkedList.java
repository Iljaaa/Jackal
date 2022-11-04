package com.a530games.jackal.objects;

import java.util.LinkedList;
import java.util.ListIterator;

public class EnemyBulletsCollectionLinkedList
{
    private final LinkedList<Bullet> bullets;

    // max bullets size
    static final int MAX_BULLETS_SIZE = 50;

    public EnemyBulletsCollectionLinkedList()
    {
        this.bullets = new LinkedList<>();
    }

    /*public int size() {
        return this.bullets.size();
    }*/

    /*public boolean add(Object item)
    {
        if (this.size() >= MAX_BULLETS_SIZE) {
            return false;
        }

        this.bullets.add((Bullet) item);
        return true;
    }*/

    /*public Bullet get (int index) {
        return this.bullets.get(index);
    }*/

    public Bullet getFreeElement()
    {
        if (this.bullets.size() >= MAX_BULLETS_SIZE) {
            return null;
        }

        Bullet b = new Bullet(0, 0, 0);
        this.bullets.add(b);

        return b;

        /*ListIterator<Bullet> listIterator = this.bullets.listIterator();

        Bullet found = null;
        while (listIterator.hasNext())
        {
            Bullet b = listIterator.next();

            // delete out bullet
            if (b.isOut()){
                found = b;
                break;
            }
        }

        return found;*/

    }

    public ListIterator<Bullet> listIterator() {
        return this.bullets.listIterator();
    }
}
