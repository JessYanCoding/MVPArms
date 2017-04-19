package com.jess.arms.integration;

import android.content.Context;

import com.jess.arms.di.module.GlobeConfigModule;

/**
 * 此接口可以给框架配置一些参数,需要实现类实现后,并在AndroidManifest中声明该实现类
 * Created by jess on 12/04/2017 11:37
 * Contact with jess.yan.effort@gmail.com
 */

public interface ConfigModule {
    /**
     * 使用{@link GlobeConfigModule.Buidler}给框架配置一些配置参数
     * @param context
     * @param builder
     */
    void applyOptions(Context context, GlobeConfigModule.Buidler builder);

    /**
     * 使用{@link IRepositoryManager}给框架注入一些网络请求和数据缓存等服务
     * @param context
     * @param repositoryManager
     */
    void registerComponents(Context context, IRepositoryManager repositoryManager);
}
