package com.jess.arms.di.module;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public Gson provideGson(Application application, GsonConfiguration configuration){
        GsonBuilder builder = new GsonBuilder();
        configuration.configGson(application, builder);
        return builder.create();
    }

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

    public interface GsonConfiguration {
        GsonConfiguration EMPTY = new GsonConfiguration() {
            @Override
            public void configGson(Context context, GsonBuilder builder) {

            }
        };

        void configGson(Context context, GsonBuilder builder);
    }

}
