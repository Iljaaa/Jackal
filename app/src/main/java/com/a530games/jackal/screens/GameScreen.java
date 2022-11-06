package com.a530games.jackal.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.Input;
import com.a530games.framework.Screen;
import com.a530games.framework.TouchEventsCollection;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.helpers.SaveBitmapToFile;
import com.a530games.framework.math.Vector2F;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Controller;
import com.a530games.jackal.ControllerEventHandler;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.levels.Level;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapObject;
import com.a530games.jackal.menu.GameOverLoseMenu;
import com.a530games.jackal.menu.GameOverLoseMenuWrap;
import com.a530games.jackal.menu.MenuEventHandler;
import com.a530games.jackal.menu.PauseMenu;
import com.a530games.jackal.menu.PauseMenuWrap;
import com.a530games.jackal.objects.Bullet;
import com.a530games.jackal.Settings;
import com.a530games.jackal.Sidebar;
import com.a530games.jackal.World;
import com.a530games.jackal.objects.ControllerPresenter;
import com.a530games.jackal.objects.enemies.Enemy;

import java.util.List;
import java.util.ListIterator;

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

    // start on running
    GameState state = GameState.Running;

    /**
     * Current level code
     */
    private String levelCode;

    /**
     * World object
     */
    World world;

    /**
     * Camera object
     */
    Camera2D camera;

    Sidebar sidebar;

    ControllerPresenter controllerPresenter;

    // int oldScore = 0;
    // String score = "0";


    /**
     * Menu on lose level
     */
    GameOverLoseMenuWrap gameOverLoseMenu;

    /**
     * Menu on pause
     */
    PauseMenuWrap pauseMenu;

    /**
     * Mep screen width in pixels
     * used in draw
     */
    public int mapScreenWidthInPixels; // = 800

    /**
     * Mep screen height in pixels
     */
    public int mapScreenHeightInPixels; // = 640

    /*
     * Mep screen width in pixels
     * used in draw
     */
    // private final int mapScreenWidthInBlocks; //  = 10;

    /*
     * Mep screen height in blocks
     */
    // private final int mapScreenHeightInBlocks; //  = 10;


    /**
     * Paint fro player hitbox rect
     */
    Paint hitBoxPaint;

    /**
     * Screen titles paint
     */
    Paint titlePaint, subTitlePaint;

    /**
     * Camera triangle paint
     */
    Paint cameraPaint;

    public GameScreen(Game game, int playerStartHp)
    {
        super(game);

        // из размера экрана получаел количество квадратов на поле
        Graphics g = this.game.getGraphics();

        //
        int screenWidth = g.getFrameBufferWidth();

        // count FULL blocks on screen
        // this.mapScreenWidthInBlocks = (int) Math.floor(screenWidth / (float) Jackal.BLOCK_WIDTH);
        // this.mapScreenHeightInBlocks = (int) Math.floor(this.game.getGraphics().getFrameBufferHeight() / (float) Jackal.BLOCK_HEIGHT);

        // calculate screen size
        this.mapScreenWidthInPixels = screenWidth;
        this.mapScreenHeightInPixels = g.getFrameBufferHeight();
        // this.mapScreenWidthInPixels = this.mapScreenWidthInBlocks * Jackal.BLOCK_WIDTH;
        // this.mapScreenHeightInPixels = this.mapScreenHeightInBlocks * Jackal.BLOCK_HEIGHT;

        // create world width calculated map size
        this.world = new World(playerStartHp, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);

        // camera object
        this.camera = new Camera2D(
                this.mapScreenWidthInPixels,
                this.mapScreenHeightInPixels,
                Jackal.BLOCK_WIDTH,
                Jackal.BLOCK_HEIGHT
        );

        // sidebar object
        int screenHeight = g.getFrameBufferHeight();
        this.sidebar = new Sidebar(this.world, this.camera, screenWidth, screenHeight);

        // atache events handler to controller
        Controller controller = Jackal.getController();
        controller.setEventHandler(this);
        // Jackal.getController().setEventHandler(this);
        // Jackal.setController(this.controller);

        this.controllerPresenter = new ControllerPresenter(screenWidth, screenHeight);
        this.controllerPresenter.bindController(controller);

        this.pauseMenu = new PauseMenuWrap(
                (int) (screenWidth * 0.5) - 200, // 200 its half of menu wisth
                200
        );
        this.pauseMenu.setEventHandler(this);

        this.gameOverLoseMenu = new GameOverLoseMenuWrap(
                (int) (screenWidth * 0.5) - 200, // 200 its half of menu wisth
                200
        );
        this.gameOverLoseMenu.setEventHandler(this);

        ///////////////////

        this.hitBoxPaint = new Paint();
        this.hitBoxPaint.setStyle(Paint.Style.STROKE);
        this.hitBoxPaint.setStrokeWidth(1);
        this.hitBoxPaint.setColor(Color.GREEN);

        this.titlePaint = new Paint();
        this.titlePaint.setColor(Color.RED);
        this.titlePaint.setTextAlign(Paint.Align.CENTER);
        this.titlePaint.setTextSize(150);

        this.subTitlePaint = new Paint();
        this.subTitlePaint.setColor(Color.RED);
        this.subTitlePaint.setTextAlign(Paint.Align.CENTER);
        this.subTitlePaint.setTextSize(30);

        this.cameraPaint = new Paint();
        this.cameraPaint.setColor(Color.GREEN);
        this.cameraPaint.setStyle(Paint.Style.STROKE);
        this.cameraPaint.setStrokeWidth(2);
    }

    /**
     * On level loaded need update data
     */
    public void initByLevel(Level level)
    {
        this.levelCode = level.getCode();

        // init world and map
        this.world.initByLevel(
                level,
                this.game.getGraphics()
        );

        // update camera limits
        this.camera.updateLimitsByMapSize(level.getMapCols(), level.getMapRows());

        // move camera
        // Map.Cell dropPadCell = level.getPlayerDropPointCell();
        // Vector2F centerDropPadCell = this.world.map.startCell(dropPadCell);
        this.camera.setPosition(this.world.map.startCell.getCenter());

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

        // update camera position
        if (this.world.player.isOnline()) {
            this.camera.followByPoint(this.world.player.hitBox.rect.left, this.world.player.hitBox.rect.top);
        }

        // обновление мира
        this.world.update(deltaTime);

        // обновление боковой информации
        // this.sidebar.update(deltaTime);

        //
        if(this.world.gameOver || this.world.gameOverSuccess) {
            this.setGameOver();
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
        Vector2F leftStick = controller.getLeftStickDirection();
        if (leftStick.x != 0 || leftStick.y != 0) {
            this.world.player.drive(leftStick, deltaTime, this.world);
        }

        // set player turret angle
        // todo: has check config controller
        Vector2F rightStick = controller.getRightStickDirection();
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

        // draw rect
        Rect viewRect = this.camera.getViewRectInBlocks(this.world.map.getCellByPosition(this.camera.position.x, this.camera.position.y));

        // рисуем имр
        this.presentWorld(g, viewRect);

        // рисуем сайдбар
        this.presentSidebar(this.sidebar);

        if (state == GameState.Running) this.drawRunningUI();
        if (state == GameState.Ready) this.drawReadyUI();
        if (state == GameState.Paused) this.drawPausedUI();
        if (state == GameState.GameOver) this.drawGameOverUI();

        // temp item
        // this.presentTouchPoints(g);

        // draw controller onscreen controller
        this.controllerPresenter.draw(g);

        // draw camera position
        // this.presentCamera(g);

        // draw score
        // this.drawText(g, score, g.getWidth() / 2 - score.length()*20 / 2, g.getHeight() - 42);
    }

    /**
     * Draw touch points on screen
     */
    private void presentTouchPoints(Graphics g)
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

    /**
     * Draw camera
     */
    private void presentCamera(Graphics g)
    {
        // its alveys in center screen

        int screenX = this.camera.screenLeft(this.camera.position.x);
        int screenY = this.camera.screenTop(this.camera.position.y);

        // int screenX = this.world.map.screenLeftPotion(this.camera.position.x);
        // int screenY = this.world.map.screenTopPotion(this.camera.position.y);

        g.drawLine(screenX, screenY, screenX - 20, screenY + 20, this.cameraPaint);
        g.drawLine(screenX, screenY, screenX + 20, screenY + 20, this.cameraPaint);
        g.drawLine(screenX - 20, screenY + 20, screenX + 20, screenY + 20, this.cameraPaint);

        // screen center
        int screenCenterX = this.mapScreenWidthInPixels / 2;
        int screenCenterY = this.mapScreenHeightInPixels / 2;

        g.drawLine(screenCenterX, 0 , screenCenterX, this.mapScreenHeightInPixels, Color.GRAY);
        g.drawLine(0, screenCenterY , this.mapScreenWidthInPixels, screenCenterY, Color.GRAY);
    }

    /**
     * Draw player, map, enemies and bullets
     */
    private void presentWorld(Graphics g, Rect drawREct)
    {
        // draw backend
        this.drawMap(g, drawREct);

        // draw player
        this.drawPlayer();

        //
        this.presentEnemies(g);

        // player bullets
        this.presentPlayerBullets(g);

        // enemy bullets
        this.presentEnemyBullets(g);

        // map top level
        this.drawMapTopLayout();

        // map object hitboxes
        this.drawMapObjectsHitBoxes();
    }

    private void presentSidebar(Sidebar sidebar)
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

        this.sidebar.present(g);

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

    /**
     * Draw background and map objects
     */
    protected void drawMap(Graphics g, Rect drawREct)
    {

        // draw back part of map
        /*g.drawBitmap(
                this.world.map.drawBitmap,
                0, // <- plus delta screen
                0,
                -1 * (int) Math.floor(this.world.map.position.x),
                -1 * (int )Math.floor(this.world.map.position.y),
                this.mapScreenWidthInPixels,
                this.mapScreenHeightInPixels);*/

        g.drawBitmap(
                this.world.map.drawBitmap,
                0, // <- plus delta screen
                0,
                (int) (this.camera.position.x - (this.mapScreenWidthInPixels / 2)),
                (int) (this.camera.position.y - (this.mapScreenHeightInPixels / 2)),
                this.mapScreenWidthInPixels,
                this.mapScreenHeightInPixels
        );

        // draw object on map
        // todo: move this code inside map
        // for (int row = this.world.map.drawMinRow; row < this.world.map.drawMaxRow; row++) {
//            for (int col = this.world.map.drawMinCol; col < this.world.map.drawMaxCol; col++)
        for (int row = Math.max(drawREct.top, 0); row < Math.min(drawREct.bottom, this.world.map.mapRows); row++) {
            for (int col = Math.max(drawREct.left, 0); col < Math.min(drawREct.right, this.world.map.mapCols); col++)
            {
                MapObject c = this.world.map.fields[row][col];
                if (c == null) continue;

                // c.draw(g, this.worldm.map);
                c.draw(g, this.camera);
            }
        }

        // highlight player cell
        // this.world.map.highlightCellByPoint(g, this.world.player.hitBox.getCenterX(), this.world.player.hitBox.getCenterY());

        // draw player active cell
        /*Map.Cell playerCell = this.world.map.getCellByPosition(
                this.world.player.hitBox.getCenterX(),
                this.world.player.hitBox.getCenterY()
        );

        this.drawActiveCell(g, playerCell);*/
    }

    /**
     * Draw map objects top layer checkbox
     */
    private void drawMapTopLayout()
    {
        Graphics g = this.game.getGraphics();

        // get player position
        Map.Cell playerCell = this.world.map.getCellByPosition(
                this.world.player.hitBox.getCenterX(),
                this.world.player.hitBox.getCenterY()
        );

        // Rect viewRect = this.getVisibleRectInBlocks(playerCell);

        // get camera view rect
        Rect viewRect = this.camera.getViewRectInBlocks(playerCell);

        // for (int row = this.world.map.drawMinRow; row < this.world.map.drawMaxRow; row++) {
        //  for (int col = this.world.map.drawMinCol; col < this.world.map.drawMaxCol; col++)
        for (int row =  Math.max(viewRect.top, 0); row < viewRect.bottom && row < this.world.map.mapRows; row++)
        {
            for (int col = Math.max(viewRect.left, 0); col < viewRect.right && col < this.world.map.mapCols; col++)
            {
                MapObject c = this.world.map.fields[row][col];
                if (c == null) continue;

                // c.drawTopLayout(g, this.world.map);
                c.drawTopLayout(g, this.camera);
            }
        }
    }

    /*
     * @param center
     * @return

    private Rect getVisibleRectInBlocks(Map.Cell center)
    {
        Rect r = new Rect(
                center.col - 3,
                center.row - 3,
                center.col + 3,
                center.row + 3
        );

        if (r.left < 0) r.left = 0;
        if (r.top < 0) r.top = 0;

        if (r.right > this.world.map.mapCols) r.left = this.world.map.mapCols;
        if (r.bottom > this.world.map.mapRows) r.bottom = this.world.map.mapRows;

        return r;
    }*/

    /**
     * Hit boxes for map objects
     */
    private void drawMapObjectsHitBoxes()
    {
        Graphics g = this.game.getGraphics();

        // get player cell

        // draw all hitboxes
        // for (int row = this.world.map.drawMinRow; row < this.world.map.drawMaxRow; row++) {
            // for (int col = this.world.map.drawMinCol; col < this.world.map.drawMaxCol; col++)
        for (int row = 0; row < this.world.map.mapRows; row++) {
            for (int col = 0; col < this.world.map.mapCols; col++) {
                MapObject c = this.world.map.fields[row][col];
                if (c == null) continue;

                // c.drawHitBox(g, this.world.map);
                c.drawHitBox(g, this.camera);
            }
        }
    }

    /*private void drawMapNet (Graphics g)
    {
        g.drawLine(640, 0, 640, 640, Color.WHITE);

        for (int i = 1; i <= 9; i++) {
            g.drawLine(i * 64, 0, i * 64, 640, Color.GRAY);
        }

        for (int i = 1; i <= 9; i++) {
            g.drawLine(0, i * 64, 640, i * 64, Color.GRAY);
        }
    }*/

    /**
     * Highlight active cell
     */
    private void drawActiveCell (Graphics g, Map.Cell cell)
    {
        // int row = Map.getRowByTop(this.world.player.hitBox.top);
        // int col = Map.getColByLeft(this.world.player.hitBox.left);

        // active cell
        // Map.Cell playerCell = this.world.map.getCellByPoint(this.world.player.getHitBox().getCenter());

        // int top = this.world.map.screenTopPotion(this.world.map.getTopByRow(playerCell.row));
        // int left = this.world.map.screenLeftPotion(this.world.map.getLeftByCol(playerCell.col));

        int top = this.camera.screenTop(this.world.map.getTopByRow(cell.row));
        int left = this.camera.screenLeft(this.world.map.getLeftByCol(cell.col));

        g.drawRect(
                left, // this.world.map.screenLeftPotion(playerCell.col),  // left,
                top, // this.world.map.screenTopPotion(playerCell.row), // top,
                this.world.map.blockWidth, // Jackal.BLOCK_HEIGHT,
                this.world.map.blockHeight, // Jackal.BLOCK_HEIGHT,
                this.world.map.activeCellPaint
        );
    }

    /**
     *
     */
    private void drawPlayer ()
    {
        Graphics g = this.game.getGraphics();

        // draw play
        // this.world.player.present(g, this.world);
        this.world.player.present(
                g,
                this.camera.screenLeft(this.world.player.hitBox.rect.left),
                this.camera.screenTop(this.world.player.hitBox.rect.top)
        );

        // line of player angle
        // this.drawPlayerAngle(g);

        // line of player turret angle
        // this.drawPlayerTurretAngle(g);

        // player hitbox
        // this.drawHitBox(g, this.world.player.hitBox);
    }

    /**
     * Draw line of layer turret angle
     * @param g Graphics object
     */
    private void drawPlayerAngle (Graphics g) // , int playerScreenX, int playerScreenY)
    {
        // int centerX = this.world.map.screenLeftPotion(this.world.player.hitBox.getCenterX());
        // int centerY = this.world.map.screenTopPotion(this.world.player.hitBox.getCenterY());

        int centerX = this.camera.screenLeft(this.world.player.hitBox.getCenterX());
        int centerY = this.camera.screenTop(this.world.player.hitBox.getCenterY());

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
        // int centerX = this.world.map.screenLeftPotion(this.world.player.hitBox.getCenterX());
        // int centerY = this.world.map.screenTopPotion(this.world.player.hitBox.getCenterY());

        int centerX = this.camera.screenLeft(this.world.player.hitBox.getCenterX());
        int centerY = this.camera.screenTop(this.world.player.hitBox.getCenterY());

        g.drawLine(
                centerX,
                centerY,
                (int) Math.ceil(centerX + (this.world.player.turret.x * 50)),
                (int) Math.ceil(centerY + (this.world.player.turret.y * 50)),
                Color.GREEN);
    }

    /**
     * Present enemies
     */
    private void presentEnemies(Graphics g)
    {

        ListIterator<Enemy> listIterator = this.world.enemies.listIterator();
        while (listIterator.hasNext())
        {
            Enemy enemy = listIterator.next();

            // enemy.present(g, this.world);
            enemy.present(g, this.camera);

            // draw hitbox
            // HitBox hitbox = enemy.getHitBox();
            // if (hitbox != null) this.drawHitBox(g, hitbox);
        }

        //
        /*int enemiesSize = this.world.enemies.size();
        if (enemiesSize > 0) {
            for (int i = 0; i < enemiesSize; i++)
            {
                Enemy enemy = this.world.enemies.get(i);
                // if (enemy == null) continue;

                // enemy.present(g, this.world);
                enemy.present(g, this.camera);

                // draw hitbox
                HitBox hitbox = enemy.getHitBox();
                if (hitbox != null) this.drawHitBox(g, hitbox);
            }
        }*/
    }

    /**
     * Draw player bullets
     */
    private void presentPlayerBullets(Graphics g)
    {
        ListIterator<Bullet> listIterator = this.world.playerBullets.listIterator();
        while (listIterator.hasNext())
        {
            Bullet b = listIterator.next();
            // if (b.isOut()) continue;

            // todo: make bullet sprite
            g.drawPixmap(
                    Assets.bullet2,
                    this.camera.screenLeft(b.getX()) - 8,
                    this.camera.screenTop(b.getY()) - 8 // <- ammmm
            );
        }

        // player bullets
        /*int bulletSize = this.world.bullets.size();
        if (bulletSize > 0) {
            for (int i = 0; i < bulletSize; i++)
            {
                Bullet b = this.world.bullets.get(i);
                if (b.isOut()) continue;

                // todo: make bullet sprite
                g.drawPixmap(
                        Assets.bullet2,
                        // this.world.map.screenLeftPotion(b.getX()) - 8,
                        // this.world.map.screenTopPotion(b.getY()) - 8, // <- ammmm
                        this.camera.screenLeft(b.getX()) - 8,
                        this.camera.screenTop(b.getY()) - 8, // <- ammmm
                        0,
                        0,
                        16,
                        16
                );

                this.drawPlayerShotBlow(g, b);
            }
        }*/
    }

    /**
     * Draw enemy bullets
     */
    private void presentEnemyBullets(Graphics g)
    {
        ListIterator<Bullet> listIterator = this.world.enemyBullets.listIterator();
        while (listIterator.hasNext()) {
            Bullet b = listIterator.next();
            // if (b.isOut()) continue;

            g.drawPixmap(
                    Assets.bullet,
                    // this.world.map.screenLeftPotion(b.getX()) - 8,
                    // this.world.map.screenTopPotion(b.getY()) - 8, // <- ammmm
                    this.camera.screenLeft(b.getX()) - 3,
                    this.camera.screenTop(b.getY()) - 3
            );
        }


        // enemy bullets
        /*int enemyBulletsSize = this.world.enemyBullets.size();
        if (enemyBulletsSize > 0) {
            for (int i = 0; i < enemyBulletsSize; i++)
            {
                Bullet b = this.world.enemyBullets.get(i);
                if (b.isOut()) continue;

                g.drawPixmap(
                        Assets.bullet,
                        // this.world.map.screenLeftPotion(b.getX()) - 8,
                        // this.world.map.screenTopPotion(b.getY()) - 8, // <- ammmm
                        this.camera.screenLeft(b.getX()) - 8,
                        this.camera.screenTop(b.getY()) - 8
                );
            }
        }*/
    }

    private void drawPlayerShotBlow(Graphics g, Bullet b)
    {
        // drww blow
        if (b.liveTime <= 0.05f) {
            g.drawPixmap(
                    Assets.playerFire,
                    // this.world.map.screenLeftPotion(b.startMapPosition.x) - 16,
                    // this.world.map.screenTopPotion(b.startMapPosition.y) - 16,
                    this.camera.screenLeft(b.startMapPosition.x) - 16,
                    this.camera.screenTop(b.startMapPosition.y) - 16,
                    0,
                    0,
                    32,
                    32
            );
        }
        else if (0.05f < b.liveTime && b.liveTime <= 0.1f) {
            g.drawPixmap(
                    Assets.playerFire,
                    // this.world.map.screenLeftPotion(b.startMapPosition.x) - 16,
                    // this.world.map.screenTopPotion(b.startMapPosition.y) - 16,
                    this.camera.screenLeft(b.startMapPosition.x) - 16,
                    this.camera.screenTop(b.startMapPosition.y) - 16,
                    32,
                    0,
                    32,
                    32
            );
        }
        else if (0.1f < b.liveTime && b.liveTime <= 0.15f) {
            g.drawPixmap(
                    Assets.playerFire,
                    // this.world.map.screenLeftPotion(b.startMapPosition.x) - 16,
                    // this.world.map.screenTopPotion(b.startMapPosition.y) - 16,
                    this.camera.screenLeft(b.startMapPosition.x) - 16,
                    this.camera.screenTop(b.startMapPosition.y) - 16,
                    64,
                    0,
                    32,
                    32
            );
        }
    }

    private void drawHitBox(Graphics g, HitBox hitBox)
    {
        // g.drawRect(this.world.player.getScreenDrawHitbox(this.world.map), this.playerHitBoxPaint);

        g.drawRect(
                // this.world.map.screenLeftPotion(this.world.player.hitBox.rect.left),
                // this.world.map.screenTopPotion(this.world.player.hitBox.rect.top),
                this.camera.screenLeft(hitBox.rect.left),
                this.camera.screenTop(hitBox.rect.top),
                (int) hitBox.rect.width(),
                (int) hitBox.rect.height(),
                this.hitBoxPaint
        );
    }

    ///////////////

    private void drawReadyUI()
    {
        Graphics g = game.getGraphics();

        this.drawTitle(g, "Ready");
        this.drawSecondTitle(g, "Press start to begin you journey");
    }

    private void drawRunningUI()
    {
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


        if (this.world.gameOverSuccess) {
            this.drawTitle(g, "You win!");
        }
        else {
            this.drawTitle(g, "You loser");
        }

        // draw lose menu
        this.gameOverLoseMenu.present(g);
    }

    private void drawTitle (Graphics g, String title)
    {
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
                this.setGamePaused();
            }

            // start game
            if (this.state == GameState.Ready) {
                this.state = GameState.Running;
            }
        }
    }

    private void setGamePaused() {
        this.state = GameState.Paused;

        // reset menu animation
        this.pauseMenu.reset();
    }

    private void setGameOver () {
        this.state = GameState.GameOver;

        // reset game over menu
        this.gameOverLoseMenu.reset();
    }

    private void restartLevel(int userHp){
        this.game.setScreen(new LoadingLevelScreen(this.game, userHp, this.levelCode));
    }

    @Override
    public void onMenuItemSelect(String menuCode)
    {
        Log.d("GameScreen", "Menu select: "+menuCode);

        switch (menuCode) {
            case PauseMenu.PAUSE_MENU_CONTINUE:
                this.state = GameScreen.GameState.Running;
                break;
            // save map image to file
            case PauseMenu.PAUSE_MENU_SAVE_MAP:
                SaveBitmapToFile.SaveToFile(this.game.getFileIO(), this.world.map.drawBitmap);
                break;
            case PauseMenu.PAUSE_MENU_RESTART:
            case GameOverLoseMenu.GLM_RESTART:
                // restart wight use continue
                this.restartLevel(Jackal.pickContinue());
                break;
        }
    }

    @Override
    public void pause() {
        Log.d("GameScree", "pause");
        this.setGamePaused();
        // if(state == GameState.Running) state = GameState.Paused;
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
