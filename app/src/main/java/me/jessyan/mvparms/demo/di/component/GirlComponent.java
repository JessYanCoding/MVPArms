package me.jessyan.mvparms.demo.di.component;

import dagger.Subcomponent;
import me.jessyan.mvparms.demo.di.module.GirlModule;
import me.jessyan.mvparms.demo.di.scope.GirlScope;
import me.jessyan.mvparms.demo.mvp.ui.activity.MultiPersenterActivity;

/**
 * ================================================
 * 展示 Component 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.6">Component wiki 官方文档</a>
 * Created by Sum41forever on 2018/1/13
 * <a href="http://www.sum41forever.com/">My Blog</a>
 * <a href="https://github.com/sum41forever">My github</a>
 * ================================================
 */
@GirlScope
@Subcomponent(modules = {GirlModule.class})
public interface GirlComponent {

    void inject(MultiPersenterActivity activity);
}
