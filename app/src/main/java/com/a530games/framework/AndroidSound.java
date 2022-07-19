package com.a530games.framework;

import android.media.SoundPool;

public class AndroidSound implements Sound
{
    private int soundId;

    private SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    @Override
    public void play(float volume) {
        this.soundPool.play(this.soundId, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose() {
        this.soundPool.unload(this.soundId);
    }
}
