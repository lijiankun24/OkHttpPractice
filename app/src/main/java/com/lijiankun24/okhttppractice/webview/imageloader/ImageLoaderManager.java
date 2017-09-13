package com.lijiankun24.okhttppractice.webview.imageloader;

import android.content.Context;

import com.lijiankun24.okhttppractice.webview.util.Md5Util;
import com.lijiankun24.okhttppractice.webview.util.StreamUtil;
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

    private static DiskCache mDiskCache = null;

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

    public void init(Context context) {
        String imageCachePath = context.getExternalCacheDir().getAbsolutePath() + "/ImageCache/";
        File file = new File(imageCachePath);
        if (!file.exists()) {
            file.mkdirs();
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
            url = Md5Util.md5(url);
            File file = mDiskCache.get(url);
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.safeClose(inputStream);
        }
        return inputStream;
    }

    public void saveDiskCache(String url, InputStream inputStream) {
        try {
            url = Md5Util.md5(url);
            mDiskCache.save(url, inputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
