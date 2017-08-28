package com.lijiankun24.okhttppractice.okhttp.cookie;

import android.content.Context;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * CustomCookieJar.java
 * <p>
 * Created by lijiankun on 17/8/25.
 */

public class CustomCookieJar implements CookieJar {

    private Context mContext = null;

    public CustomCookieJar(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        CustomCookieManager.getInstance(mContext).put(httpurlToURI(url), cookies);
//        Map<String, List<String>> map = new HashMap<>();
//        List<String> cookieValue = new ArrayList<>();
//        for (Cookie cookie : cookies) {
//            cookieValue.add(cookie.toString());
//        }
//        map.put("Set-Cookie", cookieValue);
//        try {
//            CustomCookieManager.getInstance(mContext).put(httpurlToURI(url), map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return CustomCookieManager.getInstance(mContext).get(httpurlToURI(url));
    }

    private URI httpurlToURI(HttpUrl httpUrl) {
        try {
            return new URI("http", httpUrl.host(), null, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
