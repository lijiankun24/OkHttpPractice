package com.lijiankun24.okhttppractice;

import android.app.Application;

import com.lijiankun24.okhttppractice.utils.MobileUtil;

/**
 * MyApplication.java
 * <p>
 * Created by lijiankun on 17/8/31.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobileUtil.init(this);
    }
}
