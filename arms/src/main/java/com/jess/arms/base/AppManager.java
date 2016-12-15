package com.jess.arms.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

/**
 * 用于管理所有activity
 * 可以通过直接持有AppManager对象执行对应方法
 * 也可以通过eventbus post事件,远程遥控执行对应方法
 * Created by jess on 14/12/2016 13:50
 * Contact with jess.yan.effort@gmail.com
 */

public class AppManager {
    protected final String TAG = this.getClass().getSimpleName();
    public static final String APPMANAGER_MESSAGE = "appmanager_message";
    public static final int START_ACTIVITY = 0;
    public static final int SHOW_SNACKBAR = 1;
    public static final int KILL_ALL = 2;
    public static final int APP_EXIT = 3;
    private Application mApplication;

    //管理所有activity
    public List<BaseActivity> mActivityList;
    //当前在前台的activity
    private BaseActivity mCurrentActivity;

    public AppManager(Application application) {
        this.mApplication = application;
        EventBus.getDefault().register(this);
    }


    /**
     * 通过eventbus post事件,远程遥控执行对应方法
     */
    @Subscriber(tag = APPMANAGER_MESSAGE)
    public void onReceive(Message message){
        switch (message.what){
            case START_ACTIVITY:
                startActivity((Intent) message.obj);
                break;
            case SHOW_SNACKBAR:
                break;
            case KILL_ALL:
                killAll();
                break;
            case APP_EXIT:
                AppExit();
                break;
        }
    }

    /**
     * 让在前台的activity,打开下一个activity
     * @param intent
     */
    public void startActivity(Intent intent) {
        if (getCurrentActivity()!=null){
            getCurrentActivity().startActivity(intent);
        }
    }

    /**
     * 让在前台的activity,打开下一个activity
     * @param activityClass
     */
    public void startActivity(Class activityClass) {
        if (getCurrentActivity()!=null){
            getCurrentActivity().startActivity(new Intent(mApplication,activityClass));
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        EventBus.getDefault().unregister(this);
        mActivityList.clear();
        mActivityList = null;
        mCurrentActivity = null;
        mApplication = null;
    }

    /**
     * 将在前台的activity保存
     *
     * @param currentActivity
     */
    public void setCurrentActivity(BaseActivity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    /**
     * 获得当前在前台的activity
     * @return
     */
    public BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * 返回一个存储所有未销毁的activity的集合
     *
     * @return
     */
    public List<BaseActivity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }


    /**
     * 添加Activity到集合
     */
    public void addActivity(BaseActivity activity) {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        synchronized (AppManager.class) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        }
    }

    /**
     * 删除集合里的指定activity
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when removeActivity(BaseActivity)");
            return;
        }
        synchronized (AppManager.class) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 删除集合里的指定位置的activity
     *
     * @param location
     */
    public BaseActivity removeActivity(int location) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when removeActivity(int)");
            return null;
        }
        synchronized (AppManager.class) {
            if (location > 0 && location < mActivityList.size()) {
                return mActivityList.remove(location);
            }
        }
        return null;
    }

    /**
     * 关闭指定activity
     *
     * @param activityClass
     */
    public void killActivity(Class<?> activityClass) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when killActivity");
            return;
        }
        for (BaseActivity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                activity.finish();
            }
        }
    }


    /**
     * 指定的activity实例是否存活
     *
     * @param activity
     * @return
     */
    public boolean activityInstanceIsLive(BaseActivity activity) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when activityInstanceIsLive");
            return false;
        }
        return mActivityList.contains(activity);
    }


    /**
     * 指定的activity class是否存活(一个activity可能有多个实例)
     *
     * @param activityClass
     * @return
     */
    public boolean activityClassIsLive(Class<?> activityClass) {
        if (mActivityList == null) {
            Timber.tag(TAG).w("mActivityList == null when activityClassIsLive");
            return false;
        }
        for (BaseActivity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 关闭所有activity
     */
    public void killAll() {
        LinkedList<BaseActivity> copy;
        synchronized (AppManager.class) {
            copy = new LinkedList<>(getActivityList());
        }
        for (BaseActivity baseActivity : copy) {
            baseActivity.finish();
        }
    }


    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            killAll();
            ActivityManager activityMgr =
                    (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(mApplication.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
