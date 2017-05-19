package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;
import me.jessyan.mvparms.demo.mvp.contract.UserDetailContract;
import me.jessyan.mvparms.demo.mvp.model.UserDetailModel;

/**
 * Created by xiaobailong24 on 2017/5/19.
 * Dagger UserDetailModule
 */

@Module
public class UserDetailModule {
    private UserDetailContract.View view;

    /**
     * 构建UserDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserDetailModule(UserDetailContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    UserDetailContract.View provideUserDetailView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    UserDetailContract.Model provideUserDetailModel(UserDetailModel model) {
        return model;
    }
}