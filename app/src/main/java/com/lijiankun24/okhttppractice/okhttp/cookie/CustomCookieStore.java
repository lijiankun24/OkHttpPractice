package com.lijiankun24.okhttppractice.okhttp.cookie;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lijiankun24.okhttppractice.utils.L;

import org.json.JSONArray;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    private static CustomCookieStore INSTANCE = null;

    private SharedPreferences mPreferences = null;

    private Gson mGson = new Gson();

    private CustomCookieStore(Context context) {
        mPreferences = context.getSharedPreferences(COOKIE_STORE, Context.MODE_PRIVATE);
        init();
    }

    @TargetApi(19)
    private void init() {
        Map<String, String> persistent = (Map<String, String>) mPreferences.getAll();
        for (Map.Entry<String, String> entry : persistent.entrySet()) {
            URI uri;
            List<HttpCookie> value;
            try {
                uri = mGson.fromJson(entry.getKey(), URI.class);
                value = mGson.fromJson(entry.getValue(), new TypeToken<List<HttpCookie>>(){
                }.getType());
                mCookieCache.put(uri, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        if (cookie == null || uri == null) {
            L.e("cookie == null or uri == null in CustomCookieStore");
            return;
        }
        uri = cookiesUri(uri);
        List<HttpCookie> cookieList = mCookieCache.get(uri);
        if (cookieList == null || cookieList.size() == 0) {
            cookieList = new ArrayList<>();
        }
        if (cookieList.contains(cookie)) {
            cookieList.remove(cookie);
        }
        cookieList.add(cookie);
        mCookieCache.put(uri, cookieList);
        refreshCookie(uri);
    }

    private void refreshCookie(URI uri) {
        List<HttpCookie> cookieList = mCookieCache.get(uri);
        String key = mGson.toJson(uri).toString();
        String value = mGson.toJson(cookieList).toString();
        mPreferences.edit()
                .putString(key, value)
                .commit();
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        List<HttpCookie> result = new ArrayList<>();
        if (uri == null) {
            L.e("uri == null in CustomCookieStore.get(URI)");
            return result;
        }
        uri = cookiesUri(uri);
        List<HttpCookie> cookies = mCookieCache.get(uri);
        if (cookies != null && cookies.size() > 0) {
            for (Iterator<HttpCookie> iterator = cookies.iterator(); iterator.hasNext(); ) {
                HttpCookie cookie = iterator.next();
                if (cookie.hasExpired()) {
                    iterator.remove();
                } else {
                    result.add(cookie);
                }
            }
        }
        return result;
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

    private URI cookiesUri(URI uri) {
        try {
            return new URI("http", uri.getHost(), null, null);
        } catch (URISyntaxException e) {
            return uri; // probably a URI with no host
        }
    }

    private String httpCookieToJson(@NonNull List<HttpCookie> cookieList) {
        if (cookieList == null || cookieList.size() == 0) {
            return null;
        }
        JSONArray array = new JSONArray();
        for (HttpCookie cookie : cookieList) {
            array.put(cookie);
        }
        return array.toString();
    }
}
