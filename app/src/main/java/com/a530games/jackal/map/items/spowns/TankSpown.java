package com.a530games.jackal.map.items.spowns;


import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
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

    int killedTanks = 0;

    /**
     * Need tabk kill to win this cell
     */
    private final int needTanksKill = 5;

    public TankSpown(int col, int row)
    {
        super(col, row);

        this.texture = new SpownTexture(SpownTexture.SpownType.tank);

        //
        this.spownTimer = Jackal.getRandom().nextFloat() * 10;
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
                this.spawnedTank = new Tank(this.getCenter());
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
    public boolean isIntersectRectInsideCell(HitBox rectOnMap) {
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
