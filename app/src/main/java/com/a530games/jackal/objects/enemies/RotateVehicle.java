package com.a530games.jackal.objects.enemies;

import com.a530games.framework.Pixmap;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.World;

/**
 * Diff rotate vehicle like player
 */
public abstract class RotateVehicle extends Vehicle
{

    /**
     *
     */
    public Vector2 direction;


    public RotateVehicle(World world, float startX, float startY, Pixmap image) {
        super(world, startX, startY, image);
        this.direction = new Vector2(0 , 1);
    }

    public Vector2 getDirection() {
        return this.direction;
    }

    @Override
    public void move(int x, int y, float deltaTime)
    {
        super.move(x, y, deltaTime);

        // float currentAngle = this.direction.angleInDegrees();
        //float targetAngle = direction.

        int targetAngle = 0;
        if (x == 1 &&  y == 0) targetAngle = 0;
        if (x == 1 &&  y == 1) targetAngle = 45;
        if (x == 0 &&  y == 1) targetAngle = 90;
        if (x == -1 && y == 1) targetAngle = 135;
        if (x == -1 && y == 0) targetAngle = 180;
        if (x == -1 && y == -1) targetAngle = 225;
        if (x == 0 &&  y == -1) targetAngle = 270;
        if (x == 1 &&  y == -1) targetAngle = 315;

        float currentAngle = this.direction.angleInDegrees();

        if (targetAngle == 0) {
            if (currentAngle != 0) {
                if (currentAngle < 180) {
                    this.direction.rotate(-5);
                }
                if (currentAngle > 180) {
                    this.direction.rotate(5);
                }
            }
        }

        if (targetAngle == 45) {
            if (currentAngle != 45) {
                if (45 < currentAngle && currentAngle < 225) {
                    this.direction.rotate(-5);
                }

                if (225 <= currentAngle && currentAngle <= 360) {
                    this.direction.rotate(5);
                }

                if (0 <= currentAngle && currentAngle < 45) {
                    this.direction.rotate(5);
                }
            }
        }

        if (targetAngle == 90) {
            if (currentAngle != 90) {
                if (90 < currentAngle && currentAngle < 270) {
                    this.direction.rotate(-5);
                }

                if (270 <= currentAngle && currentAngle <= 360) {
                    this.direction.rotate(5);
                }

                if (0 <= currentAngle && currentAngle < 90) {
                    this.direction.rotate(5);
                }
            }
        }

        if (targetAngle == 135) {
            if (currentAngle != 135) {
                if (135 < currentAngle && currentAngle < 315) {
                    this.direction.rotate(-5);
                }

                if (315 <= currentAngle && currentAngle <= 360) {
                    this.direction.rotate(5);
                }

                if (0 <= currentAngle && currentAngle < 135) {
                    this.direction.rotate(5);
                }
            }
        }

        if (targetAngle == 180) {
            if (currentAngle != 180) {
                if (currentAngle < 180) {
                    this.direction.rotate(5);
                }
                if (currentAngle > 180) {
                    this.direction.rotate(-5);
                }
            }
        }


        if (targetAngle == 225) {
            if (currentAngle != 225) {
                if (45 < currentAngle && currentAngle < 225) {
                    this.direction.rotate(5);
                }

                if (225 <= currentAngle) {
                    this.direction.rotate(-5);
                }

                if (0 <= currentAngle && currentAngle <= 45) {
                    this.direction.rotate(-5);
                }
            }
        }

        if (targetAngle == 270) {
            if (currentAngle != 270) {
                if (90 < currentAngle && currentAngle < 270) {
                    this.direction.rotate(5);
                }

                if (270 <= currentAngle) {
                    this.direction.rotate(-5);
                }

                if (0 <= currentAngle && currentAngle <= 90) {
                    this.direction.rotate(-5);
                }
            }
        }

        if (targetAngle == 315) {
            if (currentAngle != 315) {
                if (135 < currentAngle && currentAngle < 315) {
                    this.direction.rotate(5);
                }

                if (315 <= currentAngle) {
                    this.direction.rotate(-5);
                }

                if (0 <= currentAngle && currentAngle <= 135) {
                    this.direction.rotate(-5);
                }
            }
        }
    }

    /*@Override
    public void moveDown(float deltaTime)
    {
        super.moveDown(deltaTime);

        // target 0

        if (1 <= this.direction) {
            this.direction += 0.05;
            if (this.direction >= 2) this.direction = 0;
        }

        if (0 < this.direction && this.direction < 1) {
            this.direction -= 0.05;
            if (this.direction < 0) this.direction = 0;
        }
    }

    @Override
    public void moveDownRight(float deltaTime)
    {
        super.moveDownRight(deltaTime);

        if (1.25 <= this.direction) {
            this.direction += 0.05;
            if (this.direction > 2) this.direction = this.direction - 2;
        }

        if (0 <= this.direction && this.direction < 0.25) {
            this.direction += 0.05;
            if (this.direction > 0.25) this.direction = 0.25;
        }

        if (0.25 < this.direction && this.direction < 1.25 ) {
            this.direction -= 0.05;
            if (this.direction < 0.25) this.direction = 0.25;
        }
    }

    @Override
    public void moveRight(float deltaTime)
    {
        super.moveRight(deltaTime);

        // target 0.5

        if (this.direction >= 1.5) {
            this.direction += 0.05;
            if (this.direction > 2) this.direction = this.direction - 2;
        }

        if (0 <= this.direction && this.direction < 0.5) {
            this.direction += 0.05;
            if (this.direction > 0.5) this.direction = 0.5;
        }

        if (0.5 < this.direction && this.direction < 1.5) {
            this.direction -= 0.05;
            if (this.direction < 0.5) this.direction = 0.5;
        }
    }

    @Override
    public void moveTopRight(float deltaTime)
    {
        super.moveTopRight(deltaTime);

        // this.x += (deltaTime * this.speed);
        // this.y -= (deltaTime * this.speed);

        // target: 0,75
        if (1.75 <= this.direction) {
            this.direction += 0.05;
            if (this.direction > 2) this.direction = this.direction - 2;
        }

        if (0 <= this.direction && this.direction < 0.75) {
            this.direction += 0.05;
            if (this.direction > 0.75) this.direction = 0.75;
        }

        if (0.75 < this.direction && this.direction < 1.75 ) {
            this.direction -= 0.05;
            if (this.direction < 0.75) this.direction = 0.75;
        }
    }

    @Override
    public void moveTop(float deltaTime)
    {
        super.moveTop(deltaTime);

        // traget 1

        if (this.direction < 1) {
            this.direction += 0.05;
            if (this.direction > 1) this.direction = 1;
        }

        if (this.direction > 1) {
            this.direction -= 0.05;
            if (this.direction < 1) this.direction = 1;
        }
    }

    @Override
    public void moveTopLeft(float deltaTime)
    {
        super.moveTopLeft(deltaTime);

        // target 1.25

        if (0.25 <= this.direction && this.direction < 1.25) {
            this.direction += 0.05;
            if (this.direction > 1.25) this.direction = 1.25;
        }

        if (1.25 < this.direction) {
            this.direction -= 0.05;
            if (this.direction < 1.25) this.direction = 1.25;
        }

        if (0 <= this.direction && this.direction < 0.25) {
            this.direction -= 0.05;
            if (this.direction < 0) this.direction += 2;
        }
    }

    @Override
    public void moveLeft(float deltaTime)
    {
        super.moveLeft(deltaTime);

        // target 1,5

        if (0.5 <= this.direction && this.direction < 1.5) {
            this.direction += 0.05;
            if (this.direction > 1.5) this.direction = 1.5;
        }

        if (1.5 < this.direction) {
            this.direction -= 0.05;
            if (this.direction < 1.5) this.direction = 1.5;
        }

        if (0 <= this.direction && this.direction < 0.5) {
            this.direction -= 0.05;
            if (this.direction < 0) this.direction += 2;
        }
    }

    @Override
    public void moveDownLeft(float deltaTime)
    {
        super.moveDownLeft(deltaTime);

        // target 1.75

        if (0.75 < this.direction && this.direction < 1.75) {
            this.direction += 0.05;
            if (this.direction < 0) this.direction += 2;
        }

        if (1.75 < this.direction) {
            this.direction -= 0.05;
            if (this.direction < 1.75) this.direction = 1.75;
        }

        if (0 <= this.direction && this.direction < 0.75)
        {
            this.direction -= 0.05;
            if (this.direction < 0) this.direction += 2;
        }
    }*/

}
