package com.a530games.jackal.objects;

import android.graphics.Rect;

import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;

abstract public class GameObject
{
    public HitBox hitBox;

    public Sprite sprite;

    // hit box rect in map position for draw
    private Rect hitBoxForDraw;

    public GameObject(Pixmap image)
    {
        this.sprite = new Sprite(image);

        // hitbox for draw
        this.hitBoxForDraw = new Rect();
    }

    public Sprite getSprite() {
        return this.sprite;
    }


    public Rect getScreenDrawHitbox (Map map)
    {

        this.hitBoxForDraw.left = map.screenLeftPotion(this.hitBox.left);
        // this.hitBoxForDraw.left = this.world.map.screenLeftPotion(this.hitBox.left);
        this.hitBoxForDraw.top = map.screenTopPotion(this.hitBox.top);
        // this.hitBoxForDraw.top = this.world.map.screenTopPotion(this.hitBox.top);
        this.hitBoxForDraw.right =  Math.round(this.hitBoxForDraw.left + this.hitBox.getWidth());
        this.hitBoxForDraw.bottom = Math.round(this.hitBoxForDraw.top + this.hitBox.getHeight());
        return this.hitBoxForDraw;
    }


}
