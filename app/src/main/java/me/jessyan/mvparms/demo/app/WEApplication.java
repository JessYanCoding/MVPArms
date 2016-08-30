package me.jessyan.mvparms.demo.app;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.http.GlobeHttpResultHandler;

import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.di.component.DaggerAppComponent;
import me.jessyan.mvparms.demo.di.module.CacheModule;
import me.jessyan.mvparms.demo.di.module.ServiceModule;
import me.jessyan.mvparms.demo.mvp.model.api.Api;
import timber.log.Timber;

/**
 * Created by jess on 8/5/16 11:07
 * contact with jess.yan.effort@gmail.com
 */
public class WEApplication extends BaseApplication {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(getAppModule())
                .clientModule(getClientModule())
                .imageModule(getImageModule())
                .serviceModule(new ServiceModule())
                .cacheModule(new CacheModule())
                .build();
    }

    @Override
    public String getBaseUrl() {
        return Api.APP_DOMAIN;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    /**
     * 这里可以提供一个全局处理http响应结果的处理类,
     * 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
     * 默认不实现,如果有需求可以重写此方法
     *
     * @return
     */
    @Override
    public GlobeHttpResultHandler getHttpResultHandler() {
        return new GlobeHttpResultHandler() {
            @Override
            public void onHttpResultResponse(String httpResult) {
                Timber.tag(TAG).w("GlobeHttpResultHandler------>" + httpResult);
            }
        };
    }
}
