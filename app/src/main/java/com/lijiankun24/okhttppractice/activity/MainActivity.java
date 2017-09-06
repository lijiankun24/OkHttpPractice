package com.lijiankun24.okhttppractice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lijiankun24.okhttppractice.R;
import com.lijiankun24.okhttppractice.okhttp.OkHttpManager;
import com.lijiankun24.okhttppractice.okhttp.OnHttpListener;
import com.lijiankun24.okhttppractice.utils.L;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    private String mUrl = "http://www.jianshu.com/u/1abe21b7ff5f";

    private String mUrl = "https://kyfw.12306.cn/otn/";

//    private String mUrl = "http://ke.youdao.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_okhttp:
                request();
                break;
            case R.id.tv_webview:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
        }
    }

    private void request() {
        OkHttpManager.getInstance(MainActivity.this).addGetStringRequest(mUrl, new OnHttpListener<String>() {
            @Override
            public void onSuccess(String result) {
                L.i("onSuccess ");
            }

            @Override
            public void onError() {
                L.i("onError ");
            }
        });
    }

    private void initView() {
        findViewById(R.id.tv_okhttp).setOnClickListener(this);
        findViewById(R.id.tv_webview).setOnClickListener(this);
    }
}
