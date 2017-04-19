package com.jess.arms.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.apkfuns.logutils.LogUtils;


public class ScreenReceiver extends BroadcastReceiver {
//    private String TAG = "ScreenActionReceiver";
    private ScreenListener screenListener;

    public ScreenReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            LogUtils.d( "屏幕解锁广播...");
            if (screenListener != null) {
                screenListener.screenOn();
            }
        } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            LogUtils.d( "屏幕加锁广播...");
            if (screenListener != null) {
                screenListener.screenOff();
            }
        }
    }

    public void registerScreenReceiver(Context context, ScreenListener screenListener) {
        try {
            this.screenListener = screenListener;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            LogUtils.d("注册屏幕解锁、加锁广播接收者...");
            context.registerReceiver(this, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterScreenReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
            LogUtils.d("注销屏幕解锁、加锁广播接收者...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ScreenListener {
        void screenOn();

        void screenOff();
    }

}