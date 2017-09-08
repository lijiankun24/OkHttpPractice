package com.lijiankun24.okhttppractice.webview.glide;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;

import java.io.InputStream;

/**
 * ImageInputStreamModelLoader.java
 * <p>
 * Created by lijiankun on 17/9/7.
 */

public class ImageInputStreamModelLoader implements ModelLoader<String, InputStream> {

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(String s, int width, int height, Options options) {
        return null;
    }

    @Override
    public boolean handles(String s) {
        return false;
    }
}
