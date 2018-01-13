/**
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
package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.DaggerUserComponent;
import me.jessyan.mvparms.demo.di.component.UserComponent;
import me.jessyan.mvparms.demo.di.module.GirlModule;
import me.jessyan.mvparms.demo.di.module.UserModule;
import me.jessyan.mvparms.demo.mvp.contract.GirlContract;
import me.jessyan.mvparms.demo.mvp.contract.UserContract;
import me.jessyan.mvparms.demo.mvp.presenter.GirlPresenter;
import me.jessyan.mvparms.demo.mvp.presenter.UserPresenter;
import timber.log.Timber;


/**
 * ================================================
 * 展示 View 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.2">View wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class UserActivity extends BaseActivity<UserPresenter> implements UserContract.View, GirlContract.View,  SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    RxPermissions mRxPermissions;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mUserAdapter;
    //注意因为2个Adapter的类型是一样的，必须加上@Named来让Dagger知道注入哪一个
    @Inject
    @Named("girl")
    RecyclerView.Adapter mGirlAdapter;
    @Inject
    GirlPresenter mGirlPresenter; //复用注入的P

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean isGirl = false;//是否显示的是妹子
    private UserComponent userComponent;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        userComponent = DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build();

        //通用的P的注入
        userComponent.plus(new GirlModule(this)).
                inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_user;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        initRecyclerView();
        //默认显示github用户页面
        setAdapter(isGirl);
        initPaginate();
    }

    @Override
    public void onRefresh() {
        refreshList(isGirl,true);
    }

    /**
     * 刷新列表
     *
     * @param isGirl 是否是妹子
     * @param pullToRefresh 是否是下拉刷新
     */
    private void refreshList(boolean isGirl, boolean pullToRefresh) {
        if (isGirl) {
            mGirlPresenter.requestGirls(pullToRefresh);
        } else {
            mPresenter.requestUsers(pullToRefresh);
        }
    }
    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    refreshList(isGirl, false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_girl:
                // 切换状态
                isGirl = !isGirl;
                setAdapter(isGirl);
                // 刷新列表
                onRefresh();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 切换适配器
     *
     * @param isGirlll
     */
    private void setAdapter(boolean isGirlll) {
        if (isGirlll) {
            mRecyclerView.setAdapter(mGirlAdapter);
        } else {
            mRecyclerView.setAdapter(mUserAdapter);
        }

    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }
}
