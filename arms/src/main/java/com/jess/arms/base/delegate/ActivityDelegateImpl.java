package com.jess.arms.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;

import com.jess.arms.base.App;

import org.simple.eventbus.EventBus;

/**
 * Created by jess on 26/04/2017 20:23
 * Contact with jess.yan.effort@gmail.com
 */

public class ActivityDelegateImpl implements ActivityDelegate {
    private Activity mActivity;
    private IActivity iActivity;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (iActivity.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(mActivity);//注册到事件主线
        iActivity.setupActivityComponent(((App) mActivity.getApplication()).getAppComponent());//依赖注入
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {
        if (iActivity != null && iActivity.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(mActivity);
        this.iActivity = null;
        this.mActivity = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
    }

    public static final Creator<ActivityDelegateImpl> CREATOR = new Creator<ActivityDelegateImpl>() {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };
}
