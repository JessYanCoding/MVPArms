package com.jess.arms.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobeConfigModule;
import com.jess.arms.di.module.ImageModule;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by jess on 8/4/16.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ImageModule.class, GlobeConfigModule.class})
public interface AppComponent {
    Application getApplication();

    //用于管理网络请求层,以及数据缓存层
    IRepositoryManager getRepositoryManager();

    OkHttpClient okHttpClient();

    //图片管理器,用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    ImageLoader getImageLoader();

    //gson
    Gson getGson();

    //缓存文件根目录(RxCache和Glide的的缓存都已经作为子文件夹在这个目录里),应该将所有缓存放到这个根目录里,便于管理和清理,可在GlobeConfigModule里配置
    File getCacheFile();

    //用于管理所有activity
    AppManager getAppManager();

    void inject(BaseApplication application);
}
