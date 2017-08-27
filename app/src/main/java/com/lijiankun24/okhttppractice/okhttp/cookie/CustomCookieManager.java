package com.lijiankun24.okhttppractice.okhttp.cookie;

import android.content.Context;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;

/**
 * CustomCookieManager.java
 * <p>
 * Created by lijiankun on 17/8/25.
 */

public class CustomCookieManager extends CookieManager {

    private static CustomCookieManager INSTANCE = null;

    private CustomCookieManager(Context context) {
        this(CustomCookieStore.getInstance(context));
    }

    private CustomCookieManager(CookieStore store) {
        this(store, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
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
}
