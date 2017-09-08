package com.lijiankun24.okhttppractice.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lijiankun24.okhttppractice.R;
import com.lijiankun24.okhttppractice.utils.L;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        WebView mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl("http://gank.io/");
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        /*@Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            L.i("url is " + url);
            if (isImageResUrl(url) || url.contains("image")) {
                L.i("image url is " + url);
                WebResourceResponse response = super.shouldInterceptRequest(view, url);
                InputStream inputStream = response.getData();
                return response;
            } else {
                return super.shouldInterceptRequest(view, url);
            }
        }*/
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            L.i("onReceivedTitle title is " + title);
            view.loadUrl("javascript:" +
                    "window.addEventListener('DOMContentLoaded', function() {" +
                    "alert('domc:' + new Date().getTime()); " +
                    "})");
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            L.i("**** Blocking Javascript onJsAlert :" + message);
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            L.i("**** Blocking Javascript Prompt :" + message);
//            if (message != null) {
//                if (!preCacheRun) {
//                    String[] strs = message.split(":");
//                    if (2 == strs.length) {
//                        if ("domc".equals(strs[0])) {
//                            result.getCurrentRun().setDocComplete(Long.valueOf(strs[1].trim()));
//                        }
//                    }
//                }
//            }
//            r.confirm(defaultValue);
            return super.onJsPrompt(view, url, message, defaultValue, result);
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
