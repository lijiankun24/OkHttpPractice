package com.lijiankun24.okhttppractice.okhttp.cookie;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CustomCookieStore.java
 * <p>
 * Created by lijiankun on 17/8/25.
 */

public class CustomCookieStore implements CookieStore {

    private final Map<URI, List<HttpCookie>> mCookieCache = new HashMap<>();

    private final String COOKIE_STORE = "cookie_store";

    private final String CUSTOM_DOMAIN = "jianshu.com";

    private static CustomCookieStore INSTANCE = null;

    private SharedPreferences mPreferences = null;

    private CustomCookieStore(Context context) {
        mPreferences = context.getSharedPreferences(COOKIE_STORE, Context.MODE_PRIVATE);
    }

    public static CustomCookieStore getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CustomCookieStore.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CustomCookieStore(context);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        JSONObject object = new JSONObject();
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return null;
    }

    @Override
    public List<HttpCookie> getCookies() {
        return null;
    }

    @Override
    public List<URI> getURIs() {
        return null;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }
}
