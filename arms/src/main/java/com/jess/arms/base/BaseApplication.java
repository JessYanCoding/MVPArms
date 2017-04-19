package com.jess.arms.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.jess.arms.common.utils.HandlerUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.component.DaggerAppComponent;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobeConfigModule;
import com.jess.arms.di.module.ImageModule;
import com.jess.arms.integration.ActivityLifecycle;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.integration.ManifestParser;
import com.jess.arms.netstate.NetworkStateReceiver;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.List;

import javax.inject.Inject;


/**
 * 本项目由
 * mvp
 * +dagger2
 * +retrofit
 * +rxjava
 * +androideventbus
 * +butterknife组成
 */
public abstract class BaseApplication extends MultiDexApplication {
    private static BaseApplication mApplication;

    private AppModule mAppModule;
    private GlobeConfigModule mGlobeConfigModule;

    @Inject
    protected ActivityLifecycle mActivityLifecycle;

    private AppComponent mAppComponent;

    /**
     * 当前是否是Debug模式
     */
    protected abstract boolean isDebug();

    /**
     * Log的TAG
     */
    protected abstract String getTag();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initLogUtils();
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);




        mAppModule = new AppModule(this);
        mGlobeConfigModule = getGlobeConfigModule(this);

        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(mAppModule)////提供application
                .clientModule(new ClientModule())//用于提供okhttp和retrofit的单例
                .imageModule(new ImageModule())//图片加载框架默认使用glide
                .globeConfigModule(mGlobeConfigModule)//全局配置
                .build();
        mAppComponent.inject(this);



        //注册网络状态广播
        NetworkStateReceiver.registerNetworkStateReceiver(this);
        registerActivityLifecycleCallbacks(mActivityLifecycle);
    }


    private void initLogUtils() {
        LogUtils.getLogConfig().configAllowLog(isDebug()).configTagPrefix(getTag()).configShowBorders(true).configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}").configLevel(LogLevel.TYPE_VERBOSE);
    }



    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        HandlerUtil.removeCallbacksAndMessages();

        //注销网络状态广播
        NetworkStateReceiver.unRegisterNetworkStateReceiver(this);

        if (mGlobeConfigModule != null) {
            mGlobeConfigModule.release();
            mGlobeConfigModule = null;
        }


        if (mAppModule != null) {
            mAppModule.release();
            mAppModule = null;
        }

        if (mActivityLifecycle != null) {
            unregisterActivityLifecycleCallbacks(mActivityLifecycle);
            mActivityLifecycle.release();
            mActivityLifecycle = null;
        }


        if (mAppComponent != null) {
            AppManager appManager = mAppComponent.getAppManager();
            if (appManager != null) {
                appManager.release();
            }
            IRepositoryManager repositoryManager = mAppComponent.getRepositoryManager();
            if (repositoryManager != null) {
                repositoryManager.release();
            }
            mAppComponent = null;
        }

        if (mApplication != null) {
            mApplication = null;
        }

        super.onTerminate();
    }


    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link ConfigModule}的实现类,和Glide的配置方式相似
     *
     * @return
     */
    private GlobeConfigModule getGlobeConfigModule(Application context) {
        GlobeConfigModule.Buidler builder = GlobeConfigModule
                .buidler()
                .baseurl("https://api.github.com");//为了防止用户没有通过GlobeConfigModule配置baseurl,而导致报错,所以提前配置个默认baseurl

        List<ConfigModule> modules = new ManifestParser(this).parse();

        for (ConfigModule module : modules) {
            module.applyOptions(context, builder);
        }
        return builder.build();
    }

    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return mApplication;
    }

}
