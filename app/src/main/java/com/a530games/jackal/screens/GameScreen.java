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
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.math.Circle;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.map.Wall;
import com.a530games.jackal.objects.Bullet;
import com.a530games.jackal.Settings;
import com.a530games.jackal.Sidebar;
import com.a530games.jackal.Snake;
import com.a530games.jackal.SnakePart;
import com.a530games.jackal.Stain;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.objects.ControllerPresenter;
import com.a530games.jackal.objects.enemies.Enemy;

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

    ControllerPresenter controllerPresenter;

    // int oldScore = 0;
    // String score = "0";

    // paint for the hit box
    Paint hitBoxPaint;

    // paint for the hit box
    Paint otherHitBoxPaint;

    //
    Sprite tempBoom;
    float boomTimer;

    public GameScreen(Game game)
    {
        super(game);
        this.world = new World();
        this.sidebar = new Sidebar();

        this.controllerPresenter = new ControllerPresenter();
        this.controllerPresenter.bindController(this.game.getInput().getController());

        this.hitBoxPaint = new Paint();
        this.hitBoxPaint.setStyle(Paint.Style.STROKE);
        this.hitBoxPaint.setStrokeWidth(1);
        this.hitBoxPaint.setColor(Color.YELLOW);

        this.otherHitBoxPaint = new Paint();
        this.otherHitBoxPaint.setStyle(Paint.Style.STROKE);
        this.otherHitBoxPaint.setStrokeWidth(1);
        this.otherHitBoxPaint.setColor(Color.GREEN);

        // Assets.music.setLooping(true);
        // Assets.music.setVolume(0.5f);
        // Assets.music.play();

        //

        //
        this.tempBoom = new Sprite(Assets.boom, 0, 0);
        this.tempBoom.setSpriteSize(96, 96);
        this.boomTimer = 0.2f;
    }

    @Override
    public void update(float deltaTime)
    {
        // todo: подмать какуюто абстрацию над этим всем делом
        TouchEventsCollection touchEvents = this.game.getInput().getTouchEvents();

        List<Input.KeyEvent> keyEvents = this.game.getInput().getKeyEvents();

        //
        Controller c = this.game.getInput().getController();

        // update controller by touch events
        // c.updateByTouchEventsAndControllerMask();


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


    private void updateReady(TouchEventsCollection touchEvents, Controller controller)
    {
        // if got fouch go to run
        if(touchEvents.hasDown()) this.state = GameState.Running;
        // if(touchEvents.size() > 0) this.state = GameState.Running;

        // do touch event
        if (controller.isStart()) this.state = GameState.Running;
    }

    private void updateRunning(TouchEventsCollection touchEvents, List<Input.KeyEvent> keyEvents, Controller controller, float deltaTime)
    {
        // move player by touch events
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i); //.get(i);
            if (event == null) continue;

            // обработка паузы
            if(Input.TouchEvent.TOUCH_DOWN != event.type) continue;

            //
            if (this.controllerPresenter.topButton.isPointInside(event.x, event.y)) {
                this.world.player.move(0, -1, deltaTime, this.world);
            }
            if (this.controllerPresenter.rightButton.isPointInside(event.x, event.y)) {
                this.world.player.move(1, 0, deltaTime, this.world);
            }
            if (this.controllerPresenter.downButton.isPointInside(event.x, event.y)) {
                this.world.player.move(0, 1, deltaTime, this.world);
            }
            if (this.controllerPresenter.leftButton.isPointInside(event.x, event.y)) {
                this.world.player.move(-1, 0, deltaTime, this.world);
            }
            if (this.controllerPresenter.rightAButton.isPointInside(event.x, event.y)) {
                if (this.world.playerFire()){
                    if(Settings.soundEnabled) Assets.fire.play(1);
                }
            }
            if (this.controllerPresenter.rightBButton.isPointInside(event.x, event.y)) {
                if (this.world.playerFire()){
                    if(Settings.soundEnabled) Assets.fire.play(1);
                }
            }


        }

        // update controller presenter by touch events
        this.controllerPresenter.update(touchEvents);

        /*if (controller.isStart()) {
            this.state = GameState.Paused;
            return;
        }*/

        // move player
        Vector2 leftStick = controller.getLeftStickDirection();
        if (leftStick.x != 0 || leftStick.y != 0) {
            this.world.player.move(leftStick, deltaTime, this.world);
        }

        // set player turret angle
        // todo: has check config controller
        Vector2 rightStick = controller.getRightStickDirection();
        if (rightStick.x != 0 || rightStick.y != 0) {
            // this.world.player.turret.set(rightStick);
            this.world.player.setTurretAngle(rightStick);
        }


        // move player
        /*if (controller.isTopButtonDown())  {
            if (controller.isRightButtonDown()) {
                this.world.player.move(1, -1, deltaTime);
            }
            else if (controller.isLeftButtonDown()) {
                this.world.player.move(-1, -1, deltaTime);
            }
            else {
                this.world.player.move(0, -1, deltaTime);
            }
        }
        else if (controller.isBottomButtonDown())  {
            if (controller.isRightButtonDown()) {
                this.world.player.move(1, 1, deltaTime);
            }
            else if (controller.isLeftButtonDown()) {
                this.world.player.move(-1, 1, deltaTime);
            }
            else {
                this.world.player.move(0, 1, deltaTime);
            }
        }
        else {
            if (controller.isLeftButtonDown()) {
                this.world.player.move(-1, 0, deltaTime);
            }
            if (controller.isRightButtonDown()) {
                this.world.player.move(1, 0, deltaTime);
            }
        }*/

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
        if (controller.isA() || controller.isR1() || controller.isR2()) {
            if (this.world.playerFire()){
                if(Settings.soundEnabled) Assets.fire.play(1);
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

        // tmpppp
        this.boomTimer -= deltaTime;
        if (this.boomTimer <= 0) {
            this.tempBoom.set(this.tempBoom.col+1, this.tempBoom.row);
            if (this.tempBoom.col >= 8) this.tempBoom.col = 0;
            this.boomTimer = 0.09f;
        }

        // fixme: при завершении f[s выводить не будем
        this.sidebar.setFps(this.game.getRenderView().fps);
        this.sidebar.setPlayerAngle(this.world.player.direction);
        this.sidebar.setPlayerPos(Math.round(this.world.player.hitBox.left), Math.round(this.world.player.hitBox.top));
        this.sidebar.setMapPos((int) Math.floor(this.world.map.x), (int) Math.floor(this.world.map.y));

        // обновление боковой информации
        // this.sidebar.update(deltaTime);

        //
        if(this.world.gameOver) {
            this.state = GameState.GameOver;
        }

        if(this.world.gameOverSuccess) {
            this.state = GameState.GameOver;
        }


        // обновление рекорда
        /*if(this.oldScore != this.world.score) {
            this.oldScore = this.world.score;
            this.score = "" + this.oldScore;
            // if(Settings.soundEnabled) Assets.eat.play(1);
        }*/
    }

    private void updatePaused(TouchEventsCollection touchEvents, Controller controller)
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
        // if(touchEvents.size() > 0) this.state = GameState.Running;
        /*if (controller.isStart()) {
            this.state = GameState.Running;
        }*/
    }

    private void updateGameOver(TouchEventsCollection touchEvents)
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

        this.drawTouchPoints(g);

        // draw controller onscreen controller
        this.controllerPresenter.draw(g);

        // draw score
        // this.drawText(g, score, g.getWidth() / 2 - score.length()*20 / 2, g.getHeight() - 42);
    }

    /**
     * Draw touch points on screen
     */
    private void drawTouchPoints (Graphics g)
    {
        TouchEventsCollection touchEvents = this.game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i); //.get(i);
            if (event == null) continue;
            if (event.type != Input.TouchEvent.TOUCH_DOWN) continue;
            g.drawCircle(event.x, event.y, 50, Color.GREEN);
        }

    }

    private void drawWorld(World world)
    {
        // draw backend
        this.drawMap();

        // draw player
        this.drawPlayer();

        this.drawEnemies();

        this.drawBullets();

        //
        // this.drawMapTop();

        this.drawBlow();

        // this.drawSky();
        // map top level
        this.drawMapTopLayout();

        // map object hitboxes
        this.drawMapObjectsHitBoxes();

        //this.drawSky();
    }

    private void drawSidebar(Sidebar sidebar)
    {
        // todo: make side bar drawwight object
        Graphics g = this.game.getGraphics();
        //g.drawLine(0, 0, this.game.getGraphics().getWidth(), this.game.getGraphics().getHeight(), Color.YELLOW);

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

        // this.sidebar.setRedraw();

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

        // draw back part of map
        g.drawBitmap(
                this.world.map.drawBitmap,
                0,
                0,
                -1 * (int) Math.floor(this.world.map.x),
                -1 * (int )Math.floor(this.world.map.y),
                640,
                640);


        for (int row = this.world.map.drawMinRow; row < this.world.map.drawMaxRow; row++) {
            for (int col = this.world.map.drawMinCol; col < this.world.map.drawMaxCol; col++)
            {
                MapCell c = this.world.map.fields[row][col];
                if (c == null) continue;

                c.draw(g, this.world.map);
            }
        }

        // this.drawMapNet(g);

        // this.drawActiveCell();
    }

    /**
     * Hit boxes for map objects
     */
    private void drawMapObjectsHitBoxes()
    {
        Graphics g = this.game.getGraphics();

        for (int row = this.world.map.drawMinRow; row < this.world.map.drawMaxRow; row++) {
            for (int col = this.world.map.drawMinCol; col < this.world.map.drawMaxCol; col++)
            {
                MapCell c = this.world.map.fields[row][col];
                if (c == null) continue;

                c.drawHitBox(g, this.world.map);
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

    /**
     * Draw cell on
     */
    private void drawActiveCell ()
    {
        int row = this.world.map.getRowByTop(this.world.player.hitBox.top);
        int col = this.world.map.getColByLeft(this.world.player.hitBox.left);

        int top = this.world.map.screenTopPotion(row * Map.SPRITE_HEIGHT);
        int left = this.world.map.screenLeftPotion(col * Map.SPRITE_WIDTH);

        Graphics g = this.game.getGraphics();
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

        // this.drawPlayerHitBox(g, playerScreenX, playerSourceY);

        /*
         * 12 = 0.5 * 64 (player sprite width) - 40 (player hitbox width)
         */
        g.drawPixmap(
            this.world.player.sprite.image,
            Math.round(playerScreenX) - 12, //
            Math.round(playerSourceY) - 12,
                this.world.player.sprite.getLeft(),
                this.world.player.sprite.getTop(),
                this.world.player.sprite.width, // 64,
            this.world.player.sprite.height); // 64);

        // draw turret
        g.drawPixmap(
                this.world.player.gun.image,
                Math.round(playerScreenX) - 12, //
                Math.round(playerSourceY) - 12,
                this.world.player.gun.getLeft(),
                this.world.player.gun.getTop(),
                this.world.player.gun.width, // 64,
                this.world.player.gun.height); // 64);

        this.drawPlayerAngle(g);

        this.drawPlayerTurretAngle(g);

        // player hitbox
        this.drawPlayerHitBox(g);
    }

    private void drawPlayerHitBox (Graphics g) {
        g.drawRect(this.world.player.getScreenDrawHitbox(this.world.map), this.otherHitBoxPaint);
    }

    /**
     * Draw line of layer turret angle
     * @param g Graphics object
     */
    // private void drawPlayerAngle (Graphics g, int playerScreenX, int playerScreenY)
    private void drawPlayerAngle (Graphics g) // , int playerScreenX, int playerScreenY)
    {
        int centerX = this.world.map.screenLeftPotion(this.world.player.hitBox.getCenterLeft());
        int centerY = this.world.map.screenTopPotion(this.world.player.hitBox.getCenterTop());

        g.drawLine(
                centerX,
                centerY,
                (int) Math.ceil(centerX + (this.world.player.direction.x * 50)),
                (int) Math.ceil(centerY + (this.world.player.direction.y * 50)),
                Color.MAGENTA);
    }

    /**
     * Draw line of layer turret angle
     * @param g Graphics object
     */
    // private void drawPlayerAngle (Graphics g, int playerScreenX, int playerScreenY)
    private void drawPlayerTurretAngle (Graphics g) // , int playerScreenX, int playerScreenY)
    {
        // int centerX = (int) Math.round(playerScreenX + (0.5 * this.world.player.hitBox.getWidth()));
        // int centerY = (int) Math.round(playerScreenY  + (0.5 * this.world.player.hitBox.getHeight()));
        int centerX = this.world.map.screenLeftPotion(this.world.player.hitBox.getCenterLeft());
        int centerY = this.world.map.screenTopPotion(this.world.player.hitBox.getCenterTop());

        g.drawLine(
                centerX,
                centerY,
                (int) Math.ceil(centerX + (this.world.player.turret.x * 50)),
                (int) Math.ceil(centerY + (this.world.player.turret.y * 50)),
                Color.GREEN);
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
                // if (enemy == null) continue;

                enemy.present(g, this.world);
            }
        }
    }

    private void drawBullets()
    {
        Graphics g = this.game.getGraphics();

        // player bullets
        int bulletSize = this.world.bullets.size();
        if (bulletSize > 0) {
            for (int i = 0; i < bulletSize; i++)
            {
                Bullet b = this.world.bullets.get(i);
                if (b.isOut()) continue;

                // todo: make bullet sprite
                g.drawPixmap(
                        Assets.bullet2,
                        this.world.map.screenLeftPotion(b.getX()) - 32,
                        this.world.map.screenTopPotion(b.getY()) - 10, // <- ammmm
                        0,
                        0,
                        64,
                        64
                );

                this.drawPlayerShotBlow(g, b);
            }
        }

        // enemy bullets
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

    private void drawPlayerShotBlow(Graphics g, Bullet b)
    {
        // drww blow
        if (b.timer <= 0.05f) {
            g.drawPixmap(
                    Assets.playerFire,
                    this.world.map.screenLeftPotion(b.startMapPosition.left) - 16,
                    this.world.map.screenTopPotion(b.startMapPosition.top) - 16,
                    0,
                    0,
                    32,
                    32
            );
        }
        else if (0.05f < b.timer && b.timer <= 0.1f) {
            g.drawPixmap(
                    Assets.playerFire,
                    this.world.map.screenLeftPotion(b.startMapPosition.left) - 16,
                    this.world.map.screenTopPotion(b.startMapPosition.top) - 16,
                    32,
                    0,
                    32,
                    32
            );
        }
        else if (0.1f < b.timer && b.timer <= 0.15f) {
            g.drawPixmap(
                    Assets.playerFire,
                    this.world.map.screenLeftPotion(b.startMapPosition.left) - 16,
                    this.world.map.screenTopPotion(b.startMapPosition.top) - 16,
                    64,
                    0,
                    32,
                    32
            );
        }
    }

    private void drawBlow()
    {
        Graphics g = this.game.getGraphics();
        g.drawPixmap(
                this.tempBoom.image,
                this.world.map.screenLeftPotion(400),
                this.world.map.screenTopPotion(900),
                this.tempBoom.getLeft(),
                this.tempBoom.getTop(),
                this.tempBoom.width,
                this.tempBoom.height
        );
    }

    /**
     * Draw map objects top layer checkbox
     */
    private void drawMapTopLayout()
    {
        Graphics g = this.game.getGraphics();

        for (int row = this.world.map.drawMinRow; row < this.world.map.drawMaxRow; row++) {
            for (int col = this.world.map.drawMinCol; col < this.world.map.drawMaxCol; col++)
            {
                MapCell c = this.world.map.fields[row][col];
                if (c == null) continue;

                c.drawTopLayout(g, this.world.map);
            }
        }
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
    private void drawGameOverUI()
    {
        Graphics g = game.getGraphics();
        /*g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);*/
        g.drawText("Game over", 150, 200, 100, Color.GREEN);
        g.drawText("You win!", 150, 300, 100, Color.GREEN);
        g.drawText("press start to next level", 150, 375, 50, Color.GREEN);
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
