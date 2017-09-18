package com.lijiankun24.okhttppractice.webview;

import android.util.Log;
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

    private long mStartTime = 0;

    public CustomWebChromeClient(long startTime) {
        mStartTime = startTime;
    }

    @Override
    public void onReceivedTitle (WebView view, String title) {
        Log.i("lijk", "init WebView time is " + (System.currentTimeMillis() - mStartTime));
//        registerOnLoadHandler(view);
//        set(view);
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

    private void set(WebView view){
        view.loadUrl("javascript:" +
                "window.addEventListener('DOMContentLoaded', function() {" +
                "prompt('domc:' + new Date().getTime());})"
        );
    }

    private void registerOnLoadHandler(WebView webView) {
        webView.loadUrl("javascript:" + jsString +
                "window.addEventListener('DOMContentLoaded', function() {" +
                "first_screen();});"
        );
    }

    private String jsString = "function first_screen () {\n" +
            "\tvar imgs = document.getElementsByTagName(\"img\"), fs = +new Date;\n" +
            "\tvar fsItems = [], that = this;\n" +
            "    function getOffsetTop(elem) {\n" +
            "        var top = 0;\n" +
            "        top = window.pageYOffset ? window.pageYOffset : document.documentElement.scrollTop;\n" +
            "        try{\n" +
            "            top += elem.getBoundingClientRect().top;    \n" +
            "        }catch(e){\n" +
            "\n" +
            "        }finally{\n" +
            "            return top;\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "    var loadEvent = function() {\n" +
            "        //gif避免\n" +
            "        if (this.removeEventListener) {\n" +
            "            this.removeEventListener(\"load\", loadEvent, false);\n" +
            "        }\n" +
            "        fsItems.push({\n" +
            "            img : this,\n" +
            "            time : +new Date\n" +
            "        });\n" +
            "    }\n" +
            "    for (var i = 0; i < imgs.length; i++) {\n" +
            "        (function() {\n" +
            "            var img = imgs[i];\n" +
            "\n" +
            "            if (img.addEventListener) {\n" +
            "\n" +
            "                !img.complete && img.addEventListener(\"load\", loadEvent, false);\n" +
            "            } else if (img.attachEvent) {\n" +
            "\n" +
            "                img.attachEvent(\"onreadystatechange\", function() {\n" +
            "                    if (img.readyState == \"complete\") {\n" +
            "                        loadEvent.call(img, loadEvent);\n" +
            "                    }\n" +
            "\n" +
            "                });\n" +
            "            }\n" +
            "\n" +
            "        })();\n" +
            "    }\n" +
            "    function firstscreen_time() {\n" +
            "        var sh = document.documentElement.clientHeight;\n" +
            "        for (var i = 0; i < fsItems.length; i++) {\n" +
            "            var item = fsItems[i], img = item['img'], time = item['time'], top = getOffsetTop(img);\n" +
            "            if (top > 0 && top < sh) {\n" +
            "                fs = time > fs ? time : fs;\n" +
            "            }\n" +
            "        }\n" +
            "        return fs;\n" +
            "    }      \n" +
            "    window.addEventListener('load', function() {\n" +
            "    \t\t\t\t\t\tprompt('firstscreen:' + firstscreen_time());\n" +
            "    \t\t\t\t\t});\n" +
            "}";
}
