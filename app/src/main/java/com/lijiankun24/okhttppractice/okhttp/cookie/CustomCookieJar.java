package com.lijiankun24.okhttppractice.okhttp.cookie;

import com.lijiankun24.okhttppractice.utils.L;

import java.net.HttpCookie;
import java.util.ArrayList;
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

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            HttpCookie httpCookie = new HttpCookie(cookie.name(), cookie.value());
            L.i(cookie.toString());
            L.i(httpCookie.toString());
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return new ArrayList<>();
    }
}
