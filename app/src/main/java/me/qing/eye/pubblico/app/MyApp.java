package me.qing.eye.pubblico.app;

import com.jess.arms.base.BaseApplication;

/**
 * Application
 *
 * Created by QING on 2017/10/21.
 */

public class MyApp extends BaseApplication {

    private static MyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApp getInstance() {
        return sInstance;
    }
}
