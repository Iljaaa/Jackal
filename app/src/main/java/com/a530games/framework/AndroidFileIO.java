package com.a530games.framework;

import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AndroidFileIO implements FileIO
{
    private final AssetManager assets;

    String externalStoragePath;

    public AndroidFileIO(File p, AssetManager assets)
    {

        // this.externalStoragePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator;
        // this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        // this.externalStoragePath = Environment.getDownloadCacheDirectory().getAbsolutePath() + File.separator;
        this.externalStoragePath = p.getAbsolutePath()+ File.separator;

        this.assets = assets;
    }

    @Override
    public InputStream readAsset(String fileName) throws IOException {
        return this.assets.open(fileName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(this.externalStoragePath + fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(this.externalStoragePath + fileName);
    }
}
