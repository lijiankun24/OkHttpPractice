package com.lijiankun24.okhttppractice.webview.util;

import com.lijiankun24.okhttppractice.utils.L;

import java.io.Closeable;
import java.io.IOException;

/**
 * StreamUtil.java
 * <p>
 * Created by lijiankun on 17/9/13.
 */

public class StreamUtil {

    public static void safeClose(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            L.e(e.toString());
        }
    }
}
