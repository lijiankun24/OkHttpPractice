package com.lijiankun24.okhttppractice.webview.glide;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.lijiankun24.okhttppractice.webview.util.Md5Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * CustomGlideModule.java
 * <p>
 * Created by lijiankun on 17/9/7.
 */

@GlideModule
public class CustomGlideModule extends AppGlideModule {

    private static CustomGlideModule INSTANCE = null;

    private static DiskCache mDiskCache = null;

    private LruResourceCache mResourceCache = null;

    private LruBitmapPool mBitmapPool = null;

    private DiskCache.Factory mFactory = null;

    public static CustomGlideModule getInstance() {
        if (INSTANCE == null) {
            synchronized (CustomGlideModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CustomGlideModule();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        initCache(context);
        RequestOptions options = new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888);
        builder.setDefaultRequestOptions(options);
        builder.setMemoryCache(mResourceCache);
        builder.setBitmapPool(mBitmapPool);
        builder.setDiskCache(mFactory);
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    private void initCache(Context context) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();

        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        mResourceCache = new LruResourceCache(customMemoryCacheSize);
        mBitmapPool = new LruBitmapPool(customBitmapPoolSize);

        // set size & external vs. internal
        int cacheSize100MB = 104857600;
        String path = "ImageGlideCache";
        mFactory = new CustomCacheDiskFactory(context, path, cacheSize100MB);
        mDiskCache = mFactory.build();
        Log.i("lijk", "initCache " + mDiskCache);
    }

    public void putCacheFile(String url, InputStream inputStream) {
        try {
            String keyString = Md5Util.md5(url);
            CustomGlideWrite write = new CustomGlideWrite(inputStream);
            CustomGlideKey key = new CustomGlideKey(keyString);
            mDiskCache.put(key, write);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InputStream get(String url) {
        if (mDiskCache != null) {
            String key = Md5Util.md5(url);
            File file = mDiskCache.get(new CustomGlideKey(key));

            try {
                if (file != null && file.exists() && file.length() > 0) {
                    return new BufferedInputStream(new FileInputStream(file), (int) file.length() + 5);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
