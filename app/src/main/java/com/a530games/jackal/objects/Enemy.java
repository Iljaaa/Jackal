package com.a530games.jackal.objects;

import com.a530games.framework.helpers.HitBox;

public interface Enemy
{
    /**
     * Object hit box
     */
    HitBox getHitBox();

    void update(float deltaTime, Enemy player);

}
