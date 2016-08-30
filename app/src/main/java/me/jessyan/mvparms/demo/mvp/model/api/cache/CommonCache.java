package me.jessyan.mvparms.demo.mvp.model.api.cache;

import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import me.jessyan.mvparms.demo.mvp.model.entity.FindDetailEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.FindMoreEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.HomePicEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.HotStrategyEntity;
import rx.Observable;

/**
 * Created by jess on 8/30/16 13:53
 * Contact with jess.yan.effort@gmail.com
 */
public interface CommonCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<HomePicEntity>> getDailyList(Observable<HomePicEntity> service, DynamicKey publishTime, EvictProvider provider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<FindMoreEntity>> getFindMore(Observable<FindMoreEntity> service, DynamicKey id, EvictProvider provider);


    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<HotStrategyEntity>> getHotStrategy(Observable<HotStrategyEntity> service, DynamicKey id, EvictProvider provider);


    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<FindDetailEntity>> getFindDetail(Observable<FindDetailEntity> service, DynamicKey id, EvictProvider provider);
}
