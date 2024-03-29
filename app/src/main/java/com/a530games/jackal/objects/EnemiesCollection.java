package com.a530games.jackal.objects;

import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.objects.enemies.Enemy;

import java.util.ArrayList;
import java.util.ListIterator;

public class EnemiesCollection
{
    // enemies array
    private final ArrayList<Enemy> enemies;

    // max bullets size
    static final int MAX_ENEMIES_SIZE = 30;

    public EnemiesCollection()
    {
        this.enemies = new ArrayList<>(30);
    }

    public int size() {
        return this.enemies.size();
    }

    public Enemy get (int index){
        return this.enemies.get(index);
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
    public boolean isAnyEnemyIntersectWithOtherEnemy(Enemy enemy)
    {
        HitBox enemyHitbox = enemy.getHitBox();
        if (enemyHitbox == null) return false;

        // index of him self
        int inj = this.enemies.indexOf(enemy);

        // int len = EnemiesCollection.MAX_ENEMIES_SIZE;
        int len = this.enemies.size();

        for (int i = 0; i < len; i++)
        {
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

        return false;
    }

    /**
     * Check intersect hotbox and enemies
     */
    public boolean isHitboxIdIntersectWithEnemy(HitBox hitbox)
    {
        int len = this.enemies.size();

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
        }

        return false;
    }

    public void clearDeadEnemies()
    {
        ListIterator<Enemy> listIterator = this.enemies.listIterator();
        while (listIterator.hasNext()) {
            Enemy enemy = listIterator.next();
            if (enemy.isDead()) listIterator.remove();
        }

    }
}
