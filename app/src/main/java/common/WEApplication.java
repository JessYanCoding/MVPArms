package common;

import android.content.Context;
import android.os.StrictMode;

import com.apkfuns.logutils.LogUtils;
import com.jess.arms.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import me.jessyan.mvparms.demo.BuildConfig;
import me.jessyan.mvparms.demo.mvp.ui.activity.UserActivity;

/**
 * Created by jess on 8/5/16 11:07
 * contact with jess.yan.effort@gmail.com
 */
public class WEApplication extends BaseApplication {

    private RefWatcher mRefWatcher;//leakCanary观察器

    @Override
    public void onCreate() {
        super.onCreate();

        installLeakCanary();//leakCanary内存泄露检查
        initReference();
    }

    private void initReference() {
        Recovery.getInstance()
                .debug(isDebug())
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(UserActivity.class)
                .recoverEnabled(true)
                .callback(new RecoveryCallback() {
                    @Override
                    public void stackTrace(String s) {

                    }

                    @Override
                    public void cause(String s) {

                    }

                    @Override
                    public void exception(String s, String s1, String s2, int i) {

                    }

                    @Override
                    public void throwable(Throwable throwable) {
                        LogUtils.e(throwable);
                    }
                })
                .silent(!isDebug(), Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                //忽略某些Activity的错误
//                .skip(TestActivity.class)
                .init(this);
    }

    @Override
    protected boolean isDebug() {
        return BuildConfig.LOG_DEBUG;
    }

    @Override
    protected String getTag() {
        return "AndroidBase";
    }

    @Override
    public void onTerminate() {
        if (mRefWatcher != null) {
            this.mRefWatcher = null;
        }
        super.onTerminate();
    }

    /**
     * 安装leakCanary检测内存泄露
     */
    protected void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        if (BuildConfig.USE_CANARY) {
            enabledStrictMode();
            this.mRefWatcher = LeakCanary.install(this);
        } else {
            this.mRefWatcher = RefWatcher.DISABLED;
        }
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
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
