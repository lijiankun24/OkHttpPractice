package com.lijiankun24.okhttppractice.okhttp;

/**
 * OnHttpListener.java
 * <p>
 * Created by lijiankun on 17/8/25.
 */

public interface OnHttpListener<T> {

    void onSuccess(T result);

    void onError();
}
