package com.a530games.framework.helpers.cor;

import java.util.ArrayList;

public class Handler {

    /**
     * Steps of handler
     */
    private final ArrayList<Step> steps;

    private int stepIndex;

    public Handler() {
        this.steps = new ArrayList<>();
        this.stepIndex = 0;
    }

    public void add(Step step) {
        // step.setHandler(this);
        this.steps.add(step);
    }

    public Step get(int index){
        return this.steps.get(index);
    }

    public void next() {
        this.stepIndex++;

        // todo: make circle
    }

    public Step getCurrentStep() {
        return this.steps.get(this.stepIndex);
    }

    public boolean isOver(){
        return this.stepIndex >= this.steps.size();
    }

    /*public BasicStep setFirstStep (BasicStep startStep){
        this.startStep = startStep;
        return this.startStep;
    }*/

    /*public void update(float deltaTime) {
        this.startStep.update(deltaTime);
    }*/
}
