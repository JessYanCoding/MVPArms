package com.jess.arms.di.component;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.module.BaseModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jess on 14/12/2016 13:58
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
@Component(modules={BaseModule.class})
public interface BaseComponent {
    void inject(BaseApplication application);
}
