package com.a530games.jackal.map;

import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.Texture;
import com.a530games.jackal.Jackal;

/**
 * Its standart texture with standart map position
 */
public class MapTexture extends Texture
{

    public MapTexture(Pixmap image, int left, int top)
    {
        super(
                image,
                left,
                top,
                Jackal.BLOCK_WIDTH,
                Jackal.BLOCK_HEIGHT
        );
    }

}
