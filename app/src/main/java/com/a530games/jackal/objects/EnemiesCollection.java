package com.a530games.jackal.objects;

import com.a530games.jackal.map.Map;
import com.a530games.jackal.objects.enemies.Enemy;

import java.util.ArrayList;

public class EnemiesCollection
{
    // enemies array
    private final ArrayList<Enemy> enemies;

    public EnemiesCollection()
    {
        this.enemies = new ArrayList<>(10);
    }

    public int size() {
        return this.enemies.size();
    }

    public Enemy get (int index){
        return this.enemies.get(index);
    }

    public void add(Enemy it){
        this.enemies.add(it);
    }

    /**
     *
     */
    public boolean isAnyEnemyIntersectWith (Enemy vehicle)
    {
        int inj = this.enemies.indexOf(vehicle);

        for (int i = 0; i < this.size(); i++)
        {
            // ignore him self
            if (i == inj) {
                continue;
            }

            Enemy e = this.get(i);

            // check intersect
            if (Map.isIntersectsTwoRect(vehicle.getHitBox(), e.getHitBox())){
                return true;
            }
        }

        /*this.enemies.forEach(v -> {
        } );*/

        return false;
    }
}
