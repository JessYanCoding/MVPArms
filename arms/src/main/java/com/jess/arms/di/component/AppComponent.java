package com.jess.arms.di.component;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;

/**
 * ================================================
 * 可通过 {@link ArmsUtils#obtainAppComponentFromContext(Context)} 拿到此接口的实现类
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 * <p>
 * Created by JessYan on 8/4/2016
 * Contact with jess.yan.effort@gmail.com
 * Follow me on https://github.com/JessYanCoding
 * ================================================
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {
    Application application();

    //用于管理所有 activity
    AppManager appManager();

    //用于管理网络请求层,以及数据缓存层
    IRepositoryManager repositoryManager();

    //Rxjava 错误处理管理类
    RxErrorHandler rxErrorHandler();

    //图片管理器,用于加载图片的管理类,默认使用 Glide ,使用策略模式,可在运行时替换框架
    ImageLoader imageLoader();

    OkHttpClient okHttpClient();

    //gson
    Gson gson();

    //缓存文件根目录(RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下),应该将所有缓存都放到这个根目录下,便于管理和清理,可在 GlobalConfigModule 里配置
    File cacheFile();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Map<String, Object> extras();

    void inject(AppDelegate delegate);
}
