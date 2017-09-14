package com.lijiankun24.okhttppractice.webview.glide;

import android.content.Context;

import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;

import java.io.File;

/**
 * CustomCacheDiskFactory.java
 * <p>
 * Created by lijiankun on 17/9/14.
 */

class CustomCacheDiskFactory extends DiskLruCacheFactory {

    CustomCacheDiskFactory(final Context context, final String diskCacheName,
                                  int diskCacheSize) {
        super(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                File cacheDirectory = context.getExternalCacheDir();
                if (cacheDirectory == null) {
                    return null;
                }
                if (diskCacheName != null) {
                    return new File(cacheDirectory, diskCacheName);
                }
                return cacheDirectory;
            }
        }, diskCacheSize);
    }
}
