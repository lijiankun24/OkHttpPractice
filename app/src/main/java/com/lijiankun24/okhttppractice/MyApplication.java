package com.lijiankun24.okhttppractice;

import android.app.Application;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.lijiankun24.okhttppractice.utils.MobileUtil;
import com.lijiankun24.okhttppractice.webview.WebViewManager;
import com.lijiankun24.okhttppractice.webview.glide.GlideApp;

/**
 * MyApplication.java
 * <p>
 * Created by lijiankun on 17/8/31.
 */

public class MyApplication extends Application {

    private static Application INSTANCE = null;

    public static Application getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        MobileUtil.init(this);
        WebViewManager.getInstance().init(6, this);
        Glide glide = new GlideBuilder()
                .setDiskCache(new DiskCache.Factory() {
                    @Nullable
                    @Override
                    public DiskCache build() {
                        return null;
                    }
                })
                .build(this);
    }
}
