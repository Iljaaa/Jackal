package com.a530games.framework;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class AndroidMusic implements Music, MediaPlayer.OnCompletionListener
{
    MediaPlayer mediaPlayer;

    private boolean isPrepared = false;

    public AndroidMusic(AssetFileDescriptor assetDescriptor)
    {
        this.mediaPlayer = new MediaPlayer();
        try {
            this.mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            this.mediaPlayer.prepare();
            this.isPrepared = true;
            this.mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    @Override
    public void play()
    {
        if (this.mediaPlayer.isPlaying()) return;

        try {
            synchronized (this) {
                if (!this.isPrepared) this.mediaPlayer.prepare();
                this.mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.mediaPlayer.stop();
        synchronized (this) {
            this.isPrepared = false;
        }
    }

    @Override
    public void pause() {
        this.mediaPlayer.pause();
    }

    @Override
    public void setLooping(boolean isLooping) {
        this.mediaPlayer.setLooping(isLooping);
    }

    @Override
    public void setVolume(float volume) {
        this.mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped() {
        return !this.isPrepared;
    }

    @Override
    public boolean isLooping() {
        return this.mediaPlayer.isLooping();
    }

    @Override
    public void dispose()
    {
        if (this.mediaPlayer.isPlaying()) this.mediaPlayer.stop();
        this.mediaPlayer.release();
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        synchronized (this) {
            this.isPrepared = false;
        }
    }
}
