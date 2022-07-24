package com.a530games.jackal.objects;

import com.a530games.framework.Pixmap;
import com.a530games.jackal.Sprite;

abstract public class GameObject
{

    public Sprite sprite;

    public GameObject(Pixmap image) {
        this.sprite = new Sprite(image);
    }
}
