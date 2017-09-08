package com.lijiankun24.okhttppractice.webview.glide;

import com.bumptech.glide.load.Key;

import java.security.MessageDigest;

/**
 * CustomGlideKey.java
 * <p>
 * Created by lijiankun on 17/9/7.
 */

public class CustomGlideKey implements Key {

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        throw new UnsupportedOperationException();
    }
}
