/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.integration;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.mvp.IModel;
import com.jess.arms.utils.Preconditions;
import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.internal.RxCache;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 {@link IModel} 层必要的 Api 做数据处理
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.3">RepositoryManager wiki 官方文档</a>
 * Created by JessYan on 13/04/2017 09:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {

    @Inject
    Lazy<Retrofit> mRetrofit;
    @Inject
    Lazy<RxCache> mRxCache;
    @Inject
    Application mApplication;
    @Inject
    Cache.Factory mCachefactory;
    private Cache<String, Object> mRetrofitServiceCache;
    private Cache<String, Object> mCacheServiceCache;

    @Inject
    public RepositoryManager() {
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    @NonNull
    @Override
    public synchronized <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
        return createWrapperService(serviceClass, true);
    }

    @NonNull
    @Override
    public synchronized <T> T obtainRetrofitServiceSynchronized(@NonNull Class<T> serviceClass) {
        return createWrapperService(serviceClass, false);
    }

    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T createWrapperService(Class<T> serviceClass, boolean isAsyn) {
        Preconditions.checkNotNull(serviceClass, "serviceClass == null");
        // 如果是同步请求，直接返回.
        if (!isAsyn) {
            return getRetrofitService(serviceClass);
        }
        // 二次代理
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        // 此处在调用 serviceClass 中的方法时触发。

                        if (method.getReturnType() == Observable.class) {
                            // 如果方法返回值是 Observable 的话，则包一层再返回，
                            // 该被观察者在被订阅时触发执行。
                            return Observable.defer(() -> {
                                // ((Observable) getRetrofitMethod(service, method).invoke(service, args))
                                // 就是 Retrofit.create(serviceClass).getXXX() 的执行，也就是耗时操作所在。
                                // 2.此时为 single 线程，执行被观察者链的构造（也是耗时操作所在）。
                                // 3.然后接着被订阅后，切换到 io 线程再执行网络请求，并向外部的观察者传递结果。
                                final T service = getRetrofitService(serviceClass);
                                return ((Observable) getRetrofitMethod(service, method)
                                        .invoke(service, args))
                                        .subscribeOn(Schedulers.io());
                            }).subscribeOn(Schedulers.single()); // 1.被订阅后先切换到 single 线程
                        } else if (method.getReturnType() == Single.class) {
                            // 如果方法返回值是 Single 的话，则包一层再返回。
                            return Single.defer((Callable<SingleSource<?>>) () -> {
                                final T service = getRetrofitService(serviceClass);
                                // 执行真正的 Retrofit 动态代理的方法
                                return ((Single) getRetrofitMethod(service, method)
                                        .invoke(service, args))
                                        .subscribeOn(Schedulers.io());
                            }).subscribeOn(Schedulers.single());
                        }

                        // 返回值不是 Observable 或 Single 的话不处理。
                        final T service = getRetrofitService(serviceClass);
                        return getRetrofitMethod(service, method).invoke(service, args);
                    }
                });
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T getRetrofitService(Class<T> serviceClass) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCachefactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mRetrofitServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = mRetrofit.get().create(serviceClass);
            mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cacheClass Cache class
     * @param <T> Cache class
     * @return Cache
     */
    @NonNull
    @Override
    public synchronized <T> T obtainCacheService(@NonNull Class<T> cacheClass) {
        Preconditions.checkNotNull(cacheClass, "cacheClass == null");
        if (mCacheServiceCache == null) {
            mCacheServiceCache = mCachefactory.build(CacheType.CACHE_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mCacheServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService = (T) mCacheServiceCache.get(cacheClass.getCanonicalName());
        if (cacheService == null) {
            cacheService = mRxCache.get().using(cacheClass);
            mCacheServiceCache.put(cacheClass.getCanonicalName(), cacheService);
        }
        return cacheService;
    }

    /**
     * 清理所有缓存
     */
    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll().subscribe();
    }

    @NonNull
    @Override
    public Context getContext() {
        return mApplication;
    }
}
