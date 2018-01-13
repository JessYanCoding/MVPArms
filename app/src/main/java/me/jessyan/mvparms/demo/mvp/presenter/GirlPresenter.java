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
package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.di.scope.GirlScope;
import me.jessyan.mvparms.demo.mvp.contract.GirlContract;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseJson;
import me.jessyan.mvparms.demo.mvp.model.entity.Girl;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * ================================================
 * 复用 Presenter 的用法（其中一种）
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/issues/181">复用的Presenter 注入简单示例</a>
 * Created by Sum41forever on 01/13/2018
 * <a href="http://www.sum41forever.com/">My Blog</a>
 * <a href="https://github.com/Sum41forever">My Github</a>
 * ================================================
 */
@GirlScope
public class GirlPresenter extends BasePresenter<GirlContract.Model, GirlContract.View> {
    private RxErrorHandler mErrorHandler;
    private List<Girl> mGirls;
    private RecyclerView.Adapter mAdapter;

    private int pageNum = 1;


    @Inject
    public GirlPresenter(GirlContract.Model model, GirlContract.View rootView, RxErrorHandler handler
            , List<Girl> list, @Named("girl") RecyclerView.Adapter adapter) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mGirls = list;
        this.mAdapter = adapter;
    }


    public void requestGirls(final boolean pullToRefresh) {

        //下拉刷新默认只请求第一页
        if (pullToRefresh) {
            pageNum = 1;
        }

        // 不使用缓存
        mModel.getGirls(pageNum,true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh) {
                        mRootView.showLoading();//显示下拉刷新的进度条
                    } else {
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh) {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    } else {
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<Girl>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<List<Girl>> girlList) {
                        // 下拉刷新就重制数据源
                        if (pullToRefresh) {
                            mGirls.clear();
                        }
                        int preEndIndex = mGirls.size();// 数据源插入位置
                        mGirls.addAll(girlList.getData());
                        pageNum++;

                        if (pullToRefresh) {
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mAdapter.notifyItemRangeInserted(preEndIndex, mGirls.size());
                        }
                    }
                });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mGirls = null;
        this.mErrorHandler = null;
    }
}
