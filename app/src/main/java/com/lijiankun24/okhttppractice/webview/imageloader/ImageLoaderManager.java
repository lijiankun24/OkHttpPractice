package com.lijiankun24.okhttppractice.webview.imageloader;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ImageLoaderManager.java
 * <p>
 * Created by lijiankun on 17/9/11.
 */

public class ImageLoaderManager {

    private static ImageLoaderManager INSTANCE = null;

    private static String IMAGECACHEPATH = null;

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ImageLoaderManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageLoaderManager();
                }
            }
        }
        return INSTANCE;
    }

    public static DiskCache mDiskCache = null;

    public void init(Context context) {
        IMAGECACHEPATH = context.getExternalCacheDir().getAbsolutePath() + "/OkHttpImage/";
        File file = new File(IMAGECACHEPATH);
        if (!file.exists()) {
            file.mkdir();
        }
        mDiskCache = new UnlimitedDiskCache(file);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(mDiskCache)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public InputStream getDiskCache(String url) {
        InputStream inputStream = null;
        try {
            File file = mDiskCache.get(url);
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public void saveDiskCache(String url, InputStream inputStream) {
        try {
            mDiskCache.save(url, inputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
