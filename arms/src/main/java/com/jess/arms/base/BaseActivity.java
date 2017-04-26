package com.jess.arms.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected App mApp;
    private Unbinder mUnbinder;
    @Inject
    protected P mPresenter;

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_add_activity_list";//是否加入到activity的list，管理


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }


    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (App) getApplication();
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册到事件主线
        setContentView(initView());
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        setupActivityComponent(mApp.getAppComponent());//依赖注入
        initData();
    }

    /**
     * 提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
     * @param appComponent
     */
    protected abstract void setupActivityComponent(AppComponent appComponent);


    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);
        this.mPresenter = null;
        this.mUnbinder = null;
        this.mApp = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    protected abstract View initView();

    protected abstract void initData();

}
