package com.a530games.jackal.textures;

import com.a530games.framework.helpers.texture.AnimationTexture;
import com.a530games.framework.helpers.texture.TextureRegion;
import com.a530games.jackal.Assets;

public class BigBlowAnimation extends AnimationTexture
{

    public BigBlowAnimation()
    {
        super(Assets.boom);

        this.frames = new TextureRegion[]{
            new TextureRegion(0, 0, 96, 96),
            new TextureRegion(96, 0, 96, 96),
            new TextureRegion(192, 0, 96, 96),
            new TextureRegion(288, 0, 96, 96),
            new TextureRegion(384, 0, 96, 96),
            new TextureRegion(480, 0, 96, 96),
            new TextureRegion(576, 0, 96, 96),
            new TextureRegion(672, 0, 96, 96),
        };
    }



    /*
     * Is animation in circus
     */
//    @Override
//    protected boolean isCircle(){
//        return true;
//    }
}
