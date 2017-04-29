package com.jess.arms.di.module;

import android.app.Application;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.integration.RepositoryManager;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jess on 8/4/16.
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    public Gson provideGson(){return new Gson();}

    @Singleton
    @Provides
    public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
        return repositoryManager;
    }

    @Singleton
    @Provides
    public Map<String, Object> provideExtras(){
        return new ArrayMap<>();
    }
}
