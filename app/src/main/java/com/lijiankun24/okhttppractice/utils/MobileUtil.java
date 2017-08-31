package com.lijiankun24.okhttppractice.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.URLEncoder;

/**
 * MobileUtil.java
 * <p>
 * Created by lijiankun on 17/8/31.
 */

public class MobileUtil {

    private static MobileUtil INSTANCE;

    private static Context mContext;

    private static ConnectivityManager mConnManager;

    private static PackageInfo mPackageInfo;

    private static String mid = encode(android.os.Build.VERSION.RELEASE);

    private static String model = escapeSource(android.os.Build.MODEL);

    private MobileUtil(Context context) {
        mContext = context.getApplicationContext();
        mConnManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            mPackageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void init(Context context) {
        if (INSTANCE == null) {
            synchronized (MobileUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MobileUtil(context);
                }
            }
        }
    }

    public static String getSSID() {
        WifiManager wifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        if (ssid == null) {
            return "";
        }
        if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        return ssid;
    }

    public static String getNetwork() {
        if (mConnManager == null)
            return "unknown";
        NetworkInfo activeNetInfo = mConnManager.getActiveNetworkInfo();
        if (activeNetInfo == null)
            return "unknown";
        switch (activeNetInfo.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                return "wifi";
            case ConnectivityManager.TYPE_MOBILE:
                switch (activeNetInfo.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        return "unknown";
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        return "2g";
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return "2g";
                    default:
                        return "3g";
                }
            default:
                return activeNetInfo.getTypeName();
        }
    }

    public static String getVersion() {
        return mPackageInfo.versionName;
    }

    public static String getMid() {
        return mid;
    }

    public static String getModel() {
        return model;
    }

    private static String escapeSource(String src) {
        StringBuilder sb = new StringBuilder();
        for (char c : src.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                sb.append(c);
            } else if (c >= 'A' && c <= 'Z') {
                sb.append(c);
            } else if (c >= '0' && c <= '9') {
                sb.append(c);
            } else if (c == '.' || c == '_' || c == '-' || c == '/') {
                sb.append(c);
            } else if (c == ' ') {
                sb.append('_');
            }
        }
        return sb.toString();
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8");
        } catch (Exception e) {
            return "";
        }
    }
}
