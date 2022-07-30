package com.a530games.jackal;

import com.a530games.jackal.map.Map;
import com.a530games.jackal.objects.Player;
import com.a530games.jackal.objects.Tank;
import com.a530games.jackal.objects.Vehicle;

import java.util.ArrayList;
import java.util.Random;

public class World
{
    // размер мира
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;

    // максимальное колиечество патронов на экране
    static final int MAX_BULLETS_SIZE = 20;

    static final int SCORE_INCREMENT = 10;
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;

    // змея
    public Snake snake;

    // приманка
    public Stain stain;

    // игрок
    public Player player;

    // пульки
    public ArrayList<Bullet> bullets;

    // враги
    public ArrayList<Vehicle> enemies;

    // пульки
    public ArrayList<Bullet> enemyBullets;

    public Map map;

    public boolean gameOver = false;
    public int score = 0;

    // поле, большой массив
    boolean[][] fields = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    static float tick = TICK_INITIAL;

    public World()
    {
        this.map = new Map();

        this.player = new Player(this.map, 300, 300);
        // this.player = new Player(this.map, this.map.playerStartX, this.map.playerStartY);
        this.snake = new Snake();

        // инициализируем массиа с пулями
        this.bullets = new ArrayList<>(10);

        // пока только 10 злодеев
        this.enemies = new ArrayList<>(10);
        this.enemies.add(new Tank(this.map,100, 100));

        this.placeStain();
    }

    public void update(float deltaTime)
    {
        if (this.gameOver) return;

        this.tickTime += deltaTime;

        // update player
        this.player.update(deltaTime);

        // update map
        this.map.update(this.player, deltaTime);

        // бновляем пули игрока
        int bulletSize = this.bullets.size();
        if (bulletSize > 0) {
            for (int i = 0; i < bulletSize; i++) {
                Bullet b = this.bullets.get(i);
                b.update(deltaTime);
                // if (b.isOut()) this.bullets.remove(i);
            }
        }

        // обновляем врагов
        // бновляем пули игрока
        int enemiesSize = this.enemies.size();
        if (enemiesSize > 0) {
            for (int i = 0; i < enemiesSize; i++) {
                Vehicle b = this.enemies.get(i);
                b.update(deltaTime);
                // if (b.isOut()) this.bullets.remove(i);
            }
        }

        // rollback ticks
        while (this.tickTime > tick)
        {
            this.tickTime -= tick;

            // перемещение змеи
            snake.advance();

            // проверка укуса самого себя
            /*if (snake.checkBitten()) {
з                this.gameOver = true;
                return;
            }*/

            // проверка поедания пятна
            SnakePart head = this.snake.parts.get(0);
            if (head.x == this.stain.x && head.y == this.stain.y)
            {
                this.score += SCORE_INCREMENT;
                this.snake.eat();
                // когда занято все место
                if (this.snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
                    this.gameOver = true;
                    return;
                } else {
                    this.placeStain();
                }

                // инкрементация скорости
                if (this.score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    this.tick -= TICK_DECREMENT;
                }
            }
        }
    }

    /**
     * Ставим приманку
     */
    private void placeStain()
    {
        // заполняем массив falsr
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        // проходим по частям змеи и помечаем занятые клетки
        int len = snake.parts.size();
        for (int i = 0; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            fields[part.x][part.y] = true;
        }

        // генерируем след пятно
        int stainX = random.nextInt(WORLD_WIDTH);
        int stainY = random.nextInt(WORLD_HEIGHT);
        while (true) {
            if (fields[stainX][stainY] == false) break;
            stainX += 1;
            if (stainX >= WORLD_WIDTH) {
                stainX = 0;
                stainY += 1;
                if (stainY >= WORLD_HEIGHT) {
                    stainY = 0;
                }
            }
        }

        this.stain = new Stain(stainX, stainY, random.nextInt(3));
    }

    /**
     * Add new bullet
     */
    public boolean addBullet (Bullet bullet)
    {
        // находим свободное место под пулю
        int size = this.bullets.size();

        for (int i = 0; i < size; i++) {
            Bullet b = this.bullets.get(i);
            if (b.isOut()) {
                // this.bullets.set(i, bullet);
                b.reNew(bullet.getX(), bullet.getY());
                return true;
            }
        }

        if (size >= MAX_BULLETS_SIZE) return false;

        this.bullets.add(bullet);
        return true;
    }
}
