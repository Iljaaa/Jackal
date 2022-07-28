package com.a530games.jackal.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.a530games.framework.Controller;
import com.a530games.framework.Game;
import com.a530games.framework.Graphics;
import com.a530games.framework.Input;
import com.a530games.framework.Pixmap;
import com.a530games.framework.Screen;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Bullet;
import com.a530games.jackal.Settings;
import com.a530games.jackal.Sidebar;
import com.a530games.jackal.Snake;
import com.a530games.jackal.SnakePart;
import com.a530games.jackal.Stain;
import com.a530games.jackal.World;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.objects.Player;
import com.a530games.jackal.objects.Vehicle;

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

    // проверочная рамка для проверки столкновения
    Paint paint;

    // paint for the hit box
    Paint hitBoxPaint;

    public GameScreen(Game game) {
        super(game);
        this.world = new World();
        this.sidebar = new Sidebar();

        this.paint = new Paint();
        this.paint.setColor(Color.GRAY);

        this.hitBoxPaint = new Paint();
        this.hitBoxPaint.setStyle(Paint.Style.STROKE);
        this.hitBoxPaint.setStrokeWidth(2);
        this.hitBoxPaint.setColor(Color.YELLOW);
    }

    @Override
    public void update(float deltaTime)
    {
        // todo: подмать какуюто абстрацию над этим всем делом
        List<Input.TouchEvent> touchEvents = this.game.getInput().getTouchEvents();

        List<Input.KeyEvent> keyEvents = this.game.getInput().getKeyEvents();

        //
        Controller c = this.game.getInput().getController();


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
                    this.world.player.moveLeft(deltaTime);
                }
                if(event.x > 200) {
                    this.world.snake.turnRight();
                    this.world.player.moveRight(deltaTime);
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
                this.world.player.move(Player.MOVE_TOP_RIGHT, deltaTime);
            }
            else if (controller.isLeftButtonDown()) {
                this.world.player.move(Player.MOVE_TOP_LEFT, deltaTime);
            }
            else {
                this.world.player.move(Player.MOVE_TOP, deltaTime);
            }
        }
        else if (controller.isBottomButtonDown())  {
            if (controller.isRightButtonDown()) {
                this.world.player.move(Player.MOVE_DOWN_RIGHT, deltaTime);
            }
            else if (controller.isLeftButtonDown()) {
                this.world.player.move(Player.MOVE_DOWN_LEFT, deltaTime);
            }
            else {
                this.world.player.move(Player.MOVE_DOWN, deltaTime);
            }
        }
        else {
            if (controller.isLeftButtonDown()) {
                this.world.player.move(Player.MOVE_LEFT, deltaTime);
            }
            if (controller.isRightButtonDown()) {
                this.world.player.move(Player.MOVE_RIGHT, deltaTime);
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
            Bullet b = this.world.player.fire(deltaTime);
            if (b != null) {
                if (this.world.addBullet(b)){
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
                    this.world.player.moveRight(deltaTime);
                }
                else if (event.keyCode == 29) {
                    this.world.snake.turnLeft();
                    this.world.player.moveLeft(deltaTime);
                }
                else if (event.keyCode == 51) {
                    this.world.player.moveTop(deltaTime);
                }
                else if (event.keyCode == 47) {
                    this.world.player.moveDown(deltaTime);
                }
                else if (event.keyCode == 62) {
                    this.world.player.fire(deltaTime);
                }
            }
        }

        // обновление мира
        this.world.update(deltaTime);

        // определяем пересечение с тестовой рамкой

        // сначала мы проверяем глобальный подзод
        // надо вычислить строку на которой находится машинка и проверить три
        if (this.world.player.hitBox.bottom > this.world.map.testRect.top && (this.world.player.hitBox.top < this.world.map.testRect.bottom))
        {
            this.paint.setColor(Color.RED);
        }
        else {
            this.paint.setColor(Color.GRAY);
        }

        // fixme: при завершении f[s выводить не будем
        this.sidebar.setFps(this.game.getRenderView().fps);
        this.sidebar.setPlayerAngle(this.world.player.angle);
        this.sidebar.setPlayerPos(Math.round(this.world.player.hitBox.left), Math.round(this.world.player.hitBox.top));
        this.sidebar.setMapPos(this.world.map.x, this.world.map.y);


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
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP)
            {
                // по клику перезапускаем игру
                /*if(event.x >= 128 && event.x <= 192 && event.y >= 200 && event.y <= 264) {
                    // todo: enable sound
                    // if(Settings.soundEnabled) Assets.click.play(1);
                    // game.setScreen(new MainMenuScreen(game));
                    return;
                }*/
            }
        }
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


        this.game.getGraphics().drawPixmap(Assets.bg, 10, 10);

    }

    private void drawWorld(World world)
    {
        // рисуем карку
        this.drawMap();

        // рамка для проверки столкновений
        this.game.getGraphics().drawRect(this.world.map.testRect.getDrawRect(), this.paint.getColor());

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
        this.game.getGraphics().drawText("angle: " + sidebar.playerAngle, 650, 110, 20, Color.MAGENTA);
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
            for (int i = 0; i < bulletSize; i++) {
                Bullet b = this.world.bullets.get(i);

                g.drawPixmap(
                        Assets.bullet,
                        Math.round(b.getX()),
                        Math.round(b.getY()));
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
                Vehicle b = this.world.enemies.get(i);

                g.drawPixmap(
                        b.sprite.image,
                        Math.round(b.hitBox.left),
                        Math.round(b.hitBox.top),
                        b.sprite.getLeft(),
                        b.sprite.getTop(),
                        64,
                        64);
            }
        }
    }

    protected void drawMap()
    {
        Graphics g = this.game.getGraphics();
        g.drawBitmap(this.world.map.testBitmap, 0 ,0);

        // this.drawMapNet(g);

        this.drawActiveCell();

        for (int row = 0; row < this.world.map.mapRows; row++) {
            for (int col = 0; col < this.world.map.mapCols; col++) {
                MapCell c = this.world.map.fields[row][col];
                if (c == null) continue;
                g.drawRect(c.hitBox, this.hitBoxPaint);
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
        Graphics g = this.game.getGraphics();
        // g.drawRect(0, row * Map.SPRITE_HEIGHT, 640, 20, Color.WHITE);
        g.drawRect( col * Map.SPRITE_WIDTH, row * Map.SPRITE_HEIGHT, Map.SPRITE_WIDTH, Map.SPRITE_HEIGHT, Color.YELLOW);
    }

    /**
     *
     */
    private void drawPlayer ()
    {
        Graphics g = this.game.getGraphics();

        this.drawPlayerHitBox(g);

        int playerX = Math.round(this.world.player.getLeft());
        int playerY = Math.round(this.world.player.getTop());

        /*
         * 12 = 0.5 * 64 (player sprite width) - 40 (player hitbox width)
         */
        g.drawPixmap(
            this.world.player.sprite.image,
            Math.round(playerX) - 12, //
            Math.round(playerY) - 12,
                this.world.player.sprite.getLeft(),
                this.world.player.sprite.getTop(),
            64,
            64);

        // draw turret
        g.drawPixmap(
                this.world.player.gun.image,
                Math.round(playerX) - 12, //
                Math.round(playerY) - 12,
                this.world.player.gun.getLeft(),
                this.world.player.gun.getTop(),
                64,
                64);

        this.drawPlayerAngle(g);
    }

    private void drawPlayerHitBox (Graphics g) {
        g.drawRect(this.world.player.hitBox.getDrawRect(), this.hitBoxPaint);
    }

    private void drawPlayerAngle (Graphics g)
    {
        int centerLeft = Math.round(this.world.player.hitBox.getCenterLeft());
        int centerTop = Math.round(this.world.player.hitBox.getCenterTop());

        // отрисовываем вектор направления
        double s = Math.sin(this.world.player.getAngle() * Math.PI);
        double c = Math.cos(this.world.player.getAngle() * Math.PI);

        g.drawLine(centerLeft, centerTop,
                centerLeft + (int) Math.round(s * 50),
                centerTop + (int) Math.round(c * 50),
                Color.GREEN);
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
       Graphics g = game.getGraphics();
         /*g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);*/
        g.drawText("Fire!!!!", 150, 200, 100, Color.RED);
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
