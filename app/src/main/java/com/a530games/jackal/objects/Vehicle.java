package com.a530games.jackal.objects;

import com.a530games.framework.helpers.RollbackFloatRect;
import com.a530games.jackal.Map;

/**
 * Общий класс для транспортного средства
 */
public abstract class Vehicle
{

    // скорость перемещения
    private float speed = 100;

    public RollbackFloatRect hitBox;

    public Vehicle(float startX, float startY)
    {
        this.hitBox = new RollbackFloatRect(startX, startY, startX + 40, startY + 40);
    }

    public abstract void update(float deltaTime);

    public float getLeft() {
        return this.hitBox.left;
    }

    public float getTop() {
        return this.hitBox.top;
    }

    /**
     *
     */
    public void move(int direction, Map map, float deltaTime)
    {
        switch (direction) {
            case Player.MOVE_DOWN: this.moveDown(map, deltaTime); break;
            case Player.MOVE_DOWN_RIGHT: this.moveDownRight(map, deltaTime); break;
            case Player.MOVE_RIGHT: this.moveRight(map, deltaTime); break;
            case Player.MOVE_TOP_RIGHT: this.moveTopRight(map, deltaTime); break;
            case Player.MOVE_TOP: this.moveTop(map, deltaTime); break;
            case Player.MOVE_TOP_LEFT: this.moveTopLeft(map, deltaTime); break;
            case Player.MOVE_LEFT: this.moveLeft(map, deltaTime); break;
            case Player.MOVE_DOWN_LEFT:  this.moveDownLeft(map, deltaTime); break;
        }
    }


    public void moveDown(Map map, float deltaTime)
    {
        // move don
        // this._newPos = this.y + (deltaTime * this.speed);

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * this.speed));

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            // this.hitBox.top = this._newPos;
            this.hitBox.rollback();
        }
    }

    public void moveDownRight(Map map, float deltaTime)
    {
        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * this.speed));

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            // this.y = this._newPos;
            this.hitBox.rollback();
        }

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * this.speed), this.hitBox.top);

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // this.x += (deltaTime * this.speed);
        // this.y += (deltaTime * this.speed);
        // this.hitBox.left = Math.round(this.x);
        // this.hitBox.top = Math.round(this.y);
    }

    public void moveRight(Map map, float deltaTime)
    {
        // this.x += (deltaTime * this.speed);

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * this.speed), this.hitBox.top);

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveTopRight(Map map, float deltaTime)
    {
        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left + (deltaTime * this.speed), this.hitBox.top);

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveTop(Map map, float deltaTime)
    {
        // this.y -= (deltaTime * this.speed);

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveTopLeft(Map map, float deltaTime)
    {
        //this.x -= (deltaTime * this.speed);
        // this.y -= (deltaTime * this.speed);

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top - (deltaTime * this.speed));

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

    }

    public void moveLeft(Map map, float deltaTime)
    {
        // this.x -= (deltaTime * this.speed);

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

    public void moveDownLeft(Map map, float deltaTime)
    {
        // this.x -= (deltaTime * this.speed);
        // this.y += (deltaTime * this.speed);

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left, this.hitBox.top + (deltaTime * this.speed));

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }

        // двигаем хитбокс
        this.hitBox.moveTo(this.hitBox.left - (deltaTime * this.speed), this.hitBox.top);

        // проверяем пересечение с тестовой рамкой
        if (map.testRect.isIntersect(this.hitBox)) {
            this.hitBox.rollback();
        }
    }

}
