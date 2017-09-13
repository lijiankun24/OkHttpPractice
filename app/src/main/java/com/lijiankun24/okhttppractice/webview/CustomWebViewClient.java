package com.lijiankun24.okhttppractice.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lijiankun24.okhttppractice.okhttp.OkHttpManager;
import com.lijiankun24.okhttppractice.webview.imageloader.ImageLoaderManager;
import com.lijiankun24.okhttppractice.webview.util.WebResourceInputStream;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * CustomWebViewClient.java
 * <p>
 * Created by lijiankun on 17/9/13.
 */

public class CustomWebViewClient extends WebViewClient {

    private String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "JPG", "JPEG", "PNG", "GIF"};

    private Context mContext = null;

    public CustomWebViewClient(Context context) {
        mContext = context;
    }

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

    @TargetApi(21)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return getResponseFromCache(request.getUrl().toString());
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return getResponseFromCache(url);
    }

    private WebResourceResponse getResponseFromCache(String url) {
        WebResourceResponse response = null;
        if (isImage(url) || url.contains("image")) {
            String ext = MimeTypeMap.getFileExtensionFromUrl(url);
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
            InputStream inputStream = ImageLoaderManager.getInstance().getDiskCache(url);
            if (inputStream != null) {
                response = new WebResourceResponse(mimeType, "utf-8", inputStream);
            } else {
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Call call = OkHttpManager.getInstance(mContext)
                            .getHttpClient()
                            .newCall(request);
                    ResponseBody responseBody = call.execute().body();
                    if (responseBody != null) {
                        long contentLength = responseBody.contentLength();
                        InputStream isFromNet = responseBody.byteStream();
                        InputStream webResourceInputStream = new WebResourceInputStream((int) contentLength,
                                isFromNet, url);
                        response = new WebResourceResponse(mimeType, "utf-8", webResourceInputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
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
}
