package com.lijiankun24.okhttppractice.webview.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.lijiankun24.okhttppractice.MyApplication;
import com.lijiankun24.okhttppractice.okhttp.OkHttpManager;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Request;

/**
 * ImageInputStreamFetcher.java
 * <p>
 * Created by lijiankun on 17/9/8.
 */

public class ImageInputStreamFetcher implements DataFetcher<InputStream> {

    // 检查是否取消任务的标识
    private volatile boolean mIsCanceled;

    private final String url;

    public ImageInputStreamFetcher(String url) {
        this.url = url;
    }

    @Override
    public void loadData(Priority priority, DataCallback<? super InputStream> callback) {
        if (url == null || mIsCanceled) {
            callback.onDataReady(null);
        } else {
            callback.onDataReady(fetchStream(url));
        }
    }

    private InputStream fetchStream(String url) {
        try {
            Call fetchStreamCall = syncGet(url);
            if (fetchStreamCall != null && fetchStreamCall.execute() != null
                    && fetchStreamCall.execute().body() != null) {
                return fetchStreamCall.execute().body().byteStream();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步的http get请求
     *
     * @param url 要访问的url
     * @return OkHttp 的网络请求对象
     */
    private Call syncGet(String url) {
        Request request = new Request.Builder().url(url).get().build();
        return OkHttpManager.getInstance(MyApplication.getInstance())
                .getHttpClient()
                .newCall(request);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void cancel() {
        mIsCanceled = true;
    }

    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override
    public DataSource getDataSource() {
        return null;
    }
}
