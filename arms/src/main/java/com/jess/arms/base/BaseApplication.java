package com.jess.arms.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.jess.arms.BuildConfig;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.ImageModule;

import java.util.LinkedList;

import timber.log.Timber;

/**
 * 本项目由
 * mvp
 * +dagger2
 * +retrofit
 * +rxjava
 * +androideventbus
 * +butterknife组成
 */
public abstract class BaseApplication extends Application {
    static private BaseApplication mApplication;
    public LinkedList<BaseActivity> mActivityList;
    private ClientModule mClientModule;
    private AppModule mAppModule;
    private ImageModule mImagerModule;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        this.mClientModule = new ClientModule(getBaseUrl());//用于提供okhttp和retrofit的单列
        this.mAppModule = new AppModule(this);//提供application
        this.mImagerModule = new ImageModule();//图片加载框架默认使用glide

        if (BuildConfig.DEBUG) {//Timber
            Timber.plant(new Timber.DebugTree());
        }
    }

    public abstract String getBaseUrl();


    public LinkedList<BaseActivity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<BaseActivity>();
        }
        return mActivityList;
    }



    public ClientModule getClientModule() {
        return mClientModule;
    }

    public AppModule getAppModule() {
        return mAppModule;
    }

    public ImageModule getImageModule(){return mImagerModule;}

    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return mApplication;
    }


    /**
     * 退出所有activity
     */
    public static void killAll() {
        Intent intent = new Intent(BaseActivity.ACTION_RECEIVER_ACTIVITY);
        intent.putExtra("type", "killAll");
        getContext().sendBroadcast(intent);
    }

}
