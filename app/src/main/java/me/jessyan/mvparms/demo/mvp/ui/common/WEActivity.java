package me.jessyan.mvparms.demo.mvp.ui.common;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.BasePresenter;

import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.di.component.AppComponent;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class WEActivity<P extends BasePresenter> extends BaseActivity<P> {
    protected WEApplication mWeApplication;
    @Override
    protected void ComponentInject() {
        mWeApplication = (WEApplication) getApplication();
        setupActivityComponent(mWeApplication.getAppComponent());
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);
}
