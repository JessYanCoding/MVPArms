/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * ================================================
 * 日志工具类
 * <p>
 * Created by JessYan on 2015/11/23.
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class LogUtils {
    private static final String DEFAULT_TAG = "MVPArms";
    private static boolean isLog = true;

    private LogUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isLog() {
        return isLog;
    }

    public static void setLog(boolean isLog) {
        LogUtils.isLog = isLog;
    }

    public static void debugInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.d(tag, msg);

    }

    public static void debugInfo(String msg) {
        debugInfo(DEFAULT_TAG, msg);
    }

    public static void warnInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.w(tag, msg);

    }

    public static void warnInfo(String msg) {
        warnInfo(DEFAULT_TAG, msg);
    }

    /**
     * 这里使用自己分节的方式来输出足够长度的 message
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    public static void debugLongInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        msg = msg.trim();
        int index = 0;
        int maxLength = 3500;
        String sub;
        while (index < msg.length()) {
            if (msg.length() <= index + maxLength) {
                sub = msg.substring(index);
            } else {
                sub = msg.substring(index, index + maxLength);
            }

            index += maxLength;
            Log.d(tag, sub.trim());
        }
    }

    public static void debugLongInfo(String msg) {
        debugLongInfo(DEFAULT_TAG, msg);
    }
}
