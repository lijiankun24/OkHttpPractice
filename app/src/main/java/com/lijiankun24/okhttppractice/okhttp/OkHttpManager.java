package com.lijiankun24.okhttppractice.okhttp;

import android.content.Context;

import com.lijiankun24.okhttppractice.MyApplication;
import com.lijiankun24.okhttppractice.okhttp.cache.CustomCache;
import com.lijiankun24.okhttppractice.okhttp.cache.LocalCacheInterceptor;
import com.lijiankun24.okhttppractice.okhttp.cache.NetCacheInterceptor;
import com.lijiankun24.okhttppractice.okhttp.cookie.CustomCookieJar;
import com.lijiankun24.okhttppractice.okhttp.interceptor.HeaderInterceptor;
import com.lijiankun24.okhttppractice.okhttp.interceptor.LogInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttpManager.java
 * <p>
 * Created by lijiankun on 17/8/25.
 */


public class OkHttpManager {

    private static OkHttpManager INSTANCE = null;

    private static OkHttpClient sHttpClient = null;

    private OkHttpManager(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(CustomCache.getCache())
                .cookieJar(new CustomCookieJar(context))
                .addNetworkInterceptor(new NetCacheInterceptor())
                .addInterceptor(new LocalCacheInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LogInterceptor());
        setSSL(builder);
        sHttpClient = builder.build();
    }

    public static OkHttpManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (OkHttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkHttpManager(context);
                }
            }
        }
        return INSTANCE;
    }

    public OkHttpClient getHttpClient() {
        return sHttpClient;
    }

    public void addGetStringRequest(String url, final OnHttpListener<String> httpListener) {
        final Request request = new Request.Builder()
                .method("GET", null)
                .url(url)
                .build();

        Call call = getHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpListener.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    httpListener.onSuccess(response.body().string());
                }
            }
        });
    }

    private void setSSL(OkHttpClient.Builder builder) {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SSLSocketFactory getCertificates() {
        InputStream inputStream = null;
        try {
            inputStream = MyApplication.getInstance().getAssets().open("srca.cer");
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("1", certificateFactory.generateCertificate(null));
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
