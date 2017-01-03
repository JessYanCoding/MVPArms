package com.jess.arms.mvp;

import rx.Subscription;

/**
 * Created by jess on 16/4/28.
 */
public interface Presenter {
    void onStart();
    void onDestroy();
    void unSubscribe(Subscription subscription);
}
