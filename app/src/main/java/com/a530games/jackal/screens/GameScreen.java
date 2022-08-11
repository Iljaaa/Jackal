package com.a530games.jackal.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.a530games.framework.Controller;
import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.Input;
import com.a530games.framework.Pixmap;
import com.a530games.framework.Screen;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.objects.Bullet;
import com.a530games.jackal.Settings;
import com.a530games.jackal.Sidebar;
import com.a530games.jackal.Snake;
import com.a530games.jackal.SnakePart;
import com.a530games.jackal.Stain;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.Player;

import java.util.List;

/**
 *
 */
public class GameScreen extends Screen
{
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState state = GameState.Ready;
    World world;
    Sidebar sidebar;

    int oldScore = 0;
    String score = "0";

    // paint for the hit box
    Paint hitBoxPaint;


    /////////////////
    Vector2 cannonPos;
    Vector2 cannonAngle;
    int cannonRotateSpeed = 3;
    // float cannonAngle = 0;




    public GameScreen(Game game) {
        super(game);
        this.world = new World();
        this.sidebar = new Sidebar();

        this.hitBoxPaint = new Paint();
        this.hitBoxPaint.setStyle(Paint.Style.STROKE);
        this.hitBoxPaint.setStrokeWidth(2);
        this.hitBoxPaint.setColor(Color.YELLOW);

        // Assets.music.setLooping(true);
        // Assets.music.setVolume(0.5f);
        // Assets.music.play();

        this.cannonPos = new Vector2(200, 1000);
        this.cannonAngle = new Vector2(1, 0);
    }

    @Override
    public void update(float deltaTime)
    {
        // todo: подмать какуюто абстрацию над этим всем делом
        List<Input.TouchEvent> touchEvents = this.game.getInput().getTouchEvents();

        List<Input.KeyEvent> keyEvents = this.game.getInput().getKeyEvents();

        //
        Controller c = this.game.getInput().getController();

        //



        Vector2 vectorToPlayer = new Vector2(
                this.world.player.hitBox.getCenterLeft() - this.cannonPos.x,
                this.world.player.hitBox.getCenterTop() - this.cannonPos.y
        );

        float ca = this.cannonAngle.angleInDegrees();
        float vp = vectorToPlayer.angleInDegrees();
        float t = (float) Math.abs(ca - vp);
        if (this.cannonRotateSpeed < Math.abs(ca - vp)) {
            this.cannonAngle.rotate(3);
        }

        /*int keyEventsLength = keyEvents.size();
        if (keyEventsLength > 0) {
            for(int i = 0; i < keyEventsLength; i++) {
                Input.KeyEvent event = keyEvents.get(i);
                Log.d("key", "key pressed: "+String.valueOf(event.keyCode));
            }
        }*/

        if (this.state == GameState.Ready) this.updateReady(touchEvents, c);
        if (this.state == GameState.Running) this.updateRunning(touchEvents, keyEvents, c, deltaTime);
        if (this.state == GameState.Paused) this.updatePaused(touchEvents, c);
        if (this.state == GameState.GameOver) this.updateGameOver(touchEvents);
    }

    private void updateReady(List<Input.TouchEvent> touchEvents, Controller controller)
    {
        // if got fouch go to run
        if(touchEvents.size() > 0) this.state = GameState.Running;
        if (controller.isStart()) this.state = GameState.Running;
    }

    private void updateRunning(List<Input.TouchEvent> touchEvents, List<Input.KeyEvent> keyEvents, Controller controller, float deltaTime)
    {
        // обработка клика в левый верхний угол для установки паузы
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            // обработка паузы
            if(Input.TouchEvent.TOUCH_UP == event.type) {
                int k = 3;
                /*if(event.x < 64 && event.y < 64) {
                    //if(Settings.soundEnabled)
                        // todo: play click
                        // Assets.click.play(1);
                    this.state = GameState.Paused;
                    return;
                }*/
            }

            // определения типа клика и совершаем поворот
            if(event.type == Input.TouchEvent.TOUCH_DOWN) {
                if(event.x < 200) {
                    this.world.snake.turnLeft();
                    // this.world.player.moveLeft(deltaTime);
                }
                if(event.x > 200) {
                    this.world.snake.turnRight();
                    // this.world.player.moveRight(deltaTime);
                }
            }
        }

        /*if (controller.isStart()) {
            this.state = GameState.Paused;
            return;
        }*/

        // move player
        if (controller.isTopButtonDown())  {
            if (controller.isRightButtonDown()) {
                // this.world.player.move(Player.MOVE_TOP_RIGHT, deltaTime);
                this.world.player.move(1, -1, deltaTime);
            }
            else if (controller.isLeftButtonDown()) {
                // this.world.player.move(Player.MOVE_TOP_LEFT, deltaTime);
                this.world.player.move(-1, -1, deltaTime);
            }
            else {
                // this.world.player.move(Player.MOVE_TOP, deltaTime);
                this.world.player.move(0, -1, deltaTime);
            }
        }
        else if (controller.isBottomButtonDown())  {
            if (controller.isRightButtonDown()) {
                // this.world.player.move(Player.MOVE_DOWN_RIGHT, deltaTime);
                this.world.player.move(1, 1, deltaTime);
            }
            else if (controller.isLeftButtonDown()) {
                // this.world.player.move(Player.MOVE_DOWN_LEFT, deltaTime);
                this.world.player.move(-1, 1, deltaTime);
            }
            else {
                // this.world.player.move(Player.MOVE_DOWN, deltaTime);
                this.world.player.move(0, 1, deltaTime);
            }
        }
        else {
            if (controller.isLeftButtonDown()) {
                // this.world.player.move(Player.MOVE_LEFT, deltaTime);
                this.world.player.move(-1, 0, deltaTime);
            }
            if (controller.isRightButtonDown()) {
                // this.world.player.move(Player.MOVE_RIGHT, deltaTime);
                this.world.player.move(1, 0, deltaTime);
            }
        }

        /*
        старый вариант контора

        if (controller.isTopButtonDown())  {
            this.world.player.moveTop(deltaTime);
        }

        if (controller.isRightButtonDown()) {
            this.world.player.moveRight(deltaTime);
        }

        if (controller.isBottomButtonDown()) {
            this.world.player.moveDown(deltaTime);
        }

        if (controller.isLeftButtonDown()) {
            this.world.player.moveLeft(deltaTime);
        }*/

        // fire
        // todo: сделать абстракцию кнопку огонек
        if (controller.isA())
        {
            if (this.world.player.fire()) {
                if (this.world.addBullet(this.world.player.hitBox.getCenterLeft(), this.world.player.hitBox.getCenterTop(), 1)){
                    if(Settings.soundEnabled) Assets.fire.play(1);
                }
            }
        }

        // обработка поворота
        int keyEventsLength = keyEvents.size();
        for(int i = 0; i < keyEventsLength; i++) {
            Input.KeyEvent event = keyEvents.get(i);
            /*if (event.type == Input.KeyEvent.KEY_UP) {
                if (event.keyCode == 32) isRight = false;
            }*/
            if (event.type == Input.KeyEvent.KEY_DOWN) {
                // fixme:
                Log.d("key", String.valueOf(event.keyCode));
                if (event.keyCode == 32) {
                    this.world.snake.turnRight();
                    // this.world.player.moveRight(deltaTime);
                }
                else if (event.keyCode == 29) {
                    this.world.snake.turnLeft();
                    //this.world.player.moveLeft(deltaTime);
                }
                else if (event.keyCode == 51) {
                    //this.world.player.moveTop(deltaTime);
                }
                else if (event.keyCode == 47) {
                    //this.world.player.moveDown(deltaTime);
                }
                else if (event.keyCode == 62) {
                    this.world.player.fire();
                }
            }
        }

        // обновление мира
        this.world.update(deltaTime);

        // fixme: при завершении f[s выводить не будем
        this.sidebar.setFps(this.game.getRenderView().fps);
        this.sidebar.setPlayerAngle(this.world.player.direction);
        this.sidebar.setPlayerPos(Math.round(this.world.player.hitBox.left), Math.round(this.world.player.hitBox.top));
        this.sidebar.setMapPos((int) Math.floor(this.world.map.x), (int) Math.floor(this.world.map.y));


        // обновление боковой информации
        // this.sidebar.update(deltaTime);

        //
        if(this.world.gameOver) {
            // if(Settings.soundEnabled) Assets.bitten.play(1);
            this.state = GameState.GameOver;
        }


        // обновление рекорда
        if(this.oldScore != this.world.score) {
            this.oldScore = this.world.score;
            this.score = "" + this.oldScore;
            // todo: play sound eat
            // if(Settings.soundEnabled) Assets.eat.play(1);
        }
    }

    private void updatePaused(List<Input.TouchEvent> touchEvents, Controller controller)
    {
        /*int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP)
            {
                // определяем клик для сниятия м пайзы
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        // todo: enable sound
                        // if(Settings.soundEnabled) Assets.click.play(1);
                        this.state = GameState.Running;
                        return;
                    }
                }

                // опреляем клик по пункту меню возврата к главному экрану
                // он в левом верхнем углу
                if(event.y > 148 && event.y < 196) {
                    // if(Settings.soundEnabled) Assets.click.play(1);
                    // game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }*/
        if(touchEvents.size() > 0) this.state = GameState.Running;
        /*if (controller.isStart()) {
            this.state = GameState.Running;
        }*/
    }

    private void updateGameOver(List<Input.TouchEvent> touchEvents)
    {
        // определяяем клик для перехода в главное меню
        /*int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP)
            {
                // по клику перезапускаем игру
                if(event.x >= 128 && event.x <= 192 && event.y >= 200 && event.y <= 264) {
                    // todo: enable sound
                    // if(Settings.soundEnabled) Assets.click.play(1);
                    // game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }*/
    }

    @Override
    public void present(float deltaTime)
    {
        // clear bg
        Graphics g = game.getGraphics();
        g.clear(Color.BLACK);
        // g.drawPixmap(Assets.background, 0, 0);

        // рисуем имр
        this.drawWorld(this.world);

        // рисуем сайдбар
        this.drawSidebar(this.sidebar);

        if (state == GameState.Running) this.drawRunningUI();
        if (state == GameState.Ready) this.drawReadyUI();
        if (state == GameState.Paused) this.drawPausedUI();
        if (state == GameState.GameOver) this.drawGameOverUI();

        // draw score
        // this.drawText(g, score, g.getWidth() / 2 - score.length()*20 / 2, g.getHeight() - 42);

        // this.game.getGraphics().drawPixmap(Assets.bg, 10, 10);
        g.drawLine(
                this.world.map.screenLeftPotion(this.cannonPos.x),
                this.world.map.screenTopPotion(this.cannonPos.y),
                this.world.map.screenLeftPotion(this.cannonPos.x + this.cannonAngle.x * 50),
                this.world.map.screenTopPotion(this.cannonPos.y + this.cannonAngle.y * 50),
                Color.MAGENTA);

        /*Vector2 vectorToPlayer = this.cannonAngle.cpy();
        vectorToPlayer.set(
                this.world.player.hitBox.getCenterLeft() - this.cannonAngle.x,
                this.world.player.hitBox.getCenterTop() - this.cannonAngle.y
        );

        float ca = this.cannonAngle.angleInDegrees();
        float vp = vectorToPlayer.angleInDegrees();
        int t = (int) Math.ceil(Math.abs(ca - vp));
        if (t < this.cannonRotateSpeed) {
            int a = 3;
        }

        g.drawLine(
                this.world.map.screenLeftPotion(this.cannonPos.x),
                this.world.map.screenTopPotion(this.cannonPos.y),
                this.world.map.screenLeftPotion(vectorToPlayer.x),
                this.world.map.screenTopPotion(vectorToPlayer.y),
                Color.MAGENTA);*/
    }

    private void drawWorld(World world)
    {
        // рисуем карку
        this.drawMap();

        // draw player
        this.drawPlayer();

        this.drawEnemies();

        // draw snake
        this.drawStain();
        this.drawSnakeTail();
        this.drawSnakeHead();

        this.drawBullets();
    }

    private void drawSidebar(Sidebar sidebar)
    {
        Graphics g = this.game.getGraphics();
        g.drawLine(0, 0, this.game.getGraphics().getWidth(), this.game.getGraphics().getHeight(), Color.YELLOW);

        // если сайдбар не нуждается в отрисовке
        if (!sidebar.isNeedRedraw()) {
            return;
        }

        this.game.getGraphics().drawText("fps: " + sidebar.fps, 650, 50, 20, Color.MAGENTA);
        this.game.getGraphics().drawText("player: " + sidebar.playerX+ "x"+sidebar.playerY, 650, 80, 20, Color.MAGENTA);
        this.game.getGraphics().drawText("angle: " + sidebar.playerAngle.x + "x" + sidebar.playerAngle.y, 650, 110, 20, Color.MAGENTA);
        this.game.getGraphics().drawText("map: " + sidebar.mapX+ "x"+sidebar.mapY, 650, 140, 20, Color.MAGENTA);

        // рисуем линю отделяющею сайдбар
        if (this.game.isLandscape()) {
            g.drawLine(640, 100, 640, 300, Color.GREEN);
        }
        else {
            g.drawLine(100, 640, 300, 640, Color.GREEN);
        }


    }

    private void drawBullets()
    {
        Graphics g = this.game.getGraphics();

        //
        int bulletSize = this.world.bullets.size();
        if (bulletSize > 0) {
            for (int i = 0; i < bulletSize; i++)
            {
                Bullet b = this.world.bullets.get(i);
                if (b.isOut()) continue;

                g.drawPixmap(
                        Assets.bullet,
                        this.world.map.screenLeftPotion(Math.round(b.getX())),
                        this.world.map.screenTopPotion(Math.round(b.getY()))
                );
            }
        }

        //
        int enemyBulletsSize = this.world.enemyBullets.size();
        if (enemyBulletsSize > 0) {
            for (int i = 0; i < enemyBulletsSize; i++)
            {
                Bullet b = this.world.enemyBullets.get(i);
                if (b.isOut()) continue;

                g.drawPixmap(
                        Assets.bullet,
                        this.world.map.screenLeftPotion(Math.round(b.getX())),
                        this.world.map.screenTopPotion(Math.round(b.getY()))
                );
            }
        }
    }

    private void drawEnemies()
    {
        Graphics g = this.game.getGraphics();

        //
        int enemiesSize = this.world.enemies.size();
        if (enemiesSize > 0) {
            for (int i = 0; i < enemiesSize; i++)
            {
                Enemy enemy = this.world.enemies.get(i);
                Rect screenHitBox = enemy.getScreenDrawHitbox(this.world.map);

                Sprite s = enemy.getSprite();

                // todo: not draw enemie if if is not in screem

                g.drawPixmap(
                        s.image,
                        screenHitBox.left + s.screenMarginLeft,
                        // this.world.map.screenLeftPotion(enemy.getHitBox().left) - 12,
                        screenHitBox.top + s.screenMarginTop,
                        // this.world.map.screenTopPotion(enemy.getHitBox().top) - 12,
                        s.getLeft(),
                        s.getTop(),
                        s.width,
                        s.height);

                // draw hitbox
                g.drawRect(screenHitBox, this.hitBoxPaint);
                /*this.drawEnemyHitBox(
                        g,
                        this.world.map.screenLeftPotion(b.hitBox.left),
                        this.world.map.screenTopPotion(b.hitBox.top),
                        Math.round(b.hitBox.getWidth()),
                        Math.round(b.hitBox.getHeight())
                );*/

                // its only for tanks and turrets
                if (enemy.hasTurret())
                {
                    // target
                    this.drawAngle(g,
                            screenHitBox.centerX(),
                            screenHitBox.centerY(),
                            enemy.getTargetAngle(),
                            Color.LTGRAY
                    );

                    // turret
                    this.drawAngle(g,
                            screenHitBox.centerX(),
                            screenHitBox.centerY(),
                            enemy.getTurretAngle(),
                            Color.GREEN
                    );
                }
            }
        }
    }

    /*
    private void drawEnemyHitBox (Graphics g, int enemyScreenLeft, int enemyScreenTop, int enemyWidth, int enemyHeight) {
        g.drawRect(
            enemyScreenLeft,
            enemyScreenTop,
            enemyWidth,
            enemyHeight,
                this.hitBoxPaint);
    }*/

    protected void drawMap()
    {
        Graphics g = this.game.getGraphics();

        // draw part of map
        g.drawBitmap(
                this.world.map.testBitmap,
                0,
                0,
                -1 * (int) Math.floor(this.world.map.x),
                -1 * (int )Math.floor(this.world.map.y),
                640,
                640);

        // this.drawMapNet(g);

        // this.drawActiveCell();

        // todo: calculate objects on screen
        for (int row = 0; row < this.world.map.mapRows; row++) {
            for (int col = 0; col < this.world.map.mapCols; col++) {
                MapCell c = this.world.map.fields[row][col];
                if (c == null) continue;
                // g.drawRect(c.hitBox, this.hitBoxPaint);
                g.drawRect(
                    (int) Math.floor(c.hitBox.left + this.world.map.x),
                    (int) Math.floor(c.hitBox.top + this.world.map.y),
                    c.hitBox.width(),
                    c.hitBox.height(),
                    this.hitBoxPaint
                    );

                /*if (c.isRock) {
                    g.drawRect(c.hitBox, this.hitBoxPaint);
                }*/
            }
        }
    }

    private void drawMapNet (Graphics g)
    {
        g.drawLine(640, 0, 640, 640, Color.WHITE);

        for (int i = 1; i <= 9; i++) {
            g.drawLine(i * 64, 0, i * 64, 640, Color.GRAY);
        }

        for (int i = 1; i <= 9; i++) {
            g.drawLine(0, i * 64, 640, i * 64, Color.GRAY);
        }
    }

    private void drawActiveCell ()
    {
        int row = this.world.map.getRowByTop(this.world.player.getTop());
        int col = this.world.map.getColByLeft(this.world.player.getLeft());

        int top = this.world.map.screenTopPotion(row * Map.SPRITE_HEIGHT);
        int left = this.world.map.screenLeftPotion(col * Map.SPRITE_WIDTH);

        Graphics g = this.game.getGraphics();
        // g.drawRect(0, row * Map.SPRITE_HEIGHT, 640, 20, Color.WHITE);
        g.drawRect( left, top, Map.SPRITE_WIDTH, Map.SPRITE_HEIGHT, Color.YELLOW);
    }

    /**
     *
     */
    private void drawPlayer ()
    {
        Graphics g = this.game.getGraphics();

        // int playerScreenX = Math.round(this.world.player.hitBox.left);
        int playerScreenX = this.world.map.screenLeftPotion(this.world.player.hitBox.left);
        // int playerSourceY = Math.round(this.world.player.hitBox.top);
        int playerSourceY = this.world.map.screenTopPotion(this.world.player.hitBox.top);

        this.drawPlayerHitBox(g, playerScreenX, playerSourceY);

        /*
         * 12 = 0.5 * 64 (player sprite width) - 40 (player hitbox width)
         */
        g.drawPixmap(
            this.world.player.sprite.image,
            Math.round(playerScreenX) - 12, //
            Math.round(playerSourceY) - 12,
                this.world.player.sprite.getLeft(),
                this.world.player.sprite.getTop(),
            64,
            64);

        // draw turret
        g.drawPixmap(
                this.world.player.gun.image,
                Math.round(playerScreenX) - 12, //
                Math.round(playerSourceY) - 12,
                this.world.player.gun.getLeft(),
                this.world.player.gun.getTop(),
                64,
                64);

        this.drawPlayerAngle(g, playerScreenX, playerSourceY);
    }

    private void drawPlayerHitBox (Graphics g, int playerScreenX, int playerScreenY) {
        // g.drawRect(this.world.player.hitBox.getDrawRect(), this.hitBoxPaint);
        g.drawRect(playerScreenX, playerScreenY, Math.round(this.world.player.hitBox.getWidth()), Math.round(this.world.player.hitBox.getHeight()), this.hitBoxPaint);
    }

    private void drawPlayerAngle (Graphics g, int playerScreenX, int playerScreenY)
    {
        int centerX = (int) Math.round(playerScreenX + (0.5 * this.world.player.hitBox.getWidth()));
        int centerY = (int) Math.round(playerScreenY  + (0.5 * this.world.player.hitBox.getHeight()));

        g.drawLine(
                centerX,
                centerY,
                (int) Math.ceil(centerX + (this.world.player.direction.x * 50)),
                (int) Math.ceil(centerY + (this.world.player.direction.y * 50)),
                Color.MAGENTA);
    }

    /**
     *
     * @param g
     * @param screenCenterLeft
     * @param screenCenterTop
     * @param angleVector normal vector
     */
    private void drawAngle (Graphics g, int screenCenterLeft, int screenCenterTop, Vector2 angleVector, int color)
    {
        // int centerTop = Math.round(this.world.player.hitBox.getCenterTop());

        // отрисовываем вектор направления
        // double s = Math.sin(this.world.player.getAngle() * Math.PI);
        // double c = Math.cos(this.world.player.getAngle() * Math.PI);

        /*g.drawLine(
                screenCenterLeft,
                screenCenterTop,
                screenCenterLeft + (int) Math.round(Math.sin(angle * Math.PI) * 50),
                screenCenterTop + (int) Math.round(Math.cos(angle * Math.PI) * 50),
                Color.GREEN);*/

        g.drawLine(
                screenCenterLeft,
                screenCenterTop,
                Math.round(screenCenterLeft + (angleVector.x * 50)),
                Math.round(screenCenterTop + (angleVector.y * 50)),
                color);
    }

    private void drawStain ()
    {
    // отрисовываем пятно
        Pixmap stainPixmap = null;
        // todo: get stain tyoe
        if(this.world.stain.type == Stain.TYPE_1) stainPixmap = Assets.bg;
        if(this.world.stain.type == Stain.TYPE_2) stainPixmap = Assets.bg;
        if(this.world.stain.type == Stain.TYPE_3) stainPixmap = Assets.bg;
        this.game.getGraphics().drawPixmap(stainPixmap, this.world.stain.x * 32, this.world.stain.y * 32);
    }

    /**
     * рисуем части хвоста
     */
    private void drawSnakeTail ()
    {
        Graphics g = this.game.getGraphics();
        // todo: get new image
        int len = this.world.snake.parts.size();
        for(int i = 1; i < len; i++) {
            SnakePart part = this.world.snake.parts.get(i);
            g.drawPixmap(Assets.bg, part.x * 32, part.y * 32);
        }
    }

    private void drawSnakeHead ()
    {
        // рисуем голову
        Pixmap headPixmap = null;
        // todo: make swith
        if(this.world.snake.direction == Snake.UP) headPixmap = Assets.bg;
        if(this.world.snake.direction == Snake.LEFT) headPixmap = Assets.bg;
        if(this.world.snake.direction == Snake.DOWN) headPixmap = Assets.bg;
        if(this.world.snake.direction == Snake.RIGHT) headPixmap = Assets.bg;

        // fixme: not local variales
        SnakePart head = this.world.snake.parts.get(0);
        int x = head.x * 32 + 16;
        int y = head.y * 32 + 16;

        this.game.getGraphics().drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y -headPixmap.getHeight() / 2);
    }

    private void drawReadyUI()
    {
        Graphics g = game.getGraphics();
        // g.drawPixmap(Assets.ready, 47, 100);
        g.drawText("Ready", 100, 100, 100, Color.RED);
        g.drawText("Tab screen no press start to begin you journey", 100, 150, 30, Color.RED);
    }

    private void drawRunningUI() {
       // Graphics g = game.getGraphics();
         /*g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);*/
        // g.drawText("Fire!!!!", 150, 200, 100, Color.RED);
    }
    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        g.drawText("Pause", 100, 200, 300, Color.RED);
    }
    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        /*g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);*/
        g.drawText("Game over", 100, 200, 300, Color.BLACK);
    }



    @Override
    public void pause() {
        Log.d("GameScree", "pause");
        if(state == GameState.Running) state = GameState.Paused;
        /*if(world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }*/
    }

    @Override
    public void resume() {
        Log.d("GameScree", "resume");
    }

    @Override
    public void dispose() {

    }


}
