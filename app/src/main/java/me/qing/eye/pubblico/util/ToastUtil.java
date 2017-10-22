package me.qing.eye.pubblico.util;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import me.qing.eye.pubblico.app.MyApp;

/**
 * ToastUtil
 *
 * Created by QING on 2017/10/21.
 *
 * 待优化
 */
public class ToastUtil {

    private static Toast sToast = new Toast(MyApp.getInstance());

    private ToastUtil() {
    }

    public static void show(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        sToast.setText(text);
        if (text.length() < 10) {
            sToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            sToast.setDuration(Toast.LENGTH_LONG);
        }
        sToast.show();
    }

    public static void show(@StringRes int resId) {
        show(MyApp.getInstance().getString(resId));
    }

}