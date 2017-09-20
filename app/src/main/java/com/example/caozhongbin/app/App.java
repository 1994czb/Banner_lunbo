package com.example.caozhongbin.app;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/9/20.
 */

public class App extends Application {

    private static OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        okHttpClient = new OkHttpClient();
        okHttpClient =okHttpClient.newBuilder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                //拦截器
//                .addInterceptor(new MyLogInterceptor())
                .build();
    }

    public static OkHttpClient okHttpClient(){
        return okHttpClient;
    }
}
