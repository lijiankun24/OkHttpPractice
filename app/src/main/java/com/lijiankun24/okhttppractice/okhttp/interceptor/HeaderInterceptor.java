package com.lijiankun24.okhttppractice.okhttp.interceptor;

import com.lijiankun24.okhttppractice.utils.MobileUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HeaderInterceptor.java
 * <p>
 * Created by lijiankun on 17/8/31.
 */

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("Network", MobileUtil.getNetwork())
                .addHeader("Mid", MobileUtil.getMid())
                .addHeader("Model", MobileUtil.getModel())
                .addHeader("Version", MobileUtil.getVersion())
                .addHeader("Ssid", MobileUtil.getSSID())
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Keep-Alive", "120")
                .addHeader("User-Agent", "jiankunli24@gmail.com|www.lijiankun24.com|25|okhttp/3.8.0|OkHttpPractice")
                .build();
        return chain.proceed(newRequest);
    }
}
