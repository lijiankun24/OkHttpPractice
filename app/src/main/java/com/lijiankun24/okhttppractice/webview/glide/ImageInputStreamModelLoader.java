package com.lijiankun24.okhttppractice.webview.glide;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

/**
 * ImageInputStreamModelLoader.java
 * <p>
 * Created by lijiankun on 17/9/7.
 */

public class ImageInputStreamModelLoader implements ModelLoader<String, InputStream> {

    private final ModelCache<String, String> mStringModelCache;

    public ImageInputStreamModelLoader() {
        this(null);
    }

    public ImageInputStreamModelLoader(ModelCache<String, String> stringModelCache) {
        mStringModelCache = stringModelCache;
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(String s, int width, int height, Options options) {
        String url = s;
        if (mStringModelCache != null) {
            url = mStringModelCache.get(s, 0, 0);
            if (url == null) {
                mStringModelCache.put(s, 0, 0, s);
                url = s;
            }
        }
        return new LoadData<>(new GlideUrl(url), new ImageInputStreamFetcher(url));
    }

    @Override
    public boolean handles(String s) {
        return false;
    }

    public static class Factory implements ModelLoaderFactory<String, InputStream> {

        private final ModelCache<String, String> mModelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<String, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new ImageInputStreamModelLoader(mModelCache);
        }

        @Override
        public void teardown() {

        }
    }
}
