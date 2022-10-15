package com.a530games.jackal.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;

import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.Input;
import com.a530games.framework.Screen;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Controller;
import com.a530games.jackal.ControllerEventHandler;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.Sprite;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.menu.GameOverLoseMenu;
import com.a530games.jackal.menu.BasicMenu;
import com.a530games.jackal.menu.MenuEventHandler;
import com.a530games.jackal.menu.PauseMenu;
import com.a530games.jackal.objects.Bullet;
import com.a530games.jackal.Settings;
import com.a530games.jackal.Sidebar;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.objects.ControllerPresenter;
import com.a530games.jackal.objects.enemies.Enemy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 *
 */
public class GameScreen extends Screen implements ControllerEventHandler, MenuEventHandler
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

    /**
     * Paint fro player hitbox rect
     */
    Paint playerHitBoxPaint;

    /**
     * Menu on lose level
     */
    BasicMenu gameOverLoseMenu;

    /**
     * Menu on pause
     */
    BasicMenu pauseMenu;

    /**
     * Mep screen width in pixels
     * used in draw
     */
    public int mapScreenWidthInPixels; // = 800

    /**
     * Mep screen height in pixels
     */
    public int mapScreenHeightInPixels; // = 640

    /**
     * Mep screen width in pixels
     * used in draw
     */
    private final int mapScreenWidthInBlocks; //  = 10;

    /**
     * Mep screen height in blocks
     */
    private final int mapScreenHeightInBlocks; //  = 10;

    //
    Paint titlePaint, subTitlePaint;

    //
    Sprite tempBoom;
    float boomTimer;

    public GameScreen(Game game, int playerStartHp)
    {
        super(game);

        // из размера экрана получаел количество квадратов на поле
        int screenWidth = this.game.getGraphics().getWidth();

        // count full blocks on screen
        this.mapScreenWidthInBlocks = (int) Math.floor(screenWidth / (float) Jackal.BLOCK_WIDTH);
        this.mapScreenHeightInBlocks = (int) Math.floor(this.game.getGraphics().getHeight() / (float) Jackal.BLOCK_HEIGHT);

        // calculate screen size
        this.mapScreenWidthInPixels = this.mapScreenWidthInBlocks * Jackal.BLOCK_WIDTH;
        this.mapScreenHeightInPixels = this.mapScreenHeightInBlocks * Jackal.BLOCK_HEIGHT;

        // create world width calculated map size
        this.world = new World(playerStartHp);

        Graphics g = this.game.getGraphics();

        // sidebar object
        // todo: fix magic numbers
        this.sidebar = new Sidebar(this.world, g.getWidth(),g.getHeight());

        // atache events handler to controller
        Controller controller = Jackal.getController();
        controller.setEventHandler(this);
        // Jackal.getController().setEventHandler(this);
        // Jackal.setController(this.controller);

        this.controllerPresenter = new ControllerPresenter(g.getWidth(), g.getHeight());
        this.controllerPresenter.bindController(controller);

        this.playerHitBoxPaint = new Paint();
        this.playerHitBoxPaint.setStyle(Paint.Style.STROKE);
        this.playerHitBoxPaint.setStrokeWidth(1);
        this.playerHitBoxPaint.setColor(Color.GREEN);

        this.pauseMenu = new PauseMenu(150, 200);
        this.pauseMenu.setEventHandler(this);

        this.gameOverLoseMenu = new GameOverLoseMenu(150, 200);
        this.gameOverLoseMenu.setEventHandler(this);

        this.titlePaint = new Paint();
        this.titlePaint.setColor(Color.RED);
        this.titlePaint.setTextAlign(Paint.Align.CENTER);
        this.titlePaint.setTextSize(150);

        this.subTitlePaint = new Paint();
        this.subTitlePaint.setColor(Color.RED);
        this.subTitlePaint.setTextAlign(Paint.Align.CENTER);
        this.subTitlePaint.setTextSize(30);


        // loading config from file
        // this.loadConfig();

        // Assets.music.setLooping(true);
        // Assets.music.setVolume(0.5f);
        // Assets.music.play();

        //

        //
        this.tempBoom = new Sprite(Assets.boom, 0, 0);
        this.tempBoom.setSpriteSize(96, 96);
        this.boomTimer = 0.2f;
    }

    /**
     * Loading config from file
     */
    private void loadConfig()
    {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(this.game.getFileIO().writeFile(".mrnom")));
            out.write(Boolean.toString(true));
            out.write("\n");
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(float deltaTime)
    {
        // todo: подмать какуюто абстрацию над этим всем делом
        TouchEventsCollection touchEvents = this.game.getInput().getTouchEvents();

        List<Input.KeyEvent> keyEvents = this.game.getInput().getKeyEvents();

        // universal wrap around controller
        Controller controller = Jackal.getController();

        // update by controller by controller input
        controller.updateByController(this.game.getInput().getController());

        // update controller by touch inputs
        controller.updateByTouchEvents(touchEvents, this.controllerPresenter);

        /*int keyEventsLength = keyEvents.size();
        if (keyEventsLength > 0) {
            for(int i = 0; i < keyEventsLength; i++) {
                Input.KeyEvent event = keyEvents.get(i);
                Log.d("key", "key pressed: "+String.valueOf(event.keyCode));
            }
        }*/

        if (this.state == GameState.Ready) this.updateReady(touchEvents, controller, deltaTime);
        if (this.state == GameState.Running) this.updateRunning(keyEvents, controller, deltaTime);
        if (this.state == GameState.Paused) this.updatePaused(touchEvents, controller, deltaTime);
        if (this.state == GameState.GameOver) this.updateGameOver(touchEvents, controller, deltaTime);

        this.updateSidebar();
    }

    private void updateReady(TouchEventsCollection touchEvents, Controller controller, float deltaTime)
    {
        // start drop
        // this.world.updateDrawPad();

        // if got fouch go to run
        // if(touchEvents.hasDown()) this.state = GameState.Running;
    }

    private void updateRunning(List<Input.KeyEvent> keyEvents, Controller controller, float deltaTime)
    {
        // [layer controls
        this.updatePlayer(controller, deltaTime);

        // обновление мира
        this.world.update(deltaTime);

        // tmpppp
        this.boomTimer -= deltaTime;
        if (this.boomTimer <= 0) {
            this.tempBoom.set(this.tempBoom.col+1, this.tempBoom.row);
            if (this.tempBoom.col >= 8) this.tempBoom.col = 0;
            this.boomTimer = 0.09f;
        }

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

    /**
     * Update player move and fire
     */
    private void updatePlayer(Controller controller, float deltaTime)
    {
        if (!this.world.player.isOnline()) return;

        // move player by touch events
        /*int len = touchEvents.size();
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
        }*/

        // update controller presenter by touch events
        // this.controllerPresenter.update(touchEvents);

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

            // это заглущка для другого упралвения
            if (this.world.playerFire()){
                if(Settings.soundEnabled) Assets.fire.play(1);
            }
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
        if (controller.isA() || controller.isB() || controller.isR1() || controller.isR2()) {
            if (this.world.playerFire()){
                if(Settings.soundEnabled) Assets.fire.play(1);
            }
        }

        // обработка поворота
        /*int keyEventsLength = keyEvents.size();
        for(int i = 0; i < keyEventsLength; i++) {
            Input.KeyEvent event = keyEvents.get(i);
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
        }*/
    }

    private void updatePaused(TouchEventsCollection touchEvents, Controller controller, float deltaTime)
    {

        this.pauseMenu.update(controller, touchEvents, deltaTime);
    }

    private void updateGameOver(TouchEventsCollection touchEvents, Controller controller, float deltaTime)
    {

        this.gameOverLoseMenu.update(controller, touchEvents, deltaTime);

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

    private void updateSidebar()
    {
        // this.sidebar.setPlayerHp(this.world.player.hp);

        this.sidebar.setFps(this.game.getRenderView().fps);
        // this.sidebar.setPlayerAngle(this.world.player.direction);
        // this.sidebar.setPlayerPos(Math.round(this.world.player.hitBox.left), Math.round(this.world.player.hitBox.top));
        // this.sidebar.setMapPos((int) Math.floor(this.world.map.position.x), (int) Math.floor(this.world.map.position.y));
    }

    @Override
    public void present(float deltaTime)
    {
        // clear bg
        Graphics g = game.getGraphics();
        // g.clear(Color.BLACK);

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

        //
        this.drawEnemies();

        //
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
        Graphics g = this.game.getGraphics();

        // если сайдбар не нуждается в отрисовке
        /*if (sidebar.isNeedRedraw()) {
            sidebar.reDraw();
        }*/

        // draw sibar image
        /*g.drawBitmap(
                this.sidebar.drawBitmap,
                this.sidebar.position.x,
                0);*/

        this.sidebar.draw(g);

        // this.game.getGraphics().drawText("fps: " + sidebar.fps, 650, 50, 20, Color.MAGENTA);
        // this.game.getGraphics().drawText("player: " + sidebar.playerX+ "x"+sidebar.playerY, 650, 80, 20, Color.MAGENTA);
        // this.game.getGraphics().drawText("angle: " + sidebar.playerAngle.x + "x" + sidebar.playerAngle.y, 650, 110, 20, Color.MAGENTA);
        // this.game.getGraphics().drawText("map: " + sidebar.mapX+ "x"+sidebar.mapY, 650, 140, 20, Color.MAGENTA);

        // рисуем линю отделяющею сайдбар
        /*if (this.game.isLandscape()) {
            g.drawLine(640, 100, 640, 300, Color.GREEN);
        }
        else {
            g.drawLine(100, 640, 300, 640, Color.GREEN);
        }*/

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
        // todo: fix magic numbers
        g.drawBitmap(
                this.world.map.drawBitmap,
                0,
                0,
                -1 * (int) Math.floor(this.world.map.position.x),
                -1 * (int )Math.floor(this.world.map.position.y),
                this.mapScreenWidthInPixels,
                640);


        // draw on map
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

        int top = this.world.map.screenTopPotion(row * Jackal.BLOCK_WIDTH);
        int left = this.world.map.screenLeftPotion(col * Jackal.BLOCK_HEIGHT);

        Graphics g = this.game.getGraphics();
        g.drawRect( left, top, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT, Color.YELLOW);

    }

    /**
     *
     */
    private void drawPlayer ()
    {
        Graphics g = this.game.getGraphics();

        // draw play
        this.world.player.present(g, this.world);

        // line of player angle
        this.drawPlayerAngle(g);

        // line of player turret angle
        this.drawPlayerTurretAngle(g);

        // player hitbox
        this.drawPlayerHitBox(g);
    }

    private void drawPlayerHitBox (Graphics g) {
        g.drawRect(this.world.player.getScreenDrawHitbox(this.world.map), this.playerHitBoxPaint);
    }

    /**
     * Draw line of layer turret angle
     * @param g Graphics object
     */
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

        this.drawTitle(g, "Ready");
        this.drawSecondTitle(g, "Press start to begin you journey");
    }

    private void drawRunningUI() {
        // Graphics g = game.getGraphics();
        /*g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);*/
        // g.drawText("Fire!!!!", 150, 200, 100, Color.RED);
    }

    private void drawPausedUI()
    {
        Graphics g = this.game.getGraphics();
        this.drawTitle(g, "Pause");

        this.pauseMenu.present(g);
    }

    private void drawGameOverUI()
    {
        Graphics g = game.getGraphics();
        // int textTopPosition = 150;

        this.drawTitle(g, "Game over");

        if (this.world.gameOverSuccess) {
            this.drawSecondTitle(g, "You win!");
            // g.drawText("press start to next level", 150, textTopPosition + 175, 50, Color.GREEN);
        }
        else {
            this.drawSecondTitle(g, "You you lose!");
            // g.drawText("Go cray, baby, phhh loh", 150, textTopPosition + 175, 50, Color.RED);
        }

        // draw lose menu
        this.gameOverLoseMenu.present(g);
    }

    private void drawTitle (Graphics g, String title) {
        g.drawText(title, (int) Math.ceil(this.mapScreenWidthInPixels * 0.5), 120, this.titlePaint);
    }

    private void drawSecondTitle (Graphics g, String title) {
        g.drawText(title, (int) Math.ceil(this.mapScreenWidthInPixels * 0.5), 170, this.subTitlePaint);
    }

    @Override
    public void onButtonDown(int keyCode)
    {
        Log.d("GameScreen", "onButtonDown");
    }

    @Override
    public void onButtonUp(int keyCode)
    {
        Log.d("GameScreen", "onButtonUp");

        if (keyCode == KeyEvent.KEYCODE_BUTTON_START)
        {
            // pause game
            if (this.state == GameState.Running) {
                this.state = GameState.Paused;
            }

            // start game
            if (this.state == GameState.Ready) {
                this.state = GameState.Running;
            }
        }
    }

    @Override
    public void onMenuItemSelect(String menuCode)
    {
        Log.d("GameScreen", "Menu select: "+menuCode);

        switch (menuCode) {
            case PauseMenu.PAUSE_MENU_CONTINUE:
                this.state = GameScreen.GameState.Running;
                break;
            case PauseMenu.PAUSE_MENU_RESTART:
                this.restartLevel(Jackal.getUerStartHp());
                break;
            case GameOverLoseMenu.GLM_RESTART:
                // restart wight use continue
                this.restartLevel(Jackal.pickContinue());
                break;
        }
    }

    private void restartLevel(int userHp){
        this.game.setScreen(new LoadingLevelScreen(this.game, userHp));
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
