package com.a530games.jackal.textures;

import com.a530games.framework.helpers.texture.AnimationTexture;
import com.a530games.framework.helpers.texture.TextureRegion;
import com.a530games.jackal.Assets;

public class SmallBlowAnimation extends AnimationTexture
{

    public SmallBlowAnimation()
    {
        super(Assets.smallBlow);

        this.frames = new TextureRegion[]{
            new TextureRegion(0, 0, 16, 16),
            new TextureRegion(16, 0, 16, 16),
            new TextureRegion(32, 0, 16, 16),
            new TextureRegion(48, 0, 16, 16)
        };

        // this.isCircle(false);

//        this.addFrame(new TextureRegion(0, 0, 16, 16));
//        this.addFrame(new TextureRegion(16, 0, 16, 16));
//        this.addFrame(new TextureRegion(32, 0, 16, 16));
//        this.addFrame(new TextureRegion(48, 0, 16, 16));
    }



    /*
     * Is animation in circus
     */
//    @Override
//    protected boolean isCircle(){
//        return true;
//    }
}
