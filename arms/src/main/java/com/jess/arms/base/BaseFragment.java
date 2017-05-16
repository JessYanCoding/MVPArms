package com.jess.arms.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.mvp.IPresenter;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

/**
 * 因为java只能单继承,所以如果有需要继承特定Fragment的三方库,那你就需要自己自定义Fragment
 * 继承于这个特定的Fragment,然后按照将BaseFragment的格式,复制过去,记住一定要实现{@link IFragment}
 * Modified by https://github.com/xiaobailong24
 */
public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IFragment {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;


    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 在配置变化的时候将这个Fragment保存下来，在Activity由于配置变化重建是重复利用已经创建的Fragment。
        // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
        setRetainInstance(true);
        return initView(inflater, container);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

}
