package com.jess.arms.di.module;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.common.data.DataKeeper;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.integration.RepositoryManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jess on 8/4/16.
 */
@Module
public class AppModule {
    private static final String SP_FILE_NAME ="config";

    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    /**
     * 释放资源
     */
    public void release() {
        mApplication = null;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    public DataKeeper provideDataKeeper(Application application) {
        return new DataKeeper(application,SP_FILE_NAME);
    }

    @Singleton
    @Provides
    public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
        return repositoryManager;
    }


}
