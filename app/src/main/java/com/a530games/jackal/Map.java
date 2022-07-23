package com.a530games.jackal;

import android.graphics.Rect;

import com.a530games.framework.helpers.FloatRect;

public class Map
{
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
    MapCell[][] fields; // = new MapCell[1][1];

    public Map() {
        this.fields = new MapCell[MapRows][MapCols];
        this.fields[3][2] = new MapCell();

        MapCell c =this.fields[3][2];
        c.isRock = true;

        this.testRect = new FloatRect(400, 400, 500, 500);
    }
}
