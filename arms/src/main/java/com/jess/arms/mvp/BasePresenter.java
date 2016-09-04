package com.jess.arms.mvp;

import org.simple.eventbus.EventBus;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jess on 16/4/28.
 */
public class BasePresenter<M, V extends BaseView> implements presenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeSubscription mCompositeSubscription;

    protected M mModel;
    protected V mRootView;


    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }


    @Override
    public void onStart() {
        EventBus.getDefault().register(this);//注册eventbus
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);//解除注册eventbus
        unSubscribe();//解除订阅
        this.mModel = null;
        this.mRootView = null;
    }

    protected void handleError(Throwable throwable) {

    }


    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);//将所有subscription放入,集中处理
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
        }
    }

    @Override
    public void unSubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
        }
    }

}
