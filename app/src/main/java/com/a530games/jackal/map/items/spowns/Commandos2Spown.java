package com.a530games.jackal.map.items.spowns;


import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.framework.helpers.texture.Texture;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCell;
import com.a530games.jackal.objects.enemies.Commandos;
import com.a530games.jackal.objects.enemies.Commandos2;
import com.a530games.jackal.objects.enemies.Enemy;
import com.a530games.jackal.objects.enemies.EnemyDieEventHandler;
import com.a530games.jackal.objects.enemies.Tank;
import com.a530games.jackal.textures.SpownTexture;

public class Commandos2Spown extends MapCell implements EnemyDieEventHandler
{
    private final Texture texture;

    private Commandos2 commandos = null;

    /**
     * Need tabk kill to win this cell
     */
    private final int needTanksKill = 5;

    public Commandos2Spown(int col, int row)
    {
        super(col, row);

        this.texture = new SpownTexture(SpownTexture.SpownType.commandos2);
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler)
    {

        if (this.commandos == null) {
            this.commandos = new Commandos2(this.getCenter());

            // rize event if birn
            callbackHandler.spownEnemy(this, this.commandos);
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
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap) {
        return false;
    }

    @Override
    public void enemyDie(Enemy enemy) {

    }

    @Override
    public boolean isWin() {
        // todo: fix this on cill enemy
        return false;
    }
}
