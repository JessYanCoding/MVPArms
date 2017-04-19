package com.jess.arms.common.assist;

import android.content.Context;
import android.widget.Toast;

public class Toastor {

    private static Toast   mToast;
    /*private Context context;

    public Toastor(Context context) {
        this.context = context.getApplicationContext();
    }*/

    public static Toast getSingletonToast(Context context, int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(resId);
        }
        return mToast;
    }

    public static Toast getSingletonToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        return mToast;
    }

    public static Toast getSingleLongToast(Context context, int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
        }else{
            mToast.setText(resId);
        }
        return mToast;
    }

    public static Toast getSingleLongToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }else{
            mToast.setText(text);
        }
        return mToast;
    }

    public static Toast getToast(Context context, int resId) {
        return Toast.makeText(context, resId, Toast.LENGTH_SHORT);
    }

    public static Toast getToast(Context context, String text) {
        return Toast.makeText(context, text, Toast.LENGTH_SHORT);
    }

    public static Toast getLongToast(Context context, int resId) {
        return Toast.makeText(context, resId, Toast.LENGTH_LONG);
    }

    public static Toast getLongToast(Context context, String text) {
        return Toast.makeText(context, text, Toast.LENGTH_LONG);
    }

    public static void showSingletonToast(Context context, int resId) {
        getSingletonToast(context, resId).show();
    }


    public static void showSingletonToast(Context context, String text) {
        getSingletonToast(context, text).show();
    }

    public static void showSingleLongToast(Context context, int resId) {
        getSingleLongToast(context, resId).show();
    }


    public static void showSingleLongToast(Context context, String text) {
        getSingleLongToast(context, text).show();
    }

    public static void showToast(Context context, int resId) {
        getToast(context, resId).show();
    }

    public static void showToast(Context context, String text) {
        getToast(context, text).show();
    }

    public static void showLongToast(Context context, int resId) {
        getLongToast(context, resId).show();
    }

    public static void showLongToast(Context context, String text) {
        getLongToast(context, text).show();
    }

}
