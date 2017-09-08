/*
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.jess.arms.base.delegate;


import android.os.Bundle;

import com.jess.arms.di.component.AppComponent;

/**
 * Created by jess on 26/04/2017 21:42
 * Contact with jess.yan.effort@gmail.com
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
}
