package com.a530games.jackal.map;

import com.a530games.framework.helpers.FloatRect;

public class Map
{
    public static final int SPRITE_HEIGHT = 64;
    public static final int SPRITE_WIDTH = 64;

    // положение карты
    public int x = 0;
    public int y = 0;

    // размер карты в блоках
    public int MapRows = 50;
    public int MapCols = 100;

    // rect for test intersection
    public FloatRect testRect;



    /**
     * массив полей
     */
    public MapCell[][] fields; // = new MapCell[1][1];

    public Map() {
        this.fields = new MapCell[MapRows][MapCols];
        this.fields[3][2] = new MapCell();

        MapCell c =this.fields[3][2];
        c.isRock = true;

        this.testRect = new FloatRect(400, 400, 500, 500);


    }

    public int getRowByTop(float top) {
        return (int) Math.floor(top / Map.SPRITE_HEIGHT);
    }

    public int getColByLeft(float left) {
        return (int) Math.floor(left / Map.SPRITE_WIDTH);
    }
}
