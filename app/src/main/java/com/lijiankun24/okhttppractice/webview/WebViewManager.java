package com.lijiankun24.okhttppractice.webview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import java.util.LinkedList;

/**
 * WebViewManager.java
 * <p>
 * Created by lijiankun on 17/9/6.
 */

public class WebViewManager {

    private static WebViewManager INSTANCE = null;

    private WebViewFactory mDefaultWebViewFactory;

    private LinkedList<WebView> mWebViewCache;

    private Context mContext;

    private int mMaxWebViewCount;

    private WebViewManager() {
    }

    public static WebViewManager getInstance() {
        if (INSTANCE == null) {
            synchronized (WebViewManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WebViewManager();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void init(int maxCount, Context context) {
        mContext = context.getApplicationContext();
        mMaxWebViewCount = maxCount;
        mDefaultWebViewFactory = new DefaultWebviewFactory();
        mWebViewCache = new LinkedList<>();
        Handler handler = new Handler(Looper.getMainLooper());
        WebViewCreationTask task = new WebViewCreationTask(context);
        handler.post(task);
    }

    public synchronized WebView getWebView(Context context) {
        WebView view = null;
        if (mContext != context) {
            mWebViewCache.clear();
            mContext = context;
            Handler handler = new Handler(Looper.getMainLooper());
            WebViewCreationTask task = new WebViewCreationTask(context);
            handler.post(task);
        } else {
            if (mWebViewCache.size() > 0) {
                view = mWebViewCache.remove();
            }
        }
        if (view == null) {
            if (mDefaultWebViewFactory != null) {
                view = mDefaultWebViewFactory.createWebView(context);
            } else {
                view = new WebView(context);
            }
        }
        return view;
    }

    private class WebViewCreationTask implements Runnable {
        private Context mContext;

        WebViewCreationTask(Context context) {
            mContext = context;
        }

        @Override
        public void run() {
            synchronized (WebViewManager.this) {
                while (mWebViewCache.size() < mMaxWebViewCount) {
                    WebView view;
                    if (mDefaultWebViewFactory != null) {
                        view = mDefaultWebViewFactory.createWebView(mContext);
                    } else {
                        view = new WebView(mContext);
                    }
                    mWebViewCache.add(view);
                }
            }
        }
    }
}
