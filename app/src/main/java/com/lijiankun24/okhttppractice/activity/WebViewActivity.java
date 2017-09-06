package com.lijiankun24.okhttppractice.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.lijiankun24.okhttppractice.MyApplication;
import com.lijiankun24.okhttppractice.R;
import com.lijiankun24.okhttppractice.utils.L;
import com.lijiankun24.okhttppractice.webview.WebViewManager;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_webview_root);
        mWebView = WebViewManager.getInstance().getWebView(MyApplication.getInstance());
        if (mWebView == null) {
            return;
        }
        linearLayout.addView(mWebView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl("https://ke.youdao.com");
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            L.i("url is " + url);
            if (isImageResUrl(url) || url.contains("image")) {
                L.i("image url is " + url);

            } else {
                return super.shouldInterceptRequest(view, url);
            }
            return super.shouldInterceptRequest(view, url);
        }
    }

    private boolean isImageResUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        } else {
            if (url.startsWith("http") && (url.endsWith(".png") || url.endsWith(".jpg")
                    || url.endsWith(".jpeg") || url.endsWith(".gif"))) {
                return true;
            }
        }
        return false;
    }
}
