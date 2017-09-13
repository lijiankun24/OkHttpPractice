package com.lijiankun24.okhttppractice.webview.util;

import android.support.annotation.NonNull;

import com.lijiankun24.okhttppractice.utils.L;
import com.lijiankun24.okhttppractice.webview.imageloader.ImageLoaderManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * WebResourceInputStream.java
 * <p>
 * Created by lijiankun on 17/9/13.
 */

public class WebResourceInputStream extends InputStream {

    private ByteArrayOutputStream mOutputStream = null;
    private InputStream mInnerIs = null;
    private String mImageName = null;
    private int mCurrentLen = 0;
    private int mContentLen = 0;

    public WebResourceInputStream(int contentLength, InputStream is, String url) {
        super();
        this.mContentLen = contentLength;
        this.mInnerIs = is;
        mImageName = Md5Util.md5(url);
        mOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public int available() throws IOException {
        return mInnerIs.available();
    }

    @Override
    public void close() throws IOException {
        super.close();
        mInnerIs.close();
    }

    @Override
    public void mark(int readlimit) {
        super.mark(readlimit);
        mInnerIs.mark(readlimit);
    }

    @Override
    public boolean markSupported() {
        return mInnerIs.markSupported();
    }

    @Override
    public int read(@NonNull byte[] buffer) throws IOException {
        int count = mInnerIs.read(buffer);
        writeToCache(buffer, 0, count);
        return count;
    }

    @Override
    public int read(@NonNull byte[] buffer, int byteOffset, int byteCount) throws IOException {
        int count = mInnerIs.read(buffer, byteOffset, byteCount);
        writeToCache(buffer, byteOffset, count);
        return count;
    }

    @Override
    public synchronized void reset() throws IOException {
        super.reset();
        mInnerIs.reset();
    }

    @Override
    public long skip(long byteCount) throws IOException {
        return mInnerIs.skip(byteCount);
    }

    @Override
    public int read() throws IOException {
        return mInnerIs.read();
    }

    private void writeToCache(byte[] buffer, int byteOffset, int realCount) {
        InputStream inputStream = null;
        try {
            if (mOutputStream != null) {
                if (realCount > 0) {
                    mCurrentLen += realCount;
                    mOutputStream.write(buffer, byteOffset, realCount);
                }

                if (mCurrentLen == mContentLen) {
                    mOutputStream.flush();
                    inputStream = new ByteArrayInputStream(mOutputStream.toByteArray());
                    ImageLoaderManager.getInstance().saveDiskCache(mImageName, inputStream);
                }
            }
        } catch (Exception e) {
            L.e(e.toString());
        } finally {
            StreamUtil.safeClose(inputStream);
        }
    }
}
