package com.jess.arms.integration;

import android.content.Context;

import com.jess.arms.common.data.DataKeeper;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * 用来管理网络请求层,以及数据缓存层,以后可以添加数据库请求层
 * 需要在{@link ConfigModule}的实现类中先inject需要的服务
 * Created by jess on 13/04/2017 09:52
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {



    private Retrofit mRetrofit;
    private RxCache mRxCache;
    private DataKeeper mDataKeeper;
    private Map<String, Object> mRetrofitServiceCache = new LinkedHashMap<>();
    private Map<String, Object> mCacheServiceCache = new LinkedHashMap<>();

    @Inject
    public RepositoryManager(Retrofit retrofit, RxCache rxCache,DataKeeper dataKeeper) {
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
        this.mDataKeeper = dataKeeper;
    }

    @Override
    public void release() {
        if (mRetrofitServiceCache != null) {
            mRetrofitServiceCache.clear();
            mRetrofitServiceCache = null;
        }
        mRetrofit = null;

        if (mCacheServiceCache != null) {
            mCacheServiceCache.clear();
            mCacheServiceCache = null;
        }
        mRxCache = null;
    }

    @Override
    public DataKeeper getDataKeeper() {
        return mDataKeeper;
    }

    /**
     * 注入RetrofitService,在{@link ConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
     * @param services
     */
    @Override
    public void injectRetrofitService(Class<?>... services) {
        for (Class<?> service : services) {
            if (mRetrofitServiceCache.containsKey(service.getName())) continue;
            mRetrofitServiceCache.put(service.getName(), mRetrofit.create(service));
        }

    }

    /**
     * 注入CacheService,在{@link ConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
     * @param services
     */
    @Override
    public void injectCacheService(Class<?>... services) {
        for (Class<?> service : services) {
            if (mCacheServiceCache.containsKey(service.getName())) continue;
            mCacheServiceCache.put(service.getName(), mRxCache.using(service));
        }
    }

    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        if (!mRetrofitServiceCache.containsKey(service.getName())) {
            injectRetrofitService(service);
        }
        return (T) mRetrofitServiceCache.get(service.getName());
    }

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainCacheService(Class<T> cache) {
        if (!mCacheServiceCache.containsKey(cache.getName())) {
            injectCacheService(cache);
        }
        return (T) mCacheServiceCache.get(cache.getName());
    }


}
