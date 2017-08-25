package com.lijiankun24.okhttppractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lijiankun24.okhttppractice.okhttp.OkHttpManager;
import com.lijiankun24.okhttppractice.okhttp.OnHttpListener;
import com.lijiankun24.okhttppractice.utils.L;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String mUrl = "http://www.jianshu.com/u/1abe21b7ff5f";

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
        }
    }

    private void request() {
        OkHttpManager.getInstance().addGetStringRequest(mUrl, new OnHttpListener<String>() {
            @Override
            public void onSuccess(String result) {
                L.i("onSuccess " + result);
            }

            @Override
            public void onError() {
                L.i("onError ");
            }
        });
    }

    private void initView() {
        findViewById(R.id.tv_okhttp).setOnClickListener(this);
    }
}
