package com.jess.arms.di.module;

import android.app.Application;

import com.jess.arms.base.AppManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jess on 14/12/2016 13:54
 * Contact with jess.yan.effort@gmail.com
 */
@Module
public class BaseModule {
    private Application mApplication;

    public BaseModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public AppManager provideAppManager(){ return new AppManager(mApplication);}
}
