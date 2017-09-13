package com.lijiankun24.okhttppractice.webview;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.lijiankun24.okhttppractice.utils.L;

/**
 * CustomWebChromeClient.java
 * <p>
 * Created by lijiankun on 17/9/13.
 */

public class CustomWebChromeClient extends WebChromeClient {

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
