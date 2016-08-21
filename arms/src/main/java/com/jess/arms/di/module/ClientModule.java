package com.jess.arms.di.module;

import android.app.Application;

import com.jess.arms.http.RequestIntercept;
import com.jess.arms.utils.DataHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.BaseUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jessyan on 2016/3/14.
 */
@Module
public class ClientModule {
    private static final int TOME_OUT = 10;
    public static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;//缓存文件最大值为10Mb
    private HttpUrl mApiUrl = HttpUrl.parse("https://api.github.com/");

    /**
     * @param baseUrl
     * @author: jess
     * @date 8/5/16 11:03 AM
     * @description: 设置baseurl
     */
    public ClientModule(String baseUrl) {
        this.mApiUrl = HttpUrl.parse(baseUrl);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(Cache cache, Interceptor intercept) {
        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        return configureClient(okHttpClient, cache, intercept);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, HttpUrl httpUrl) {
        final Retrofit.Builder builder = new Retrofit.Builder();
        return configureRetrofit(builder, client, httpUrl);
    }

    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return mApiUrl;
    }

    @Singleton
    @Provides
    Cache provideCache(Application application) {
        return new Cache(DataHelper.getCacheFile(application), HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);//设置缓存路径和大小
    }


    @Singleton
    @Provides
    Interceptor provideIntercept() {
        return new RequestIntercept();//打印请求信息的拦截器
    }

    private Retrofit configureRetrofit(Retrofit.Builder builder, OkHttpClient client, final HttpUrl httpUrl) {
        return builder
                .baseUrl(new BaseUrl() {
                    @Override
                    public HttpUrl url() {
                        return httpUrl;
                    }
                })//域名
                .client(client)//设置okhttp
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//使用rxjava
                .addConverterFactory(GsonConverterFactory.create())//使用Gson
                .build();
    }

    /**
     * 配置okhttpclient
     *
     * @param okHttpClient
     * @param Cache
     * @param Interceptor
     * @return
     */
    private OkHttpClient configureClient(OkHttpClient.Builder okHttpClient, Cache cache, Interceptor intercept) {
        return okHttpClient
                .connectTimeout(TOME_OUT, TimeUnit.SECONDS)
                .readTimeout(TOME_OUT, TimeUnit.SECONDS)
                .cache(cache)//设置缓存
                .addNetworkInterceptor(intercept)
                .build();
    }
//    .addNetworkInterceptor(new Interceptor() {
//        @Override
//        public Response intercept(Interceptor.Chain chain) throws IOException {
//            Request request = chain.request();
//            if(!DeviceUtils.netIsConnected(UiUtils.getContext())){
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build();
//                LogUtils.warnInfo("http","no network");
//            }
//            Response originalResponse = chain.proceed(request);
//            if(DeviceUtils.netIsConnected(UiUtils.getContext())){
//                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//                String cacheControl = request.cacheControl().toString();
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma")
//                        .build();
//            }else{
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
//                        .removeHeader("Pragma")
//                        .build();
//            }
//        }
//    })

}
