package com.lijiankun24.okhttppractice.okhttp;

import com.lijiankun24.okhttppractice.okhttp.cookie.CustomCookieJar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttpManager.java
 * <p>
 * Created by lijiankun on 17/8/25.
 */


public class OkHttpManager {

    private static OkHttpManager INSTANCE = null;

    private static OkHttpClient sHttpClient = null;

    private OkHttpManager() {
        sHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CustomCookieJar())
                .build();
    }

    public static OkHttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (OkHttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkHttpManager();
                }
            }
        }
        return INSTANCE;
    }

    private OkHttpClient getHttpClient() {
        return sHttpClient;
    }

    public void addGetStringRequest(String url, final OnHttpListener<String> httpListener) {
        final Request request = new Request.Builder()
                .method("GET", null)
                .url(url)
                .build();

        Call call = getHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpListener.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    httpListener.onSuccess(response.body().string());
                }
            }
        });
    }
}
