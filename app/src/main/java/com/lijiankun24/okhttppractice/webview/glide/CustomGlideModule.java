package com.lijiankun24.okhttppractice.webview.glide;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.lijiankun24.okhttppractice.utils.L;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * CustomGlideModule.java
 * <p>
 * Created by lijiankun on 17/9/7.
 */

@GlideModule
public class CustomGlideModule extends AppGlideModule {

    private LruResourceCache mResourceCache = null;

    private LruBitmapPool mBitmapPool = null;

    private DiskCache mDiskCache = null;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        initCache(context);
        RequestOptions options = new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888);
        builder.setDefaultRequestOptions(options);
        builder.setMemoryCache(mResourceCache);
        builder.setBitmapPool(mBitmapPool);
        builder.setDiskCache(new DiskCache.Factory() {
            @Nullable
            @Override
            public DiskCache build() {
                return mDiskCache;
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.append(String.class, InputStream.class, new ImageInputStreamModelLoader.Factory());
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
        int cacheSize100MegaBytes = 104857600;
        DiskCache.Factory factory = new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes);
        mDiskCache = factory.build();
    }

    public void getCacheFile() {
        if (mResourceCache != null) {
            Resource resource = mResourceCache.get(new Key() {
                @Override
                public void updateDiskCacheKey(MessageDigest messageDigest) {

                }
            });
            L.i("getResourceClass " + resource.getResourceClass());
        }
    }
}
