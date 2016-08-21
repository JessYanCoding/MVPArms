package me.jessyan.mvparms.demo.app;

import com.jess.arms.base.BaseApplication;

import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.di.component.DaggerAppComponent;
import me.jessyan.mvparms.demo.di.module.ServiceModule;
import me.jessyan.mvparms.demo.mvp.model.api.Api;

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
                .build();
    }

    @Override
    public String getBaseUrl() {
        return Api.APP_DOMAIN;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
