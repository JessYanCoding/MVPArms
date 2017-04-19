package com.jess.arms.common.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.View;

public class DialogUtil {

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(msg);
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(Html.fromHtml(msg));
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }


    public static AlertDialog.Builder dialogBuilder(Context context, int title, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) {
            builder.setView(view);
        }
        if (title > 0) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, int titleResId, int msgResId) {
        String title = titleResId > 0 ? context.getResources().getString(titleResId) : null;
        String msg = msgResId > 0 ? context.getResources().getString(msgResId) : null;
        return dialogBuilder(context, title, msg);
    }


    public static Dialog createDialog(Context context, String title, String des,boolean cancelable) {
        return createDialog(context, title, des, null, null,cancelable);
    }

    public static Dialog createDialog(Context context, int title, int des,boolean cancelable) {
        return createDialog(context, context.getString(title), context.getString(des),cancelable);
    }

    public static Dialog createDialog(Context context, int title, int des, int btn, DialogInterface.OnDismissListener dismissListener,boolean cancelable) {
        return createDialog(context, context.getString(title), context.getString(des), context.getString(btn), dismissListener,cancelable);
    }

    public static Dialog createDialog(Context context, String title, String des) {
        return createDialog(context, title, des, null, null,true);
    }

    public static Dialog createDialog(Context context, int title, int des) {
        return createDialog(context, context.getString(title), context.getString(des),true);
    }

    public static Dialog createDialog(Context context, int title, int des, int btn, DialogInterface.OnDismissListener dismissListener) {
        return createDialog(context, context.getString(title), context.getString(des), context.getString(btn), dismissListener,true);
    }

    public static Dialog createDialog(Context context, String title, String des, String btn, DialogInterface.OnDismissListener dismissListener,boolean cancelable) {
        AlertDialog.Builder builder = dialogBuilder(context, title, des);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(btn, null);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }
}
