package com.jess.arms.common.service;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;


/**
 * note: VERSION_CODE >= API_18
 * <p/>
 * manifest:
 * <service android:name=".service.NotificationService"
 *  android:label="@string/app_name"
 *  android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE">
 *  <intent-filter>
 *      <action android:name="android.service.notification.NotificationListenerService" />
 *  </intent-filter>
 * </service>
 *
 * @author lujianzhao
 * @date 2015-03-09
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {
//    private static final String TAG = "NotificationService";
    public static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static NotificationService self;
    private static NotificationListener notificationListener;

    /*----------------- 静态方法 -----------------*/
    public synchronized static void startNotificationService(Context context, NotificationListener notificationListener) {
        NotificationService.notificationListener = notificationListener;
        context.startService(new Intent(context, NotificationService.class));
    }

    public synchronized static void stopNotificationService(Context context) {
        context.stopService(new Intent(context, NotificationService.class));
    }


    public static void startNotificationListenSettings(Context context) {
        Intent intent = new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS);
        if(!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static boolean isNotificationListenEnable(Context context) {
        return isNotificationListenEnable(context, context.getPackageName());
    }

    public static boolean isNotificationListenEnable(Context context, String pkgName) {
        final String flat = Settings.Secure.getString(context.getContentResolver(), ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*----------------- 生命周期 -----------------*/
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i( "onCreate..");

        if (notificationListener != null) {
            notificationListener.onServiceCreated(this);
        }
        self = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("onStartCommand..");
        return notificationListener == null ? START_STICKY : notificationListener.onServiceStartCommand(this, intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i( "onDestroy..");

        if (notificationListener != null) {
            notificationListener.onServiceDestroy();
            notificationListener = null;
        }
        self = null;
    }

    /*----------------- 通知回调 -----------------*/
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        if (LogUtils.getLogConfig().isEnable()) {
//            LogUtils.i( sbn.toString());
//            Notification notification = sbn.getNotification();
//            LogUtils.i( "tickerText : " + notification.tickerText);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                Bundle bundle = notification.extras;
//                for (String key : bundle.keySet()) {
//                    LogUtils.i(key + ": " + bundle.get(key));
//                }
//            }
//        }
        if (self != null && notificationListener != null) {
            notificationListener.onNotificationPosted(sbn);
        }
    }


    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (self != null && notificationListener != null) {
            notificationListener.onNotificationRemoved(sbn);
        }
    }

    public void printCurrentNotifications() {
        StatusBarNotification[] ns = getActiveNotifications();
        for (StatusBarNotification n : ns) {
            LogUtils.i(String.format("%20s",n.getPackageName()) + ": " + n.getNotification().tickerText);
        }
    }


    public static abstract class NotificationListener {
        public void onServiceCreated(NotificationService service) {}

        public abstract int onServiceStartCommand(NotificationService service, Intent intent, int flags, int startId);

        public void onServiceDestroy() {}

        /**
         * Implement this method to learn about new notifications as they are posted by apps.
         *
         * @param sbn A data structure encapsulating the original {@link Notification}
         *            object as well as its identifying information (tag and id) and source
         *            (package name).
         */
        public abstract void onNotificationPosted(StatusBarNotification sbn);

        /**
         * Implement this method to learn when notifications are removed.
         * <p/>
         * This might occur because the user has dismissed the notification using system UI (or another
         * notification listener) or because the app has withdrawn the notification.
         * <p/>
         * NOTE: The {@link StatusBarNotification} object you receive will be "light"; that is, the
         * result from {@link StatusBarNotification#getNotification} may be missing some heavyweight
         * fields such as {@link Notification#contentView} and
         * {@link Notification#largeIcon}. However, all other fields on
         * {@link StatusBarNotification}, sufficient to match this call with a prior call to
         * {@link #onNotificationPosted(StatusBarNotification)}, will be intact.
         *
         * @param sbn A data structure encapsulating at least the original information (tag and id)
         *            and source (package name) used to post the {@link Notification} that
         *            was just removed.
         */
        public abstract void onNotificationRemoved(StatusBarNotification sbn);
    }
}