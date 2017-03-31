package com.jess.arms.di.module;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.http.GlobeHttpHandler;
import com.jess.arms.utils.DataHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.rx2errorhandler.handler.listener.ResponseErrorListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by jessyan on 2016/3/14.
 */
@Module
public class GlobeConfigModule {
    private HttpUrl mApiUrl;
    private GlobeHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private ResponseErrorListener mErrorListener;
    private File mCacheFile;

    /**
     * @author: jess
     * @date 8/5/16 11:03 AM
     * @description: 设置baseurl
     */
    private GlobeConfigModule(Builder builder) {
        this.mApiUrl = builder.apiUrl;
        this.mHandler = builder.handler;
        this.mInterceptors = builder.interceptors;
        this.mErrorListener = builder.responseErroListener;
        this.mCacheFile = builder.cacheFile;
    }

    public static Builder builder() {
        return new Builder();
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
    GlobeHttpHandler provideGlobeHttpHandler() {
        return mHandler == null ? GlobeHttpHandler.EMPTY : mHandler;//打印请求信息
    }


    /**
     * 提供缓存地址
     */

    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        return mCacheFile == null ? DataHelper.getCacheFile(application) : mCacheFile;
    }


    /**
     * 提供处理Rxjava错误的管理器的回调
     *
     * @return
     */
    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }


    public static final class Builder {
        private HttpUrl apiUrl = HttpUrl.parse("https://api.github.com/");
        private GlobeHttpHandler handler;
        private List<Interceptor> interceptors = new ArrayList<>();
        private ResponseErrorListener responseErroListener;
        private File cacheFile;

        private Builder() {
        }

        public Builder baseurl(String baseurl) {//基础url
            if (TextUtils.isEmpty(baseurl)) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseurl);
            return this;
        }

        public Builder globeHttpHandler(GlobeHttpHandler handler) {//用来处理http响应结果
            this.handler = handler;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            this.interceptors.add(interceptor);
            return this;
        }


        public Builder responseErroListener(ResponseErrorListener listener) {//处理所有Rxjava的onError逻辑
            this.responseErroListener = listener;
            return this;
        }


        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }


        public GlobeConfigModule build() {
            checkNotNull(apiUrl, "baseurl is required");
            return new GlobeConfigModule(this);
        }


    }


}
