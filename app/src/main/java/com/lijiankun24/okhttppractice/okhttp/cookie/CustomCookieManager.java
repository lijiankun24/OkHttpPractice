package com.lijiankun24.okhttppractice.okhttp.cookie;

import android.content.Context;

import com.lijiankun24.okhttppractice.utils.L;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

/**
 * CustomCookieManager.java
 * <p>
 * Created by lijiankun on 17/8/25.
 */

class CustomCookieManager extends CookieManager {

    private static CustomCookieManager INSTANCE = null;

    private CookieStore mCookieStore = null;

    private CustomCookieManager(Context context) {
        this(CustomCookieStore.getInstance(context));
    }

    private CustomCookieManager(CookieStore store) {
        this(store, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        mCookieStore = store;
    }

    private CustomCookieManager(CookieStore store, CookiePolicy cookiePolicy) {
        super(store, cookiePolicy);
    }

    static CustomCookieManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CustomCookieManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CustomCookieManager(context);
                }
            }
        }
        return INSTANCE;
    }

    void put(URI uri, List<Cookie> cookieList) {
        if (uri == null || cookieList == null || cookieList.size() == 0) {
            L.e("uri == null or cookie == null in CustomCookieStore");
        }

        Map<String, List<String>> map = new HashMap<>();
        List<String> value = new ArrayList<>();
        for (Cookie cookie : cookieList) {
            value.add(cookie.toString());
        }
        map.put("Set-Cookie", value);
        try {
            put(uri, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Cookie> get(URI uri) {
        List<Cookie> result = new ArrayList<>();
        List<HttpCookie> cookieList = mCookieStore.get(uri);
        for (HttpCookie cookie : cookieList) {
            Cookie cookie1 = httpCookieToCookie(cookie);
            result.add(cookie1);
        }
        return result;
    }

    void remove(URI uri, Cookie cookie) {
        mCookieStore.remove(uri, cookieToHttpCookie(cookie));
    }

    void removeAll() {
        mCookieStore.removeAll();
    }

    private HttpCookie cookieToHttpCookie(Cookie cookie) {
        HttpCookie httpCookie = new HttpCookie(cookie.name(), cookie.value());
        httpCookie.setDomain(cookie.domain());
        httpCookie.setPath(cookie.path());
        httpCookie.setMaxAge(cookie.expiresAt());
        return httpCookie;
    }

    private Cookie httpCookieToCookie(HttpCookie cookie) {
        return new Cookie.Builder()
                .name(cookie.getName())
                .value(cookie.getValue())
                .domain(cookie.getDomain())
                .path(cookie.getPath())
                .expiresAt(cookie.getMaxAge())
                .build();
    }
}
