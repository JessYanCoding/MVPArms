package com.jess.arms.base.delegate;


import android.os.Bundle;

import com.jess.arms.di.component.AppComponent;

/**
 * Created by jess on 26/04/2017 21:42
 * Contact with jess.yan.effort@gmail.com
 * Modified by https://github.com/xiaobailong24
 */

public interface IActivity {

    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     *
     * @param appComponent
     */
    void setupActivityComponent(AppComponent appComponent);

    boolean useEventBus();

    /**
     * 如果initView返回0,框架则不会调用{@link android.app.Activity#setContentView(int)}
     *
     * @return
     * @param savedInstanceState
     */
    int initView(Bundle savedInstanceState);

    void initData(Bundle savedInstanceState);

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link com.jess.arms.base.BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    boolean useFragment();

    /**
     * 如果Activity是使用Fragment，并且要想处理Activity重建时重用已经创建好的Fragment，在Activity则重写此方法。
     * BaseActivity提供一个空实现，因为使用Fragment不是必须。
     * 因为 {@link #initData(Bundle)} 方法是在 super.onCreate() 中调用的，
     * 但是FragmentManager的恢复是在 onCreate() 方法返回后执行的，所以在 initData(Bundle) 中恢复Fragment会返回null。
     *
     * @param savedInstanceState
     */
    void initFragments(Bundle savedInstanceState);
}
