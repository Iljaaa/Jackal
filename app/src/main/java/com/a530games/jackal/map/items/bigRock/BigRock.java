package com.a530games.jackal.map.items.bigRock;

import com.a530games.framework.Graphics;
import com.a530games.framework.helpers.FloatRect;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.framework.helpers.Sprite;
import com.a530games.jackal.map.CellEventCallbackHandler;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.MapCellWithHitbox;

public class BigRock extends MapCellWithHitbox
{
    Sprite sprite;

    public enum Part {
        LeftTop, CenterTop, RightTop,
        LeftCenter, CenterCenter, RightCenter,
        LeftBottom, CenterBottom, RightBottom,
    }

    public BigRock(int row, int col, Part part) {
        super(row, col, Jackal.BLOCK_WIDTH, Jackal.BLOCK_HEIGHT);

        this.sprite = new Sprite(Assets.bigStone);

        switch (part) {
            case LeftTop:       this.sprite.set(0, 0); break;
            case CenterTop:     this.sprite.set(1, 0); break;
            case RightTop:      this.sprite.set(2, 0); break;
            case LeftCenter:    this.sprite.set(0, 1); break;
            case CenterCenter:  this.sprite.set(1, 1); break;
            case RightCenter:   this.sprite.set(2, 1); break;
            case LeftBottom:    this.sprite.set(0, 2); break;
            case CenterBottom:  this.sprite.set(1, 2); break;
            case RightBottom:   this.sprite.set(2, 2); break;
        }
    }

    @Override
    public void drawOnBackground(Graphics g, Map map)
    {
        this._draw(g, this.leftTopCorner.x, this.leftTopCorner.y);
    }

    @Override
    public void update(float deltaTime, CellEventCallbackHandler callbackHandler) {

    }

    @Override
    public void draw(Graphics g, Map map) {

    }

    @Override
    public void drawTopLayout(Graphics g, Map map)
    {
        this._draw(g, map.screenLeftPotion(this.leftTopCorner.x), map.screenTopPotion(this.leftTopCorner.y));
    }

    private void _draw(Graphics g, int x, int y) {
        g.drawPixmap(
                this.sprite.image,
                x,
                y,
                this.sprite.getLeft(),
                this.sprite.getTop(),
                this.sprite.width,
                this.sprite.height);
    }

    @Override
    public boolean isIntersectPointInsideCell(float mapLeft, float mapTop) {
        return true;
    }

    @Override
    public boolean isIntersectRectInsideCell(FloatRect rectOnMap) {
        return true;
    }
}
