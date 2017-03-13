package com.jess.arms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseApplication;

import org.simple.eventbus.EventBus;

import java.security.MessageDigest;

import static com.jess.arms.base.AppManager.APPMANAGER_MESSAGE;
import static com.jess.arms.base.AppManager.APP_EXIT;
import static com.jess.arms.base.AppManager.KILL_ALL;
import static com.jess.arms.base.AppManager.SHOW_SNACKBAR;
import static com.jess.arms.base.AppManager.START_ACTIVITY;

/**
 * Created by jess on 2015/11/23.
 */
public class UiUtils {
    static public Toast mToast;

    /**
     * 设置hint大小
     *
     * @param size
     * @param v
     * @param res
     */
    public static void setViewHintSize(int size, TextView v, int res) {
        SpannableString ss = new SpannableString(getResources().getString(
                res));
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        // 附加属性到文本  
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint  
        v.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }


    /**
     * dip转pix
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获得资源
     */
    public static Resources getResources() {
        return BaseApplication.getContext().getResources();
    }

    /**
     * 得到字符数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * pix转dip
     */
    public static int pix2dip(int pix) {
        final float densityDpi = getResources().getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }

    /**
     * 获得上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }


    /**
     * 从dimens中获得尺寸
     *
     * @param homePicHeight
     * @return
     */

    public static int getDimens(int homePicHeight) {
        return (int) getResources().getDimension(homePicHeight);
    }

    /**
     * 从dimens中获得尺寸
     *
     * @param
     * @return
     */

    public static float getDimens(String dimenNmae) {
        return getResources().getDimension(getResources().getIdentifier(dimenNmae, "dimen", getContext().getPackageName()));
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(int stringID) {
        return getResources().getString(stringID);
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(String strName) {
        return getString(getResources().getIdentifier(strName, "string", getContext().getPackageName()));
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(View view, String viewName) {
        int id = getResources().getIdentifier(viewName, "id", getContext().getPackageName());
        T v = (T) view.findViewById(id);
        return v;
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Activity activity, String viewName) {
        int id = getResources().getIdentifier(viewName, "id", getContext().getPackageName());
        T v = (T) activity.findViewById(id);
        return v;
    }

    /**
     * 根据lauout名字获得id
     *
     * @param layoutName
     * @return
     */
    public static int findLayout(String layoutName) {
        int id = getResources().getIdentifier(layoutName, "layout", getContext().getPackageName());
        return id;
    }

    /**
     * 填充view
     *
     * @param detailScreen
     * @return
     */
    public static View inflate(int detailScreen) {
        return View.inflate(getContext(), detailScreen, null);
    }

    /**
     * 单列toast
     *
     * @param string
     */

    public static void makeText(String string) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), string, Toast.LENGTH_SHORT);
        }
        mToast.setText(string);
        mToast.show();
    }

    /**
     * 用snackbar显示
     *
     * @param text
     */
    public static void SnackbarText(String text) {
        Message message = new Message();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        message.arg1 = 0;
        EventBus.getDefault().post(message, APPMANAGER_MESSAGE);
    }

    /**
     * 用snackbar长时间显示
     *
     * @param text
     */
    public static void SnackbarTextWithLong(String text) {
        Message message = new Message();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        message.arg1 = 1;
        EventBus.getDefault().post(message, APPMANAGER_MESSAGE);
    }


    /**
     * 通过资源id获得drawable
     *
     * @param rID
     * @return
     */
    public static Drawable getDrawablebyResource(int rID) {
        return getResources().getDrawable(rID);
    }

    /**
     * 跳转界面
     *
     * @param activity
     * @param homeActivityClass
     */
    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(getContext(), homeActivityClass);
        activity.startActivity(intent);
    }

    /**
     * 跳转界面3
     *
     * @param
     * @param homeActivityClass
     */
    public static void startActivity(Class homeActivityClass) {
        Message message = new Message();
        message.what = START_ACTIVITY;
        message.obj = homeActivityClass;
        EventBus.getDefault().post(message, APPMANAGER_MESSAGE);
    }

    /**
     * 跳转界面3
     *
     * @param
     */
    public static void startActivity(Intent content) {
        Message message = new Message();
        message.what = START_ACTIVITY;
        message.obj = content;
        EventBus.getDefault().post(message, APPMANAGER_MESSAGE);
    }

    /**
     * 跳转界面4
     *
     * @param
     */
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    public static int getLayoutId(String layoutName) {
        return getResources().getIdentifier(layoutName, "layout", getContext().getPackageName());
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth() {
        return getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 获得颜色
     */
    public static int getColor(int rid) {
        return getResources().getColor(rid);
    }

    /**
     * 获得颜色
     */
    public static int getColor(String colorName) {
        return getColor(getResources().getIdentifier(colorName, "color", getContext().getPackageName()));
    }

    /**
     * 移除孩子
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }


    /**
     * MD5
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String MD5encode(String string) {
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    /**
     * 全屏，并且沉侵式状态栏
     *
     * @param activity
     */
    public static void statuInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    /**
     * 配置recycleview
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecycleView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public static void killAll(){
        Message message = new Message();
        message.what = KILL_ALL;
        EventBus.getDefault().post(message, APPMANAGER_MESSAGE);
    }

    public static void exitApp(){
        Message message = new Message();
        message.what = APP_EXIT;
        EventBus.getDefault().post(message, APPMANAGER_MESSAGE);
    }

}
