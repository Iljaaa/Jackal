package com.a530games.jackal.textures;

import com.a530games.framework.Pixmap;
import com.a530games.framework.helpers.texture.Texture;
import com.a530games.jackal.Assets;

public class SpownTexture extends Texture {

    public enum SpownType {
        tank,
        commandos,
        commandos2
    }

    public SpownTexture(SpownType type) {
        super(Assets.spown, 0, 0, 64, 53);
        switch (type){
            case commandos: this.offsetToFrames(1, 0); break;
            case commandos2: this.offsetToFrames(2, 0); break;
        }
    }
}
