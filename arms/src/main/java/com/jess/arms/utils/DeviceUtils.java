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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.List;

/**
 * ================================================
 * 获取设备常用信息和处理设备常用操作的工具类
 * <p>
 * Created by JessYan on 2016/3/15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DeviceUtils {
    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;
    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;
    public static float displayDensity = 0.0F;
    private static Boolean _hasBigScreen = null;
    private static Boolean _hasCamera = null;
    private static Boolean _isTablet = null;
    private static Integer _loadFactor = null;

    static {
        GTE_ICS = Build.VERSION.SDK_INT >= 14;
        GTE_HC = Build.VERSION.SDK_INT >= 11;
        PRE_HC = Build.VERSION.SDK_INT < 11;
    }

    private DeviceUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public static float dpToPixel(Context context, float dp) {
        return dp * (getDisplayMetrics(context).densityDpi / 160F);
    }

    /**
     * px转dp
     *
     * @param context
     * @param f
     * @return
     */
    public static float pixelsToDp(Context context, float f) {
        return f / (getDisplayMetrics(context).densityDpi / 160F);
    }

    public static int getDefaultLoadFactor(Context context) {
        if (_loadFactor == null) {
            Integer integer = 0xf & context
                    .getResources().getConfiguration().screenLayout;
            _loadFactor = integer;
            _loadFactor = Math.max(integer, 1);
        }
        return _loadFactor;
    }

    public static float getDensity(Context context) {
        if (displayDensity == 0.0) {
            displayDensity = getDisplayMetrics(context).density;
        }
        return displayDensity;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
                displaymetrics);
        return displaymetrics;
    }

    /**
     * 屏幕高度
     *
     * @param context
     * @return
     */
    public static float getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * 屏幕宽度
     *
     * @param context
     * @return
     */
    public static float getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取activity尺寸
     *
     * @param activity
     * @return
     */
    public static int[] getRealScreenSize(Activity activity) {
        int[] size = new int[2];
        int screenWidth, screenHeight;
        WindowManager w = activity.getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                screenWidth = (Integer) Display.class.getMethod("getRawWidth")
                        .invoke(d);
                screenHeight = (Integer) Display.class
                        .getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d,
                        realSize);
                screenWidth = realSize.x;
                screenHeight = realSize.y;
            } catch (Exception ignored) {
            }
        }
        size[0] = screenWidth;
        size[1] = screenHeight;
        return size;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean hasBigScreen(Context context) {
        boolean flag = true;
        if (_hasBigScreen == null) {
            boolean flag1;
            if ((0xf & context.getResources()
                    .getConfiguration().screenLayout) >= 3) {
                flag1 = flag;
            } else {
                flag1 = false;
            }
            Boolean boolean1 = flag1;
            _hasBigScreen = boolean1;
            if (!boolean1) {
                if (getDensity(context) <= 1.5F) {
                    flag = false;
                }
                _hasBigScreen = flag;
            }
        }
        return _hasBigScreen;
    }

    /**
     * 设备是否有相机
     *
     * @param context
     * @return
     */
    public static boolean hasCamera(Context context) {
        if (_hasCamera == null) {
            PackageManager pckMgr = context
                    .getPackageManager();
            boolean flag = pckMgr
                    .hasSystemFeature("android.hardware.camera.front");
            boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
            boolean flag2;
            flag2 = flag || flag1;
            _hasCamera = flag2;
        }
        return _hasCamera;
    }

    /**
     * 设备是否有实体菜单
     *
     * @param context
     * @return
     */
    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag;
        if (PRE_HC) {
            flag = true;
        } else if (GTE_ICS) {
            flag = ViewConfiguration.get(context).hasPermanentMenuKey();
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 当前是否有网
     *
     * @param context
     * @return
     */
    public static boolean hasInternet(Context context) {
        boolean flag;
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        flag = manager != null && manager.getActiveNetworkInfo() != null;
        return flag;
    }

    /**
     * 当前的包是否存在
     *
     * @param context
     * @param pckName
     * @return
     */
    public static boolean isPackageExist(Context context, String pckName) {
        try {
            PackageInfo pckInfo = context.getPackageManager()
                    .getPackageInfo(pckName, 0);
            if (pckInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TDvice", e.getMessage());
        }
        return false;
    }

    public static void hideAnimatedView(View view) {
        if (PRE_HC && view != null) {
            view.setPadding(view.getWidth(), 0, 0, 0);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(
                    view.getWindowToken(), 0);
        }
    }

    /**
     * 是否是横屏
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        boolean flag;
        flag = context.getResources().getConfiguration().orientation == 2;
        return flag;
    }

    /**
     * 是否是竖屏
     *
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        boolean flag = true;
        if (context.getResources().getConfiguration().orientation != 1) {
            flag = false;
        }
        return flag;
    }

    public static boolean isTablet(Context context) {
        if (_isTablet == null) {
            boolean flag;
            flag = (0xf & context.getResources()
                    .getConfiguration().screenLayout) >= 3;
            _isTablet = flag;
        }
        return _isTablet;
    }

    public static void showAnimatedView(View view) {
        if (PRE_HC && view != null) {
            view.setPadding(0, 0, 0, 0);
        }
    }

    public static void showSoftKeyboard(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(4);
    }

    public static void showSoftKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
    }

    public static void toogleSoftKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public static String getCurCountryLan(Context context) {
        return context.getResources().getConfiguration().locale
                .getLanguage()
                + "-"
                + context.getResources().getConfiguration().locale
                .getCountry();
    }

    public static boolean isZhCN(Context context) {
        String lang = context.getResources()
                .getConfiguration().locale.getCountry();
        return "CN".equalsIgnoreCase(lang);
    }

    public static String percent(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        str = nf.format(p3);
        return str;
    }

    public static String percent2(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        str = nf.format(p3);
        return str;
    }

    public static boolean isHaveMarket(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        return infos.size() > 0;
    }

    public static void setFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow()
                .getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static void cancelFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow()
                .getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static PackageInfo getPackageInfo(Context context, String pckName) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(pckName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode;
        try {
            versionCode = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 获取指定包名应用的版本号
     *
     * @param context
     * @param packageName
     * @return
     */
    public static int getVersionCode(Context context, String packageName) {
        int versionCode;
        try {
            versionCode = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String name;
        try {
            name = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            name = "";
        }
        return name;
    }

    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }

    /**
     * 安装应用
     *
     * @param context
     * @param file
     */
    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static Intent getInstallApkIntent(File file) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param number
     */
    public static void openDial(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }

    public static void openSMS(Context context, String smsBody, String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsBody);
        context.startActivity(it);
    }

    public static void openDail(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openSendMsg(Context context) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openCamera(Context context) {
        Intent intent = new Intent(); // 调用照相机
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setFlags(0x34c40000);
        context.startActivity(intent);
    }

    public static String getIMEI(Context context) {
        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }

    public static String getPhoneType() {
        return Build.MODEL;
    }

    public static void openApp(Context context, String packageName) {
        Intent mainIntent = context.getPackageManager()
                .getLaunchIntentForPackage(packageName);
        if (mainIntent == null) {
            mainIntent = new Intent(packageName);
        }
        context.startActivity(mainIntent);
    }

    public static boolean openAppActivity(Context context, String packageName,
                                          String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * wifi是否开启
     *
     * @param context
     * @return
     */
    public static boolean isWifiOpen(Context context) {
        boolean isWifiConnect = false;
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // check the networkInfos numbers
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (NetworkInfo networkInfo : networkInfos) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }

    /**
     * 卸载软件
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApk(Context context, String packageName) {
        if (isPackageExist(context, packageName)) {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
                    packageURI);
            context.startActivity(uninstallIntent);
        }
    }

    @SuppressWarnings("deprecation")
    public static void copyTextToBoard(Context context, String string) {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        ClipboardManager clip = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(string);
    }

    /**
     * 发送邮件
     *
     * @param context
     * @param subject 主题
     * @param content 内容
     * @param emails  邮件地址
     */
    public static void sendEmail(Context context, String subject,
                                 String content, String... emails) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 模拟器
            // intent.setType("text/plain");
            intent.setType("message/rfc822"); // 真机
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int getStatuBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, sbar = 38;// 默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources()
                    .getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    public static boolean hasStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        return (attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 调用系统安装了的应用分享
     *
     * @param context
     * @param title
     * @param url
     */
    public static void showSystemShareOption(Activity context,
                                             final String title, final String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
        context.startActivity(Intent.createChooser(intent, "选择分享"));
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (extraInfo != null && !extraInfo.isEmpty()) {
                if ("cmnet".equalsIgnoreCase(extraInfo)) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    public static boolean netIsConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //手机网络连接状态
        NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //WIFI连接状态
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //当前无可用的网络
        return mobNetInfo.isConnected() || wifiNetInfo.isConnected();
    }

    /**
     * 判断是否存在sd卡
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}


