package com.jess.arms.common.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.View;

public class ProgressDialogUtil {

    public static ProgressDialog dialogBuilder(Context context, String title, String msg) {
        ProgressDialog builder = new ProgressDialog(context);
        if (msg != null) {
            builder.setMessage(msg);
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static ProgressDialog dialogBuilder(Context context, String title, String msg, int i) {
        ProgressDialog builder = new ProgressDialog(context);
        if (msg != null) {
            builder.setMessage(Html.fromHtml(msg));
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }


    public static ProgressDialog dialogBuilder(Context context, int title, View view) {
        ProgressDialog builder = new ProgressDialog(context);
        if (view != null) {
            builder.setView(view);
        }
        if (title > 0) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static ProgressDialog dialogBuilder(Context context, int titleResId, int msgResId) {
        String title = titleResId > 0 ? context.getResources().getString(titleResId) : null;
        String msg = msgResId > 0 ? context.getResources().getString(msgResId) : null;
        return dialogBuilder(context, title, msg);
    }


    public static Dialog createProgressDialog(Context context, String title, String des,boolean cancelable) {
        return createProgressDialog(context, title, des, null,cancelable);
    }

    public static Dialog createProgressDialog(Context context, int title, int des,boolean cancelable) {
        return createProgressDialog(context, context.getString(title), context.getString(des),cancelable);
    }

    public static Dialog createProgressDialog(Context context, int title, int des, int btn, DialogInterface.OnDismissListener dismissListener,boolean cancelable) {
        return createProgressDialog(context, context.getString(title), context.getString(des),  dismissListener,cancelable);
    }

    public static Dialog createProgressDialog(Context context, String title, String des) {
        return createProgressDialog(context, title, des, null,true);
    }

    public static Dialog createProgressDialog(Context context, int title, int des) {
        return createProgressDialog(context, context.getString(title), context.getString(des),true);
    }

    public static Dialog createProgressDialog(Context context, int title, int des, int btn, DialogInterface.OnDismissListener dismissListener) {
        return createProgressDialog(context, context.getString(title), context.getString(des),  dismissListener,true);
    }



    public static Dialog createProgressDialog(Context context, String title, String des,  DialogInterface.OnDismissListener dismissListener,boolean cancelable) {
        ProgressDialog builder = dialogBuilder(context, title, des);
        builder.setCancelable(cancelable);
        builder.setCanceledOnTouchOutside(true);
        builder.setOnDismissListener(dismissListener);
        return builder;
    }
}
