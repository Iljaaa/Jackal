package com.a530games.jackal.map.items;


import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.enemies.EnemyDieEventHandler;
import com.a530games.jackal.objects.enemies.Tank;

public class Spown extends MapCell implements EnemyDieEventHandler
{
    private final Sprite sprite;

    float spownTimer;

    private Tank spawnedTank = null;

    int killedTanks = 0;
    private final int needTanksKill = 5;

    public Spown(int row, int col)
    {
        super(row, col);
        // this.tank = new Tank();
        this.sprite = new Sprite(Assets.spown, 0, 0);

        this.spownTimer = Jackal.getRandom().nextFloat() * 10;

        // this.spawnedTank = new Tank(this.col * Map.SPRITE_WIDTH, this.row * Map.SPRITE_HEIGHT);
    }

    @Override
    public void drawOnBackground(Graphics g, Map map) {
        g.drawPixmap(
                this.sprite.image,
                this.col * Map.SPRITE_WIDTH,
                this.row * Map.SPRITE_HEIGHT,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height
        );
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler)
    {
        if (this.killedTanks >= this.needTanksKill) {
            return;
        }

        this.spownTimer -= deltaTime;
        if (this.spownTimer <= 0)
        {
            this.spownTimer = Jackal.getRandom().nextFloat() * 10;

            if (this.spawnedTank == null) {
                this.spawnedTank = new Tank(this.col * Map.SPRITE_WIDTH, this.row * Map.SPRITE_HEIGHT);
                this.spawnedTank.setDieEventHandler(this);
                // this.spawnedTank.reNew(this.col * Map.SPRITE_WIDTH, this.row * Map.SPRITE_HEIGHT);

                callbackHandler.spownEnemy(this, this.spawnedTank);
            }
        }
    }

    @Override
    public void draw(Graphics g, Map map) {

    }

    @Override
    public void drawHitBox(Graphics g, Map map) {

    }

    @Override
    public void drawTopLayout(Graphics g, Map map) {

    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap) {
        return false;
    }

    @Override
    public void enemyDie(Enemy enemy) {
        this.spownTimer = Jackal.getRandom().nextFloat() * 10;
        this.spawnedTank = null;
        this.killedTanks++;
    }

    @Override
    public boolean isWin() {
        return (this.killedTanks >= this.needTanksKill);
    }
}
