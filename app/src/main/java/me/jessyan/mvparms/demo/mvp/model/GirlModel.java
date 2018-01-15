/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.mvparms.demo.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import me.jessyan.mvparms.demo.di.scope.GirlScope;
import me.jessyan.mvparms.demo.mvp.contract.GirlContract;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CommonCache;
import me.jessyan.mvparms.demo.mvp.model.api.service.CommonService;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseJson;
import me.jessyan.mvparms.demo.mvp.model.entity.Girl;

/**
 * ================================================
 * 展示 Model 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.3">Model wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 10:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@GirlScope
public class GirlModel extends BaseModel implements GirlContract.Model {


    public static final int USERS_PER_PAGE = 10;

    @Inject
    public GirlModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseJson<List<Girl>>> getGirls(int pageNum, boolean update) {

        //实际过程拆分开
//        Observable<BaseJson<List<Girl>>> girlData = mRepositoryManager
//                .obtainRetrofitService(CommonService.class)
//                .getGirlData(pageNum, USERS_PER_PAGE);
//
//        Observable<Observable<BaseJson<List<Girl>>>> observableJust = Observable.just(girlData);
//
//        observableJust.flatMap(new Function<Observable<BaseJson<List<Girl>>>, ObservableSource<BaseJson<List<Girl>>>>() {
//            @Override
//            public ObservableSource<BaseJson<List<Girl>>> apply(Observable<BaseJson<List<Girl>>> baseJsonObservable) throws Exception {
//                return mRepositoryManager.obtainCacheService(CommonCache.class)
//                        .getGirls(baseJsonObservable
//                                , new DynamicKey(pageNum)
//                                , new EvictDynamicKey(update))
//                        .map( listReply -> listReply.getData());
//            }
//        });

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class) //这里给Dagger2 提供Retrofit对象
                .getGirlData(pageNum, USERS_PER_PAGE))
                .flatMap(new Function<Observable<BaseJson<List<Girl>>>, ObservableSource<BaseJson<List<Girl>>>>() {
                    @Override
                    public ObservableSource<BaseJson<List<Girl>>> apply(Observable<BaseJson<List<Girl>>> baseJsonObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CommonCache.class) //这里给Dagger2 提供RxCache对象
                                .getGirls(baseJsonObservable
                                        , new DynamicKey(pageNum)
                                        , new EvictDynamicKey(update))
                                .map( listReply -> listReply.getData());
                    }
                });
    }

}



