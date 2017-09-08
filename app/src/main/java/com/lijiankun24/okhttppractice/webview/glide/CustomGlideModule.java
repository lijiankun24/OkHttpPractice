package com.lijiankun24.okhttppractice.webview.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * CustomGlideModule.java
 * <p>
 * Created by lijiankun on 17/9/7.
 */

@GlideModule
public class CustomGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        RequestOptions options = new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888);
        builder.setDefaultRequestOptions(options);
        // set size & external vs. internal
        int cacheSize100MegaBytes = 104857600;
        DiskCache.Factory diskCacheFactory = new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes);
        DiskCache diskCache = diskCacheFactory.build();
        CustomGlideKey glideKey = new CustomGlideKey();
        DiskCache.Writer writer = new DiskCache.Writer() {
            @Override
            public boolean write(File file) {

                return false;
            }
        };
        diskCache.put(glideKey,writer);
        builder.setDiskCache(diskCache);
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
