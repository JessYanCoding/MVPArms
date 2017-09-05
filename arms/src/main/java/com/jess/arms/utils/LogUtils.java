package com.jess.arms.utils;

import android.text.TextUtils;
import android.util.Log;


public class LogUtils {

    private LogUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    private final static boolean isLog = true;
    public static final String DEFAULT_TAG = "MVPArms";

    public static void debugInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) return;
        Log.d(tag, msg);

    }

    public static void debugInfo(String msg) {
        debugInfo(DEFAULT_TAG, msg);
    }

    public static void warnInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) return;
        Log.w(tag, msg);

    }

    public static void warnInfo(String msg) {
        warnInfo(DEFAULT_TAG, msg);
    }

    /**
     * 所以这里使用自己分节的方式来输出足够长度的message
     *
     * @param tag
     * @param str void
     */
    public static void debugLongInfo(String tag, String str) {
        if (!isLog) return;
        str = str.trim();
        int index = 0;
        int maxLength = 3500;
        String sub;
        while (index < str.length()) {
            // java的字符不允许指定超过总的长度end  
            if (str.length() <= index + maxLength) {
                sub = str.substring(index);
            } else {
                sub = str.substring(index, index + maxLength);
            }

            index += maxLength;
            Log.d(tag, sub.trim());
        }
    }

    public static void debugLongInfo(String str) {
        debugLongInfo(DEFAULT_TAG, str);
    }

}
