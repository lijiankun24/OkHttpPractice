package com.lijiankun24.okhttppractice.okhttp.cookie;

import android.content.Context;

import com.google.gson.Gson;
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

public class CustomCookieManager extends CookieManager {

    private static CustomCookieManager INSTANCE = null;

    private CookieStore mCookieStore = null;

    private Gson mGson = new Gson();

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

    public static CustomCookieManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CustomCookieManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CustomCookieManager(context);
                }
            }
        }
        return INSTANCE;
    }

    public void put(URI uri, List<Cookie> cookieList) {
        if (uri == null || cookieList == null || cookieList.size() == 0) {
            L.e("uri == null or cookie == null in CustomCookieStore");
        }

        Map<String, List<String>> map = new HashMap<>();
        List<String> value = new ArrayList<>();
        for (Cookie cookie : cookieList) {
//            value.add(mGson.toJson(cookie, Cookie.class));
            value.add(cookie.toString());
        }
        map.put("Set-Cookie", value);
        try {
            put(uri, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cookie> get(URI uri) {
        List<Cookie> result = new ArrayList<>();
        List<HttpCookie> cookieList = mCookieStore.get(uri);
        L.i("===== ");
        return result;
    }
}
