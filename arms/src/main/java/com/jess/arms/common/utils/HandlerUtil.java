package com.jess.arms.common.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @author lujianzhao
 * @date 2015-03-12
 */
public class HandlerUtil {

    public static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(Runnable runnable){
        HANDLER.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis){
        HANDLER.postDelayed(runnable,delayMillis);
    }

    public static void removeRunable(Runnable runnable){
        HANDLER.removeCallbacks(runnable);
    }

    public static void removeCallbacksAndMessages() {
        HANDLER.removeCallbacksAndMessages(null);
    }

    public static void sendMessage(Handler handler, int what) {

        Message msg = handler.obtainMessage();

        msg.what = what;

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, int arg1) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.arg1 = arg1;

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, Object obj) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.obj = obj;

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, int arg1, Object obj) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.arg1 = arg1;
        msg.obj = obj;

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, int arg1, int arg2) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, int arg1, int arg2, Object obj) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, Bundle bundle) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.setData(bundle);

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, int arg1, Bundle bundle) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.arg1 = arg1;
        msg.setData(bundle);

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, Object obj, Bundle bundle) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.obj = obj;
        msg.setData(bundle);

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, int arg1, Object obj, Bundle bundle) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.arg1 = arg1;
        msg.obj = obj;
        msg.setData(bundle);

        // 发送消息
        handler.sendMessage(msg);
    }


    public static void sendMessage(Handler handler, int what, int arg1, int arg2, Bundle bundle) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.setData(bundle);

        // 发送消息
        handler.sendMessage(msg);
    }



    public static void sendMessage(Handler handler, int what, int arg1, int arg2, Object obj, Bundle bundle) {

        Message msg = handler.obtainMessage();

        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;
        msg.setData(bundle);

        // 发送消息
        handler.sendMessage(msg);
    }
}
