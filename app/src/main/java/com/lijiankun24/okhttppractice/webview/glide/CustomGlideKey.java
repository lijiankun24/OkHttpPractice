package com.lijiankun24.okhttppractice.webview.glide;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.signature.EmptySignature;

import java.security.MessageDigest;

/**
 * CustomGlideKey.java
 * <p>
 * Created by lijiankun on 17/9/7.
 */

class CustomGlideKey implements Key {

    private Key mSignature = null;

    private String mUrl = null;

    CustomGlideKey(String url) {
        this.mUrl = url;
        mSignature = EmptySignature.obtain();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof CustomGlideKey) || obj.getClass() != this.getClass()) {
            return false;
        }

        CustomGlideKey key = (CustomGlideKey) obj;
        return (this.mUrl.equals(key.mUrl) && this.mSignature.equals(key.mSignature));
    }

    @Override
    public int hashCode() {
        int result = mUrl.hashCode();
        result = 31 * result + mSignature.hashCode();
        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(mUrl.getBytes());
        mSignature.updateDiskCacheKey(messageDigest);
    }
}
