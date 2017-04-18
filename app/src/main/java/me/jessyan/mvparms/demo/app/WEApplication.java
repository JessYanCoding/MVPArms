package me.jessyan.mvparms.demo.app;

import android.content.Context;

import com.jess.arms.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import me.jessyan.mvparms.demo.BuildConfig;
import timber.log.Timber;

/**
 * Created by jess on 8/5/16 11:07
 * Contact with jess.yan.effort@gmail.com
 */
public class WEApplication extends BaseApplication {
    private RefWatcher mRefWatcher;//leakCanary观察器

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.LOG_DEBUG) {//Timber日志打印
            Timber.plant(new Timber.DebugTree());
        }

        installLeakCanary();//leakCanary内存泄露检查
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        this.mRefWatcher = null;
    }

    /**
     * 安装leakCanary检测内存泄露
     */
    protected void installLeakCanary() {
        this.mRefWatcher = BuildConfig.USE_CANARY ? LeakCanary.install(this) : RefWatcher.DISABLED;
    }

    /**
     * 获得leakCanary观察器
     *
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        WEApplication application = (WEApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

}
