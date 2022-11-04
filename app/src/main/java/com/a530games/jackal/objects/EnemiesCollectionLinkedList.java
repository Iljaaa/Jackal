package com.a530games.jackal.objects;

import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.objects.enemies.Enemy;

import java.util.LinkedList;
import java.util.ListIterator;

public class EnemiesCollectionLinkedList
{
    // enemies array
    private final LinkedList<Enemy> enemies;

    // max bullets size
    static final int MAX_ENEMIES_SIZE = 30;

    public EnemiesCollectionLinkedList()
    {
        this.enemies = new LinkedList<>();
    }

    public int size() {
        return this.enemies.size();
    }

    public void add(Enemy it){
        this.enemies.add(it);
        // this.enemies.re
    }

    public int indexOf(Enemy e) {
        return this.enemies.indexOf(e);
    }

    /**
     * Almost isHitboxIdIntersectWithEnemy but exclude him self
     */
    public boolean isIntersectWithEnemies(Enemy enemy)
    {
        HitBox enemyHitbox = enemy.getHitBox();
        if (enemyHitbox == null) return false;

        for (Enemy e : this.enemies)
        {
            // check himself
            if (e.equals(enemy)) {
                continue;
            }

            if (e.isDead()) continue;

            HitBox hitBox = e.getHitBox();
            if (hitBox == null) continue;

            if (enemyHitbox.isIntersectsWithHitbox(hitBox)) {
                return true;
            }
        }

        return false;

        // index of him self
        /*int inj = this.enemies.indexOf(enemy);

        // int len = EnemiesCollection.MAX_ENEMIES_SIZE;
        int len = this.enemies.size();

        for (int i = 0; i < len; i++)
        {
            enemy.equals(enemy)
            // ignore him self
            if (i == inj) {
                continue;
            }

            Enemy e = this.get(i);
            if (e == null) continue;

            HitBox hitBox = e.getHitBox();
            if (hitBox == null) continue;

            // check intersect
            // if (FloatRect.isIntersectsTwoRectF(vehicle.getHitBox(), enemyHitbox)){
            if (enemyHitbox.isIntersectsWithHitbox(hitBox)){
                return true;
            }
        }

        return false;*/
    }

    /**
     * Check intersect hotbox and enemies
     */
    public boolean isPlayerHitboxIntersectEnemy(HitBox hitbox)
    {
        for (Enemy e : this.enemies)
        {
            if (e.isDead()) continue;

            HitBox enemyHitbox = e.getHitBox();
            if (enemyHitbox == null) continue;

            // check intersect
            if (enemyHitbox.isIntersectsWithHitbox(hitbox)){
                return true;
            }
        }

        /*int len = this.enemies.size();

        for (int i = 0; i < len; i++)
        {
            Enemy e = this.get(i);
            if (e == null) continue;

            HitBox enemyHitbox = e.getHitBox();
            if (enemyHitbox == null) continue;

            // check intersect
            if (enemyHitbox.isIntersectsWithHitbox(hitbox)){
                return true;
            }
        }*/

        return false;
    }

    /*public void clearDeadEnemies()
    {
        ListIterator<Enemy> listIterator = this.enemies.listIterator();
        while (listIterator.hasNext()) {
            Enemy enemy = listIterator.next();
            if (enemy.isDead()) listIterator.remove();
        }

    }*/


    public ListIterator<Enemy> listIterator() {
        return this.enemies.listIterator();
    }
}
