package com.a530games.jackal.objects;

import android.graphics.Color;
import android.graphics.Paint;

import com.a530games.framework.Graphics;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.math.Circle;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Controller;

public class ControllerPresenter
{

    Controller controller;

    // display controller
    Vector2 controllerLeftButtonsPosition;
    Vector2 controllerRightButtonsPosition;

    // input left buttons
    public Circle topButton, rightButton, downButton, leftButton;

    // input right button
    public Circle rightAButton, rightBButton;

    // input sticks
    public Circle leftStickCircle, rightStickCircle;

    Paint circleButtonPaint, activeCircleButtonPaint;

    public ControllerPresenter() 
    {
        this.controllerLeftButtonsPosition = new Vector2(150, 500);
        this.controllerRightButtonsPosition = new Vector2(650, 500);

        this.topButton = new Circle(this.controllerLeftButtonsPosition.x, this.controllerLeftButtonsPosition.y - 75, 50); // top
        this.rightButton = new Circle(this.controllerLeftButtonsPosition.x + 75, this.controllerLeftButtonsPosition.y, 50); // right
        this.downButton =  new Circle(this.controllerLeftButtonsPosition.x, this.controllerLeftButtonsPosition.y + 75, 50); // down
        this.leftButton =  new Circle(this.controllerLeftButtonsPosition.x - 75, this.controllerLeftButtonsPosition.y, 50); // left

        this.rightAButton =  new Circle(this.controllerRightButtonsPosition.x - 60, this.controllerRightButtonsPosition.y + 10, 50); // A
        this.rightBButton = new Circle(this.controllerRightButtonsPosition.x + 60, this.controllerRightButtonsPosition.y - 10, 50); // B

        this.leftStickCircle = new Circle(this.controllerLeftButtonsPosition.x, this.controllerLeftButtonsPosition.y, 100);
        this.rightStickCircle = new Circle(this.controllerRightButtonsPosition.x, this.controllerRightButtonsPosition.y, 100);

        this.circleButtonPaint = new Paint();
        this.circleButtonPaint.setStyle(Paint.Style.STROKE);
        this.circleButtonPaint.setStrokeWidth(2);
        this.circleButtonPaint.setColor(Color.GREEN);

        this.activeCircleButtonPaint = new Paint();
        this.activeCircleButtonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.activeCircleButtonPaint.setStrokeWidth(2);
        this.activeCircleButtonPaint.setColor(Color.GREEN);
    }

    public void bindController (Controller c) {
        this.controller = c;
    }
    
    public void update(TouchEventsCollection touchEvents)
    {
        /*int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i); //.get(i);
            if (event == null) continue;

            // обработка паузы
            if(Input.TouchEvent.TOUCH_DOWN != event.type) continue;

            //
            if (this.topButton.isPointInside(event.x, event.y)) {
                this.world.player.move(0, -1, deltaTime, this.world);
            }
            if (this.rightButton.isPointInside(event.x, event.y)) {
                this.world.player.move(1, 0, deltaTime, this.world);
            }
            if (this.downButton.isPointInside(event.x, event.y)) {
                this.world.player.move(0, 1, deltaTime, this.world);
            }
            if (this.leftButton.isPointInside(event.x, event.y)) {
                this.world.player.move(-1, 0, deltaTime, this.world);
            }
            if (this.rightAButton.isPointInside(event.x, event.y)) {
                if (this.world.playerFire()){
                    if(Settings.soundEnabled) Assets.fire.play(1);
                }
            }
            if (this.rightBButton.isPointInside(event.x, event.y)) {
                if (this.world.playerFire()){
                    if(Settings.soundEnabled) Assets.fire.play(1);
                }
            }

        }*/

    }

    public void draw(Graphics g) 
    {
        // this.drawLeftControllerLikeButtons(g);
        this.drawLeftStickAsStick(g);

        // this.drawRightControllerLikeButtons(g);
        this.drawRightStickAsStick(g);
    }

    private void drawLeftControllerLikeButtons(Graphics g)
    {
        Vector2 leftStickDirection = this.controller.getLeftStickDirection();

        this.drawCircleButton(g, this.topButton, (leftStickDirection.y < 0)); // top
        this.drawCircleButton(g, this.rightButton, (leftStickDirection.x > 0)); // right
        this.drawCircleButton(g, this.downButton, (leftStickDirection.y > 0)); // bottom
        this.drawCircleButton(g, this.leftButton, (leftStickDirection.x < 0)); // left

        // g.drawCircle((int) Math.ceil(this.topButton.center.x), (int) Math.ceil(this.topButton.center.y), (int) Math.ceil(this.topButton.radius), Color.GREEN); // top
        // g.drawCircle((int) Math.ceil(this.rightButton.center.x), (int) Math.ceil(this.rightButton.center.y), (int) Math.ceil(this.rightButton.radius), Color.GREEN); // right
        // g.drawCircle((int) Math.ceil(this.downButton.center.x), (int) Math.ceil(this.downButton.center.y), (int) Math.ceil(this.downButton.radius), Color.GREEN); // down
        // g.drawCircle((int) Math.ceil(this.leftButton.center.x), (int) Math.ceil(this.leftButton.center.y), (int) Math.ceil(this.leftButton.radius), Color.GREEN); // left
    }

    private void drawRightControllerLikeButtons(Graphics g)
    {
        // right
        this.drawCircleButton(g, this.rightAButton, (this.controller.isR1() || this.controller.isA()));
        // g.drawCircle((int) Math.ceil(this.rightAButton.center.x), (int) Math.ceil(this.rightAButton.center.y), (int) Math.ceil(this.rightAButton.radius), Color.GREEN); // A

        // right right button
        this.drawCircleButton(g, this.rightBButton, this.controller.isR2() || this.controller.isB());
        /*if (this.controller.isR2()) {
            g.drawCircle((int) Math.ceil(this.rightBButton.center.x), (int) Math.ceil(this.rightBButton.center.y), (int) Math.ceil(this.rightBButton.radius), Color.RED); // B
        }
        else {
            g.drawCircle((int) Math.ceil(this.rightBButton.center.x), (int) Math.ceil(this.rightBButton.center.y), (int) Math.ceil(this.rightBButton.radius), Color.GREEN); //
        }*/
    }

    private void drawCircleButton (Graphics g, Circle circle, boolean isActive)
    {
        if (isActive){
            g.drawCircle(
                    (int) Math.ceil(circle.center.x),
                    (int) Math.ceil(circle.center.y),
                    (int) Math.ceil(circle.radius),
                    this.activeCircleButtonPaint); //
        }
        else {
            g.drawCircle(
                    (int) Math.ceil(circle.center.x),
                    (int) Math.ceil(circle.center.y),
                    (int) Math.ceil(circle.radius),
                    this.circleButtonPaint); //
        }

    }

    private void drawLeftStickAsStick (Graphics g)
    {
        g.drawCircle(
                (int) Math.ceil(this.leftStickCircle.center.x),
                (int) Math.ceil(this.leftStickCircle.center.y),
                (int) Math.ceil(this.leftStickCircle.radius),
                this.circleButtonPaint
        );

        // line to position
        Vector2 leftStickDirection = this.controller.getLeftStickDirection();

        g.drawLine(
                (int) Math.ceil(this.controllerLeftButtonsPosition.x),
                (int) Math.ceil(this.controllerLeftButtonsPosition.y),
                (int) Math.ceil(this.controllerLeftButtonsPosition.x + (leftStickDirection.x * this.leftStickCircle.radius)),
                (int) Math.ceil(this.controllerLeftButtonsPosition.y + (leftStickDirection.y * this.leftStickCircle.radius)),
                Color.GREEN
        );

        g.drawCircle(
                (int) Math.ceil(leftStickDirection.x * this.leftStickCircle.radius + this.leftStickCircle.center.x),
                (int) Math.ceil(leftStickDirection.y * this.leftStickCircle.radius + this.leftStickCircle.center.y),
                30,
                this.activeCircleButtonPaint
        );
    }

    private void drawRightStickAsStick (Graphics g)
    {
        g.drawCircle(
                (int) Math.ceil(this.rightStickCircle.center.x),
                (int) Math.ceil(this.rightStickCircle.center.y),
                (int) Math.ceil(this.rightStickCircle.radius),
                this.circleButtonPaint
        );

        // line to position
        Vector2 rightStickDirection = this.controller.getRightStickDirection();

        g.drawLine(
                (int) Math.ceil(this.controllerRightButtonsPosition.x),
                (int) Math.ceil(this.controllerRightButtonsPosition.y),
                (int) Math.ceil(this.controllerRightButtonsPosition.x + (rightStickDirection.x * this.rightStickCircle.radius)),
                (int) Math.ceil(this.controllerRightButtonsPosition.y + (rightStickDirection.y * this.rightStickCircle.radius)),
                Color.GREEN
        );


        g.drawCircle(
                (int) Math.ceil(rightStickDirection.x * this.rightStickCircle.radius + this.rightStickCircle.center.x),
                (int) Math.ceil(rightStickDirection.y * this.rightStickCircle.radius + this.rightStickCircle.center.y),
                30,
                this.activeCircleButtonPaint
        );

        if (rightStickDirection.x != 0 || rightStickDirection.y != 0)
        {
            float angle = rightStickDirection.angle();
            g.drawLine(
                    (int) Math.ceil(this.controllerRightButtonsPosition.x),
                    (int) Math.ceil(this.controllerRightButtonsPosition.y),
                    (int) Math.ceil(this.controllerRightButtonsPosition.x + (Math.cos(angle) * this.rightStickCircle.radius)),
                    (int) Math.ceil(this.controllerRightButtonsPosition.y + (Math.sin(angle) * this.rightStickCircle.radius)),
                    Color.RED
            );
        }
    }
}
