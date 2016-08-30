package me.jessyan.mvparms.demo.mvp.model.api.cache;

import com.jess.arms.http.BaseCacheManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jess on 8/30/16 13:54
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
public class CacheManager extends BaseCacheManager{
    private CommonCache mCommonCache;

    @Inject public CacheManager(CommonCache commonCache) {
        this.mCommonCache = commonCache;
    }

    public CommonCache getCommonCache() {
        return mCommonCache;
    }
}
