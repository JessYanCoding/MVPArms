package com.jess.arms.utils;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.BaseView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jess on 11/10/2016 16:39
 * Contact with jess.yan.effort@gmail.com
 */

public class RxUtils {

    public static <T> ObservableTransformer<T, T> applySchedulers(final BaseView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                //隐藏进度条
                return observable.subscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable -> {
                            //显示进度条
                            view.showLoading();
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterTerminate(view::hideLoading)
                        .compose(RxUtils.bindToLifecycle(view));
            }
        };
    }


    public static <T> LifecycleTransformer<T> bindToLifecycle(BaseView view) {
        if (view instanceof BaseActivity) {
            return ((BaseActivity) view).bindToLifecycle();
        } else if (view instanceof BaseFragment) {
            return ((BaseFragment) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }

    }

}
