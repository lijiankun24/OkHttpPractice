package com.lijiankun24.okhttppractice;

import android.app.Application;

import com.lijiankun24.okhttppractice.utils.MobileUtil;

/**
 * MyApplication.java
 * <p>
 * Created by lijiankun on 17/8/31.
 */

public class MyApplication extends Application {

    private static Application INSTANCE = null;

    public static Application getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        MobileUtil.init(this);
    }
}
