package com.jess.arms.common.utils;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

/**
 * Created by jess on 17/10/2016 10:09
 * Contact with jess.yan.effort@gmail.com
 */

public class PermissionUtil {

    /**
     * 请求摄像头权限
     */
    public static Observable<Boolean> launchCamera(RxPermissions rxPermissions) {
       return rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    /**
     * 请求外部存储的权限
     */
    public static Observable<Boolean> externalStorage( RxPermissions rxPermissions) {
        return rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 请求发送短信权限
     */
    public static Observable<Boolean> sendSms(RxPermissions rxPermissions) {
        return rxPermissions.request(Manifest.permission.SEND_SMS);
    }

    /**
     * 请求打电话权限
     */
    public static Observable<Boolean> callPhone(RxPermissions rxPermissions) {
        return rxPermissions.request(Manifest.permission.CALL_PHONE);
    }

    /**
     * 请求获取手机状态的权限
     */
    public static Observable<Boolean> readPhonestate(RxPermissions rxPermissions) {
        return rxPermissions.request(Manifest.permission.READ_PHONE_STATE);
    }

}
