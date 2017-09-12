package com.lijiankun24.okhttppractice.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.lijiankun24.okhttppractice.R;
import com.lijiankun24.okhttppractice.okhttp.OkHttpManager;
import com.lijiankun24.okhttppractice.utils.L;
import com.lijiankun24.okhttppractice.webview.WebViewManager;
import com.lijiankun24.okhttppractice.webview.imageloader.ImageLoaderManager;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class WebViewActivity extends AppCompatActivity {

    private String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "JPG", "JPEG", "PNG", "GIF"};

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

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl("http://ke.youdao.com/");
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse response = null;
            if (isImage(url) || url.contains("image")) {
                InputStream inputStream = ImageLoaderManager.getInstance().getDiskCache(url);
                String ext = MimeTypeMap.getFileExtensionFromUrl(url);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
                L.i("url is " + url);
                if (inputStream != null) {
                    response = new WebResourceResponse(mimeType, "utf-8", inputStream);
                    L.i("inputStream != null");
                } else {
                    try {
                        inputStream = handleRequestViaOkHttp(url);
                        L.i("inputStream == null" + (inputStream != null ? inputStream.available() : null));
                        ImageLoaderManager.getInstance().saveDiskCache(url, inputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        return super.shouldInterceptRequest(view, url);
                    }
                }
            }
            return response;
        }

        @TargetApi(21)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            WebResourceResponse response = null;
            if (isImage(url) || url.contains("image")) {
                InputStream inputStream = ImageLoaderManager.getInstance().getDiskCache(url);
                String ext = MimeTypeMap.getFileExtensionFromUrl(url);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
                L.i("url is " + url);
                if (inputStream != null) {
                    response = new WebResourceResponse(mimeType, "utf-8", inputStream);
                    L.i("inputStream != null");
                } else {
                    try {
                        inputStream = handleRequestViaOkHttp(url);
                        L.i("inputStream == null" + (inputStream != null ? inputStream.available() : null));
                        ImageLoaderManager.getInstance().saveDiskCache(url, inputStream);
                        response = new WebResourceResponse(mimeType, "utf-8", inputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return response;
        }
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

    /**
     * Checks whether a given url points to a valid image. The following formats are treated as
     * images: png, jpg (and jpeg) and gif (Also in capital letters).
     *
     * @param url Url that points at a certain location on the web. Must be different from null.
     * @return true when url points to a valid image.
     */
    private boolean isImage(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        for (String extension : imageExtensions) {
            if (url.startsWith("http") && url.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    private InputStream handleRequestViaOkHttp(@NonNull String url) {
        try {
            // On Android API >= 21 you can get request method and headers
            // As I said, we need to only display "simple" page with resources
            // So it's GET without special headers
            final Call call = OkHttpManager.getInstance(WebViewActivity.this)
                    .getHttpClient()
                    .newCall(new Request.Builder()
                            .url(url)
                            .build()
                    );

            final Response response = call.execute();
            return response.body().byteStream();
        } catch (Exception e) {
            return null; // return response for bad request
        }
    }
}
