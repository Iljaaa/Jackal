package com.a530games.jackal.map;

import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.texture.Texture;
import com.a530games.jackal.Jackal;

/**
 * Its standart texture with standart map position
 */
public class MapTexture extends Texture
{
    public int col, row;

    public MapTexture(Pixmap image, Map.Cell cell)
    {
        super(
                image,
                0,
                0,
                Jackal.BLOCK_WIDTH,
                Jackal.BLOCK_HEIGHT
        );


        this.row = cell.row;
        this.col = cell.col;
    }

}
