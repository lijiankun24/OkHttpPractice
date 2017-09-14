package com.lijiankun24.okhttppractice.webview.glide;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.bitmap_recycle.LruArrayPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.model.StreamEncoder;
import com.lijiankun24.okhttppractice.webview.util.StreamUtil;

import java.io.File;
import java.io.InputStream;

/**
 * CustomGlideWrite.java
 * <p>
 * Created by lijiankun on 17/9/14.
 */

class CustomGlideWrite implements DiskCache.Writer {
    // 4MB.
    private static final int DEFAULT_SIZE = 4 * 1024 * 1024;

    private InputStream mInputStream = null;

    private StreamEncoder mEncoder = null;

    CustomGlideWrite(InputStream inputStream) {
        mInputStream = inputStream;
        mEncoder = new StreamEncoder(new LruArrayPool(DEFAULT_SIZE));
    }

    @Override
    public boolean write(File file) {
        boolean isSuccess = false;
        try {
            isSuccess = mEncoder.encode(mInputStream, file, new Options());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.safeClose(mInputStream);
        }
        return isSuccess;
    }
}
