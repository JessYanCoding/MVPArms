package com.jess.arms.base.delegate;


import com.jess.arms.di.component.AppComponent;

/**
 * Created by jess on 26/04/2017 21:42
 * Contact with jess.yan.effort@gmail.com
 */

public interface IActivity {

    /**
     * 提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
     * @param appComponent
     */
    void setupActivityComponent(AppComponent appComponent);

    boolean useEventBus();

    int initView();

    void initData();

}
