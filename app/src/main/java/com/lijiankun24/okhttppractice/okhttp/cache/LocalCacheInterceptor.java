package com.lijiankun24.okhttppractice.okhttp.cache;

import com.lijiankun24.okhttppractice.MyApplication;
import com.lijiankun24.okhttppractice.utils.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * LocalCacheInterceptor.java
 * <p>
 * Created by lijiankun on 17/9/4.
 */

public class LocalCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .maxStale(0, TimeUnit.SECONDS)
                .build();
        Request newRequest = chain.request().newBuilder()
                .cacheControl(cacheControl)
                .build();
        if (NetworkUtil.isNetWorkAvailable(MyApplication.getInstance())) {
            Response response = chain.proceed(newRequest);
            if (response.isSuccessful()) {
                return response;
            }
        }

        cacheControl = new CacheControl.Builder()
                .maxAge(Integer.MAX_VALUE, TimeUnit.SECONDS)
                .maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS)
                .build();
        newRequest = chain.request().newBuilder()
                .cacheControl(cacheControl)
                .build();
        return chain.proceed(newRequest);
    }
}
