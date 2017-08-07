package com.sgcc.smarteleclife;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
