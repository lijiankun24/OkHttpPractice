package com.lijiankun24.okhttppractice.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.lijiankun24.okhttppractice.R;
import com.lijiankun24.okhttppractice.okhttp.OkHttpManager;
import com.lijiankun24.okhttppractice.okhttp.OnHttpListener;
import com.lijiankun24.okhttppractice.webview.CustomWebChromeClient;
import com.lijiankun24.okhttppractice.webview.CustomWebViewClient;
import com.lijiankun24.okhttppractice.webview.WebViewManager;

public class WebViewActivity extends AppCompatActivity {

    private LinearLayout linearLayout = null;

    private WebView mWebView = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (mWebView != null) {
                Log.i("lijk", "mWebView != null onSuccess " + result);
                mWebView.loadData(result, "text/html", "UTF-8");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (linearLayout != null && mWebView != null) {
            linearLayout.removeView(mWebView);
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.removeAllViewsInLayout();
        }
    }

    private void initView() {
        linearLayout = (LinearLayout) findViewById(R.id.ll_webview_root);
//        WebView mWebView = (WebView) findViewById(R.id.webview);
        long startTime = System.currentTimeMillis();
        mWebView = WebViewManager.getInstance().getWebView(this.getApplicationContext());
//        WebView mWebView = new WebView(this);
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
        mWebView.setWebChromeClient(new CustomWebChromeClient(startTime));
        loadHtml();
//        mWebView.setWebViewClient(new LightWebViewClient());
//        mWebView.loadUrl("https://ke.youdao.com");
//        mWebView.reload();
    }

    private void loadHtml() {
        OkHttpManager.getInstance(WebViewActivity.this).addGetStringRequest("https://ke.youdao.com", new OnHttpListener<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lijk", "onSuccess ");
                Message message = Message.obtain();
                message.obj = result;
                mHandler.sendMessage(message);
            }

            @Override
            public void onError() {

            }
        });
    }
}
