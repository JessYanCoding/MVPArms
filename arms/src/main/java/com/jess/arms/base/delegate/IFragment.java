package com.jess.arms.base.delegate;

import com.jess.arms.di.component.AppComponent;

/**
 * Created by jess on 29/04/2017 14:31
 * Contact with jess.yan.effort@gmail.com
 */

public interface IFragment {
    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     * @param appComponent
     */
    void setupFragmentComponent(AppComponent appComponent);

    boolean useEventBus();

    int initView();

    void initData();

    void setData(Object data);
}
