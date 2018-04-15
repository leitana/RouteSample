package com.boco.routesample;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by 11300 on 2018/4/11.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
