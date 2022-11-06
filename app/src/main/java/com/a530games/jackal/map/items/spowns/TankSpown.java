package com.a530games.jackal.map.items.spowns;


import android.graphics.Color;
import android.graphics.Paint;

import com.a530games.framework.Camera2D;
import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.HitBox;
import com.a530games.framework.helpers.texture.Texture;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.enemies.EnemyDieEventHandler;
import com.a530games.jackal.objects.enemies.Tank;
import com.a530games.jackal.textures.SpownTexture;

public class TankSpown extends MapCell implements EnemyDieEventHandler
{
    private final Texture texture;

    float spownTimer;

    private Tank spawnedTank = null;

    /**
     * Need tabk kill to win this cell
     */
    private int needTanksKill = 5;

    Paint remainTanks;

    public TankSpown(int col, int row)
    {
        super(col, row);

        this.texture = new SpownTexture(SpownTexture.SpownType.tank);

        // start delay 10 secs
        this.spownTimer = 10 + Jackal.getRandom().nextFloat() * 10;

        this.remainTanks = new Paint();
        this.remainTanks.setStyle(Paint.Style.FILL);
        this.remainTanks.setColor(Color.GREEN);
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler)
    {
        if (this.needTanksKill <= 0) {
            return;
        }

        this.spownTimer -= deltaTime;
        if (this.spownTimer <= 0)
        {
            this.spownTimer = Jackal.getRandom().nextFloat() * 10;

            if (this.spawnedTank == null) {
                this.spawnedTank = new Tank(this.getCenter());

                // todo: remove event handler and check his state
                this.spawnedTank.setDieEventHandler(this);
                // this.spawnedTank.reNew(this.col * Map.SPRITE_WIDTH, this.row * Map.SPRITE_HEIGHT);

                callbackHandler.spownEnemy(this, this.spawnedTank);
            }
        }
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
    {
        this.texture.draw(g, this.rect.left, this.rect.top);
        /*g.drawPixmap(
                this.sprite.image,
                this.rect.left,
                this.rect.top,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height
        );*/

    }

    @Override
    public void draw(Graphics g, Camera2D camera2D)
    {
        // draw rects of need tank kill
        for (int i = 0; i < this.needTanksKill; i++)
        {
            g.drawRect(
                    camera2D.screenLeft(this.rect.left + 5 + (i * 10)),
                    camera2D.screenTop(this.rect.top + 5),
                    5,
                    5,
                    this.remainTanks
            );
        }
    }

    @Override
    public void drawHitBox(Graphics g, Camera2D camera) {

    }

    @Override
    public void drawTopLayout(Graphics g, Camera2D camera) {

    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return false;
    }

    @Override
    public boolean isIntersectRectInsideCell(HitBox rectOnMap) {
        return false;
    }

    @Override
    public void enemyDie(Enemy enemy) {
        this.spownTimer = Jackal.getRandom().nextFloat() * 10;
        this.spawnedTank = null;
        this.needTanksKill--;
    }

    @Override
    public boolean isWin() {
        return (this.needTanksKill <= 0);
    }
}
