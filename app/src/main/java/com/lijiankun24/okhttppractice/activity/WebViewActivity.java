package com.lijiankun24.okhttppractice.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.lijiankun24.okhttppractice.R;
import com.lijiankun24.okhttppractice.webview.CustomWebChromeClient;
import com.lijiankun24.okhttppractice.webview.CustomWebViewClient;
import com.lijiankun24.okhttppractice.webview.WebViewManager;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_webview_root);
//        WebView mWebView = (WebView) findViewById(R.id.webview);
        WebView mWebView = WebViewManager.getInstance().getWebView(WebViewActivity.this);
        linearLayout.addView(mWebView);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new CustomWebViewClient(WebViewActivity.this));
       /* mWebView.setWebViewClient(new WebViewClient() {

            @TargetApi(21)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });*/
        mWebView.setWebChromeClient(new CustomWebChromeClient());
        mWebView.loadUrl("https://ke.youdao.com");
    }
}
