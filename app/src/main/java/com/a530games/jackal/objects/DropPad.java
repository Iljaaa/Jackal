package com.a530games.jackal.objects;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Cell;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.enemies.EnemyFireEventHandler;

public class DropPad implements Enemy
{
    private Vector2 position;

    private Vector2 dropPosition;

    boolean isDead = false;

    public DropPad(Player p) {
        this.position = new Vector2();
        this.dropPosition = new Vector2();
    }

    protected void finalize() {
        Log.d("Dropad", "finalizew");
    }

    public void moveToStart(Cell playerDropCell)
    {
        this.dropPosition.set(playerDropCell.center.x, playerDropCell.center.y);

        this.position.x = playerDropCell.center.x;
        this.position.y = playerDropCell.center.y + 1000;
    }

    @Override
    public HitBox getHitBox() {
        return null;
    }

    @Override
    public Rect getScreenDrawHitbox(Map map) {
        return null;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public boolean isDead() {
        return this.isDead;
    }

    @Override
    public void update(float deltaTime, World world)
    {
        if (this.position.y > this.dropPosition.y) {
            this.position.y -= (deltaTime * 100);
            return;
        }

        this.position.y = this.dropPosition.y;
        this.isDead = true;
    }

    @Override
    public void present(Graphics g, World world)
    {

        g.drawCircle(
                world.map.screenLeftPotion(this.position.x),
                world.map.screenTopPotion(this.position.y),
                100, Color.RED);

    }

    @Override
    public boolean hit(int damage) {
        return false;
    }

    @Override
    public void setFireEventHandler(EnemyFireEventHandler fireEventHandler) {

    }
}
