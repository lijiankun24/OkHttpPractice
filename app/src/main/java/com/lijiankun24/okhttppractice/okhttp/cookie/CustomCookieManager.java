package com.lijiankun24.okhttppractice.okhttp.cookie;

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

    private CustomCookieManager() {
    }

    public static CustomCookieManager getInstance() {
        if (INSTANCE == null) {
            synchronized (CustomCookieManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CustomCookieManager();
                }
            }
        }
        return INSTANCE;
    }

    public CustomCookieManager(CookieStore store) {
        this(store, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    public CustomCookieManager(CookieStore store, CookiePolicy cookiePolicy) {
        super(store, cookiePolicy);
    }
}
