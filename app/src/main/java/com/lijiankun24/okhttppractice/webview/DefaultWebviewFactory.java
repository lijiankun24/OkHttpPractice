package com.lijiankun24.okhttppractice.webview;

import android.content.Context;
import android.webkit.WebView;

/**
 * DefaultWebviewFactory.java
 * <p>
 * Created by lijiankun on 17/9/6.
 */

class DefaultWebviewFactory implements WebViewFactory {

    @Override
    public WebView createWebView(Context context) {
        return new WebView(context);
    }
}
