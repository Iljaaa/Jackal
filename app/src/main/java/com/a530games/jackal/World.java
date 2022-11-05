package com.a530games.jackal;

import android.util.Log;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.jackal.levels.Level;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.map.MapEventsHandler;
import com.a530games.jackal.objects.Bullet;
import com.a530games.jackal.objects.DropPad;
import com.a530games.jackal.objects.EnemiesCollectionLinkedList;
import com.a530games.jackal.objects.EnemyBulletsCollectionLinkedList;
import com.a530games.jackal.objects.PlayerBulletsCollectionLinkedList;
import com.a530games.jackal.objects.PlayerEventHandler;
import com.a530games.jackal.objects.enemies.EnemyFireEventHandler;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.Player;

import java.util.ListIterator;

public class World implements PlayerEventHandler, EnemyFireEventHandler, MapEventsHandler
{
    // размер мира
    /*static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;

    // max bullets size
    static final int MAX_BULLETS_SIZE = 20;

    static final int SCORE_INCREMENT = 10;
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;*/

    // приманка
    // public Stain stain;

    // игрок
    public Player player;

    /**
     * Enemy drop pad
     */
    public DropPad dropPad;

    /**
     * player bullets
     */
    public PlayerBulletsCollectionLinkedList playerBullets;

    /**
     * Enemies
     */
    public EnemiesCollectionLinkedList enemies;

    /**
     * Enemies bullets
     */
    public EnemyBulletsCollectionLinkedList enemyBullets;

    // map
    public Map map;


    // Random random = new Random();

    // private ArrayList<Sound> tankHitSounds;

    public boolean gameOver = false;
    public boolean gameOverSuccess = false;

    // public int score = 0;

    // поле, большой массив
    // boolean[][] fields = new boolean[WORLD_WIDTH][WORLD_HEIGHT];

    // float tickTime = 0;
    // static float aTick = TICK_INITIAL;


    /**
     * @param playerStartHp player start hp
     * @param oneMapBlockWidth map block width
     * @param oneMapBlockHeight map block size
     */
    public World(int playerStartHp, int oneMapBlockWidth, int oneMapBlockHeight)
    {
        this.player = new Player(this);
        this.player.hp = playerStartHp;

        // create map
        // this.map = new Map(Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);
        this.map = new Map(oneMapBlockWidth, oneMapBlockHeight);
        this.map.setEventHandler(this);

        // bind map follow for player
        // this.map.setFollowObject(this.player);

        // this.enemies = new ArrayList<>(10);
        this.enemies = new EnemiesCollectionLinkedList();

        // this.enemies.add(new Tank(this,100, 100));
        // this.enemies.add(new Commandos(400, 800));
        // this.enemies.add(new Commandos2(500, 800));

        // add drop padd
        this.dropPad = new DropPad(this);
        this.enemies.add(this.dropPad);

        // player bullets collection
        this.playerBullets = new PlayerBulletsCollectionLinkedList();

        // enemy bullets collection
        this.enemyBullets = new EnemyBulletsCollectionLinkedList();

        //
        /*this.tankHitSounds = new ArrayList<>();
        this.tankHitSounds.add(Assets.tankHit1);
        this.tankHitSounds.add(Assets.tankHit2);
        this.tankHitSounds.add(Assets.playerBlow);*/
    }

    /**
     * Init world by level
     */
    public void initByLevel(Level level, Graphics g)
    {
        // init map
        this.map.init(
                level,
                g,
                this.player,
                this.dropPad
        );
    }

    public void update(float deltaTime)
    {
        // is game loose
        if (this.gameOver) return;

        // is game win
        if (this.gameOverSuccess) return;

        // update player
        this.player.update(deltaTime);

        // update map
        this.map.update(deltaTime);

        // player bullets update
        this.updatePlayerBullets(deltaTime);

        // update enemies
        this.updateEnemies(deltaTime);

        // this.enemyBullets.update(deltaTime);
        this.updateEnemyBullets(deltaTime);

        // rollback ticks
        /*while (this.tickTime > aTick)
        {
            this.tickTime -= aTick;

            // перемещение змеи
            snake.advance();

            // проверка укуса самого себя
            if (snake.checkBitten()) {
з                this.gameOver = true;
                return;
            }

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
                if (this.score % 100 == 0 && aTick - TICK_DECREMENT > 0) {
                    World.aTick -= TICK_DECREMENT;
                }
            }
        }*/
    }

    /**
     * Update player bullets
     */
    private void updatePlayerBullets(float deltaTime)
    {

        ListIterator<Bullet> listIterator = this.playerBullets.listIterator();
        while (listIterator.hasNext())
        {
            Bullet b = listIterator.next();

            // delete out bullet
            if (b.isOut()){
                listIterator.remove();
            }

            this.updateOnePlayerBullet(b, deltaTime);
        }

        /*int bulletSize = this.bullets.size();
        if (bulletSize > 0) {
            for (int i = 0; i < bulletSize; i++)
            {
                Bullet b = this.bullets.get(i);
                if (b.isOut()) continue;

                // check bullet is intersect with map objects
                if (this.map.isIntersectPoint(b.mapPosition.x, b.mapPosition.y)) {
                    b.setIsOutOnIntersectWithMap();
                }

                b.update(deltaTime);
            }
        }*/
    }

    /**
     * Update one bullet
     * @param bullet Bullet for update
     */
    private void updateOnePlayerBullet(Bullet bullet, float deltaTime)
    {
        // check bullet is intersect with map objects
        if (this.map.isIntersectPoint(bullet.position.x, bullet.position.y)) {
            bullet.setIsOutOnIntersectWithMap();
        }

        bullet.update(deltaTime);
    }

    /**
     * Update enemies
     */
    private void updateEnemies (float deltaTime)
    {
        // delete dead enemies
        // this.enemies.clearDeadEnemies();

        ListIterator<Enemy> listIterator = this.enemies.listIterator();
        while (listIterator.hasNext())
        {
            Enemy enemy = listIterator.next();

            // delete out bullet
            if (enemy.isDead()){
                listIterator.remove();
            }

            this.updateOneEnemy(enemy, deltaTime);
        }

        /*boolean enemyDeleted;
        int enemiesSize;

        // delete dead enemies
        do
        {
            enemiesSize = this.enemies.size();
            enemyDeleted = false;
            if (enemiesSize > 0)
            {
                // update enemies and check intersect with bullets
                for (int i = 0; i < enemiesSize; i++) {
                    // remove dead enemies
                    if (this.enemies.get(i).isDead()) {
                        this.enemies.remove(this.enemies.get(i));
                        enemyDeleted = true;
                        break;
                    }
                }
            }
        }
        while (enemyDeleted);*/
        // while (enemyDeleted && enemiesSize > 0);


        //  update enemies and check intersect with bullets
        /*int enemiesSize = this.enemies.size();
        int playerBulletsSize = this.playerBullets.size();

        if (enemiesSize > 0)
        {
            // update enemies and check intersect with bullets
            for (int i = 0; i < enemiesSize; i++)
            {
                Enemy enemy = this.enemies.get(i);
                enemy.update(deltaTime, this);

                // check intersect with player bullets
                HitBox hitbox = enemy.getHitBox();
                if (hitbox != null) {
                    ListIterator<Bullet> listIterator = this.playerBullets.listIterator();
                    while (listIterator.hasNext())
                    {
                        Bullet b = listIterator.next();
                        if (b.isOut()) continue;

                        if (hitbox.isHit(b))
                        {
                            // hit enemy here
                            enemy.hit(1);

                            // disable bullet
                            b.setIsOutOnHitEnemy();

                            // and brake this cicle
                            break;
                        }
                    }
                }

            }
        }*/
    }

    /**
     * Update one enemby
     */
    private void updateOneEnemy(Enemy enemy, float deltaTime)
    {
        // check intersect with player bullets
        HitBox hitbox = enemy.getHitBox();
        if (hitbox != null) {
            ListIterator<Bullet> listIterator = this.playerBullets.listIterator();
            while (listIterator.hasNext())
            {
                Bullet b = listIterator.next();
                if (b.isOut()) continue;

                if (hitbox.isHit(b))
                {
                    // hit enemy here
                    enemy.hit(1);

                    // disable bullet
                    b.setIsOutOnHitEnemy();

                    // and brake this cicle
                    break;
                }
            }
        }


        // update enemy
        enemy.update(deltaTime, this);
    }

    /**
     * Update enemies bullets
     */
    private void updateEnemyBullets (float deltaTime)
    {
        ListIterator<Bullet> listIterator = this.enemyBullets.listIterator();
        while (listIterator.hasNext())
        {
            Bullet b = listIterator.next();

            // delete out bullet
            if (b.isOut()){
                listIterator.remove();
                continue;
            }

            this.updateOneEnemyBullet(b, deltaTime);
        }

        /*int enemyBulletsSize = this.enemyBullets.size();
        if (enemyBulletsSize > 0) {
            for (int i = 0; i < enemyBulletsSize; i++) {
                Bullet b = this.enemyBullets.get(i);
                if (b.isOut()) continue;

                // if (this.map.isIntersectPoint(b.x, b.y)) {
                if (this.map.isIntersectPoint(b.mapPosition.x, b.mapPosition.y)) {
                    b.setIsOutOnIntersectWithMap();
                    continue;
                }

                b.update(deltaTime);
                // if (b.isOut()) this.bullets.remove(i);

                if (this.player.hitBox.isHit(b))
                {
                    // player hit
                    this.player.hit(1);

                    // mark bullet is out
                    b.setIsOutOnHitEnemy();

                    if(Settings.soundEnabled) {
                        Assets.playerHit.play(1);
                    }
                }
            }
        }*/
    }

    /**
     * Update one enemy bullet
     */
    private void updateOneEnemyBullet (Bullet bullet, float deltaTime)
    {
        // check intersect bullet with map
        // if (this.map.isIntersectPoint(b.x, b.y)) {
        if (this.map.isIntersectPoint(bullet.position.x, bullet.position.y))
        {
            bullet.setIsOutOnIntersectWithMap();
            return;
        }

        // check intersect with player
        if (this.player.hitBox.isHit(bullet))
        {
            // player hit
            this.player.hit(1);

            // mark bullet is out
            bullet.setIsOutOnHitEnemy();

            if(Settings.soundEnabled) {
                Assets.playerHit.play(1);
            }

            return;
        }

        //
        bullet.update(deltaTime);
    }

    /**
     * Player press fire button
     */
    public boolean playerFire()
    {
        // check delay
        if (!this.player.fire()) return false;

        // Vector2 turretAngle = this.player.getTurretAngle();

        // add bullet
        return this.addBullet(
                this.player.hitBox.getCenterX() + this.player.turret.x * 20,
                this.player.hitBox.getCenterY() + this.player.turret.y * 20,
                this.player.turret.x * Player.BULLET_SPEED,
                this.player.turret.y * Player.BULLET_SPEED,
                1f
        );
    }

    /**
     * Add player bullet
     * refactor by get free
     */
    public boolean addBullet (float playerCenterX, float playerCenterY, float velocityX, float velocityY, float lifeTime)
    {
        Bullet b = this.playerBullets.getFreeBullet();
        if (b == null) return false;

        b.reNew(playerCenterX, playerCenterY, velocityX, velocityY, lifeTime);
        // if(Settings.soundEnabled) Assets.fire.play(1);
        return true;

        /*int size = this.bullets.size();

        for (int i = 0; i < size; i++) {
            Bullet b = this.bullets.get(i);
            if (b.isOut()) {
                b.reNew(bullet.getX(), bullet.getY(), 1);
                return true;
            }
        }

        if (size >= MAX_BULLETS_SIZE) return false;

        this.bullets.add(bullet);
        return true;*/
    }

    @Override
    public void mapInitFinish() {

    }

    @Override
    public void spownEnemyOnCell(MapCell spownCell, Enemy enemy)
    {
        // check intersect by player
        // if (FloatRect.isIntersectsTwoRectF(this.player.getHitBox(), enemy.getHitBox())) {
        if (this.player.hitBox.isIntersectsWithHitbox(enemy.getHitBox())) {
            return;
        }

        // check intersect with enem
        if (this.enemies.isIntersectWithEnemies(enemy)) {
            return;
        }

        // is this new enemy
        if (this.enemies.indexOf(enemy) <= 0)
        {
            // bind fire handlet
            enemy.setFireEventHandler(this);

            // add enemy to list
            this.enemies.add(enemy);

            // set follow from enemy tank
            // this.map.setFollowObject(enemy);
        }

    }

    @Override
    public void mapWin() {
        // this.gameOver = true;
        this.gameOverSuccess = true;
    }

    @Override
    public void enemyFire(float mapPositionX, float mapPositionY, float velocityX, float velocityY, float lifeTime)
    {
        Bullet b = this.enemyBullets.getFreeElement();
        if (b == null) return;

        // b.reNewByVector(mapPositionX, mapPositionY, this.turretAngle.x, this.turretAngle.y);
        b.reNew(mapPositionX, mapPositionY, velocityX, velocityY, lifeTime);

        if (Settings.soundEnabled) {
            Assets.tankFire.play(0.7f);
        }
    }

    @Override
    public void onPlayerDie() {
        Log.d("World", "Player dit");
        this.gameOver = true;
    }

    /**
     * On player droppers
     */
    public void playerDropped()
    {
        // this.map.setFollowObject(this.player);

        //
        this.player.state = Player.PlayerState.OnLine;
    }
}
