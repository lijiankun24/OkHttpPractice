package com.lijiankun24.okhttppractice.webview.imageloader;

import android.content.Context;

import com.lijiankun24.okhttppractice.utils.Util;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * ImageLoaderManager.java
 * <p>
 * Created by lijiankun on 17/9/11.
 */

public class ImageLoaderManager {

    public static DiskCache mDiskCache = null;

    public static void init(Context context) {
        File file = new File(Util.IMAGECACHEPATH);
        if (!file.exists()) {
            file.mkdir();
        }
        mDiskCache = new UnlimitedDiskCache(file);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(mDiskCache)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static File getDiskCache(String url) {
        return mDiskCache.get(url);
    }

    public static void saveDiskCache(String url, File file) {
    }
}
