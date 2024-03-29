package com.a530games.framework;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<T>(maxSize);
    }

    public T newObject() {
        T object = null;
        if (this.freeObjects.size() == 0){
            object = this.factory.createObject();
        }
        else {
            object = this.freeObjects.remove(this.freeObjects.size() - 1);
        }

        return object;
    }

    public void free(T object) {
        if (freeObjects.size() < this.maxSize) this.freeObjects.add(object);
    }
}
