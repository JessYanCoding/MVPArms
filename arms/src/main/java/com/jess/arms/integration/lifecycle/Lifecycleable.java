package com.jess.arms.integration.lifecycle;

import android.support.annotation.NonNull;

import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.subjects.Subject;

/**
 * ================================================
 * 让 Activity/Fragment 实现此接口,即可正常使用 {@link com.trello.rxlifecycle2.RxLifecycle}
 * 无需再继承 {@link RxLifecycle} 提供的 Activity/Fragment,扩展性极强
 *
 * @see RxLifecycleUtils 详细用法请查看此类
 * Created by JessYan on 25/08/2017 18:39
 * Contact with jess.yan.effort@gmail.com
 * Follow me on https://github.com/JessYanCoding
 * ================================================
 */
public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
