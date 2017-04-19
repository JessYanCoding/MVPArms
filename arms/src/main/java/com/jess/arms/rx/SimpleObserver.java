package com.jess.arms.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者: lujianzhao
 * 创建时间: 2017/03/22 10:27
 * 描述:
 */

public abstract class SimpleObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }
}
