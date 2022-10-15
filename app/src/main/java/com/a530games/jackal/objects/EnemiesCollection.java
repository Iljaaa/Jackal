package com.a530games.jackal.objects;

import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.objects.enemies.Enemy;

import java.util.ArrayList;

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

    public void remove(Enemy e) {
        this.enemies.remove(e);
    }

    /**
     *
     */
    public boolean isAnyEnemyIntersectWith (Enemy vehicle)
    {
        //
        int inj = this.enemies.indexOf(vehicle);
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

            // check intersect
            if (FloatRect.isIntersectsTwoRect(vehicle.getHitBox(), e.getHitBox())){
                return true;
            }
        }

        /*this.enemies.forEach(v -> {
        } );*/

        return false;
    }
}
