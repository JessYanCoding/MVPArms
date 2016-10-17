package me.jessyan.mvparms.demo.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.ImageModule;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Component;
import me.jessyan.mvparms.demo.di.module.CacheModule;
import me.jessyan.mvparms.demo.di.module.ServiceModule;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CacheManager;
import me.jessyan.mvparms.demo.mvp.model.api.service.ServiceManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;

/**
 * Created by jess on 8/4/16.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ServiceModule.class, ImageModule.class, CacheModule.class})
public interface AppComponent {
    Application Application();

    //服务管理器,retrofitApi
    ServiceManager serviceManager();

    //缓存管理器
    CacheManager cacheManager();

    //Rxjava错误处理管理类
    RxErrorHandler rxErrorHandler();

    //用于请求权限,适配6.0的权限管理
    RxPermissions rxPermissions();

    OkHttpClient okHttpClient();

    //图片管理器,用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    ImageLoader imageLoader();

    //gson
    Gson gson();
}
