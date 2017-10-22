package me.qing.eye.pubblico.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import me.qing.eye.pubblico.app.MyApp;

/**
 * 网络相关工具集合
 * <p>
 * Created by QING on 2017/10/22.
 */

public class NetworkUtil {

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkAvailable(){
        return isNetworkAvailable(MyApp.getInstance());
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isAvailable();
        }
    }



    /**
     * 判断当前是否是移动网络
     */
    public static boolean isMobileAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null
                    && (info.getType() == ConnectivityManager.TYPE_MOBILE);
        }
    }

    /**
     * 判断当前是否是WIFI网络
     */
    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null
                    && (info.getType() == ConnectivityManager.TYPE_WIFI);
        }
    }




}
