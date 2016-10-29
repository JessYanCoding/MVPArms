package com.jess.arms.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.mvp.BasePresenter;
import com.trello.rxlifecycle.components.support.RxFragment;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by jess on 2015/12/8.
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment {
    protected BaseActivity mActivity;
    protected View mRootView;
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView();
        ButterKnife.bind(this, mRootView);//绑定到butterknife
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        EventBus.getDefault().register(this);//注册到事件主线
        ComponentInject();
        initData();
    }

    /**
     * 依赖注入的入口
     */
    protected abstract void ComponentInject();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        EventBus.getDefault().unregister(this);
    }


    protected abstract View initView();

    protected abstract void initData();


    public void setData(Object data) {

    }

    public void setData() {

    }

}
