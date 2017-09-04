package com.lijiankun24.okhttppractice.okhttp.cache;

import com.lijiankun24.okhttppractice.MyApplication;
import com.lijiankun24.okhttppractice.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * CacheInterceptor.java
 * <p>
 * Created by lijiankun on 17/9/4.
 */

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (NetworkUtil.isNetWorkAvailable(MyApplication.getInstance())) {
            int maxAge = 60;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
