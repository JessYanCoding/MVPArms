package me.jessyan.mvparms.demo.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import me.jessyan.mvparms.demo.mvp.contract.UserDetailContract;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CommonCache;
import me.jessyan.mvparms.demo.mvp.model.api.service.UserService;
import me.jessyan.mvparms.demo.mvp.model.entity.UserDetail;

/**
 * Created by xiaobailong24 on 2017/5/19.
 * MVP UserDetailModel
 */

@FragmentScope
public class UserDetailModel extends BaseModel implements UserDetailContract.Model {

    @Inject
    public UserDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<UserDetail> getUserDetail(String username, boolean update) {
        Observable<UserDetail> userDetail = mRepositoryManager.obtainRetrofitService(UserService.class)
                .getUserDetail(username);
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mRepositoryManager.obtainCacheService(CommonCache.class)
                .getUserDetail(userDetail
                        , new DynamicKey(username)
                        , new EvictDynamicKey(update))
                .flatMap(new Function<Reply<UserDetail>, ObservableSource<UserDetail>>() {
                    @Override
                    public ObservableSource<UserDetail> apply(@NonNull Reply<UserDetail> listReply) throws Exception {
                        return Observable.just(listReply.getData());
                    }
                });
    }
}
