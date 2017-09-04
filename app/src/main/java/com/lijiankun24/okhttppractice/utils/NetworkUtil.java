package com.lijiankun24.okhttppractice.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetworkUtil.java
 * <p>
 * Created by lijiankun on 17/9/4.
 */

public class NetworkUtil {

    private NetworkUtil() {
    }

    /**
     * 判断网络是否可用
     *
     * @param context 上下文
     * @return true 网络可用，false 网络不可用
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
