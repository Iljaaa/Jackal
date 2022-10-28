package com.a530games.framework.helpers.cor;

import java.util.ArrayList;

public class Handler<T> {

    /**
     * Steps of handler
     */
    private final ArrayList<T> steps;

    /**
     * Selected inder
     */
    private int index;

    public Handler() {
        this.steps = new ArrayList<>();
        this.index = 0;
    }

    public void add(T step) {
        this.steps.add(step);
    }

    public T get(int index){
        return this.steps.get(index);
    }

    public boolean hasNext() {
        return this.index < (this.steps.size() - 1);
    }

    public T next() {
        this.index++;
        return this.steps.get(this.index);
    }

    public T current() {
        return this.steps.get(this.index);
    }

    public void setStep(int index) {
        this.index = index;
    }

    /*public BasicStep setFirstStep (BasicStep startStep){
        this.startStep = startStep;
        return this.startStep;
    }*/

    /*public void update(float deltaTime) {
        this.startStep.update(deltaTime);
    }*/
}
