package com.a530games.jackal;

public class Map
{
    // положение карты
    public int x = 0;
    public int y = 0;

    // размер карты в блоках
    public int MapRows = 50;
    public int MapCols = 100;

    /**
     * массив полей
     */
    MapCell[][] fields; // = new MapCell[1][1];

    public Map() {
        this.fields = new MapCell[MapRows][MapCols];
        this.fields[3][2] = new MapCell();

        MapCell c =this.fields[3][2];
        c.isRock = true;
    }


}
