package com.sgcc.smarteleclife;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.sgcc.greendao.gen.DaoMaster;
import com.sgcc.greendao.gen.DaoSession;

/**
 * Created by miao_wenlong on 2017/8/3.
 */

public class MyApp extends Application {
    public static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "main-db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }
}
