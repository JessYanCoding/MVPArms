package com.jess.arms.mvp;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.netstate.INetChangeObserver;
import com.jess.arms.netstate.NetWorkUtil;
import com.jess.arms.netstate.NetworkStateReceiver;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jess on 16/4/28.
 */
public class PresenterImp<M extends IModel, V extends IView> implements IPresenter,INetChangeObserver {

    private CompositeDisposable mCompositeDisposable;

    protected Application mApplication;

    protected AppManager mAppManager;

    protected M mModel;

    protected V mRootView;

    public PresenterImp( Application application,AppManager appManager) {
        this(null,null,application,appManager);
    }

    public PresenterImp(M model, V rootView, Application application,AppManager appManager) {
        this.mModel = model;
        this.mRootView = rootView;
        this.mApplication = application;
        this.mAppManager = appManager;
        onStart();
    }


    @Override
    public void onStart() {
        if (useEventBus()) {//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册eventbus
        }
        NetworkStateReceiver.registerObserver(this);
    }

    @Override
    public void onDestroy() {
        NetworkStateReceiver.removeRegisterObserver(this);
        if (useEventBus()) {//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);//解除注册eventbus
        }

        cleanCompositeDisposable();//解除订阅


        if (mModel != null) {
            mModel.onDestroy();
            this.mModel = null;
        }

        this.mRootView = null;

        this.mAppManager = null;

        this.mApplication = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }


    protected void addDisposable(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);//将所有subscription放入,集中处理
    }

    private void cleanCompositeDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
            mCompositeDisposable = null;
        }
    }


    @Override
    public void onNetworkConnect(NetWorkUtil.NetWorkType type) {

    }

    @Override
    public void onNetworkDisConnect() {

    }
}
