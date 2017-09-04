package com.lijiankun24.okhttppractice.okhttp.cache;

import com.lijiankun24.okhttppractice.MyApplication;
import com.lijiankun24.okhttppractice.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * NetCacheInterceptor.java
 * <p>
 * Created by lijiankun on 17/9/4.
 */

public class NetCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (NetworkUtil.isNetWorkAvailable(MyApplication.getInstance())) {
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        } else {
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + Integer.MAX_VALUE)
                    .build();
        }
    }
}
