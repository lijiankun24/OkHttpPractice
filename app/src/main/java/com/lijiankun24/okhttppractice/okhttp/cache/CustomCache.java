package com.lijiankun24.okhttppractice.okhttp.cache;

import com.lijiankun24.okhttppractice.MyApplication;

import java.io.File;

import okhttp3.Cache;

/**
 * CustomCache.java
 * <p>
 * Created by lijiankun on 17/9/4.
 */

public class CustomCache {

    private static File sHttpCacheFile = new File(MyApplication.getInstance().getExternalCacheDir(),
            "OkHttpPractice");

    private static int sCacheSize = 10 * 1024 * 1024; // 10 MiB

    private static Cache sCache = new Cache(sHttpCacheFile, sCacheSize);

    public static Cache getCache() {
        return sCache;
    }
}
