package com.jess.arms.di.module;

import android.app.Application;

import com.jess.arms.common.assist.Check;
import com.jess.arms.common.utils.FileUtil;
import com.jess.arms.http.IGlobeHttpHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by jessyan on 2016/3/14.
 */
@Module
public class GlobeConfigModule {
    private HttpUrl mApiUrl;
    private IGlobeHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private File mCacheFile;

    /**
     * @author: jess
     * @date 8/5/16 11:03 AM
     * @description: 设置baseurl
     */
    private GlobeConfigModule(Buidler buidler) {
        this.mApiUrl = buidler.apiUrl;
        this.mHandler = buidler.handler;
        this.mInterceptors = buidler.interceptors;
        this.mCacheFile = buidler.cacheFile;
    }

    /**
     * 释放资源
     */
    public void release() {
        mApiUrl = null;
        mHandler = null;
        if (mInterceptors != null) {
            mInterceptors.clear();
            mInterceptors = null;
        }
        mCacheFile = null;
    }

    public static Buidler buidler() {
        return new Buidler();
    }


    @Singleton
    @Provides
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }


    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return mApiUrl;
    }


    @Singleton
    @Provides
    IGlobeHttpHandler provideGlobeHttpHandler() {
        return mHandler == null ? IGlobeHttpHandler.EMPTY : mHandler;//打印请求信息
    }


    /**
     * 提供缓存地址
     */
    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        if (mCacheFile == null) {
            mCacheFile = new File(FileUtil.getCacheDir());
        }
        return mCacheFile;
    }

    public static final class Buidler {
        private HttpUrl apiUrl = HttpUrl.parse("https://api.github.com/");
        private IGlobeHttpHandler handler;
        private List<Interceptor> interceptors = new ArrayList<>();
        private File cacheFile;

        private Buidler() {
        }

        public Buidler baseurl(String baseurl) {//基础url
            if (Check.isEmpty(baseurl)) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseurl);
            return this;
        }

        public Buidler globeHttpHandler(IGlobeHttpHandler handler) {//用来处理http响应结果
            this.handler = handler;
            return this;
        }

        public Buidler addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            this.interceptors.add(interceptor);
            return this;
        }

        public Buidler cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }


        public GlobeConfigModule build() {
            if (apiUrl == null) {
                throw new NullPointerException("baseurl is required");
            }
            return new GlobeConfigModule(this);
        }


    }


}
