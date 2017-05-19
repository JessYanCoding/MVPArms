package me.jessyan.mvparms.demo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;
import me.jessyan.mvparms.demo.di.module.UserDetailModule;
import me.jessyan.mvparms.demo.mvp.ui.fragment.UserDetailFragment;

/**
 * Created by xiaobailong24 on 2017/5/19.
 * Dagger UserDetailComponent
 */

@FragmentScope
@Component(modules = UserDetailModule.class, dependencies = AppComponent.class)
public interface UserDetailComponent {
    void inject(UserDetailFragment fragment);
}