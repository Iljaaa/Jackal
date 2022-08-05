package com.a530games.jackal.objects;

import com.a530games.jackal.map.Map;

import java.util.ArrayList;

public class EnemiesCollection
{
    // enemies array
    private ArrayList<Vehicle> enemies;

    public EnemiesCollection()
    {
        this.enemies = new ArrayList<>(10);
    }

    public int size() {
        return this.enemies.size();
    }

    public Vehicle get (int index){
        return this.enemies.get(index);
    }

    public void add(Vehicle it){
        this.enemies.add(it);
    }

    /**
     *
     */
    public boolean isAnyEnemyIntersectWith (Vehicle vehicle)
    {
        int inj = this.enemies.indexOf(vehicle);
        int enemiesSize = this.size();

        for (int i = 0; i < enemiesSize; i++) {
            if (i == inj) {
                continue;
            }

            Vehicle e = this.get(i);

            // check intersect
            if (Map.isIntersectsTwoRect(vehicle.hitBox, e.hitBox)){
                return true;
            }
        }

        /*this.enemies.forEach(v -> {
        } );*/

        return false;
    }
}
