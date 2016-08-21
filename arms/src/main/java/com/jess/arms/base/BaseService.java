package com.jess.arms.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.simple.eventbus.EventBus;

import rx.Subscription;

/**
 * Created by jess on 16/5/6.
 */
public abstract class BaseService extends Service {
    protected final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void unSubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();//保证service结束时取消所有正在执行的订阅
        }
    }

    /**
     * 初始化
     */
    abstract public void init();
}
