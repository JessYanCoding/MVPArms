package com.jess.arms.base;

import android.app.Application;
import android.content.Context;

import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;

/**
 * 本项目由
 * mvp
 * +dagger2
 * +retrofit
 * +rxjava
 * +androideventbus
 * +butterknife组成
 * 请配合官方wiki文档https://github.com/JessYanCoding/MVPArms/wiki,学习本框架
 */
public class BaseApplication extends Application implements App {
    private AppLifecycles mAppDelegate;

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAppDelegate.onCreate(this);
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @Override
    public AppComponent getAppComponent() {
        return ((App) mAppDelegate).getAppComponent();
    }

}
