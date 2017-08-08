package com.jess.arms.mvp;

import android.content.Intent;

/**
 *
 * deg 显示加载错误
 * Created by QING on 16/4/22.
 */
public interface IView {

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(String message);

    /**
     * 跳转activity
     */
    void launchActivity(Intent intent);
    /**
     * 杀死自己
     */
    void killMyself();

//    //显示加载失败 一般都是没有网络的原因 可以写成showNoNetwork(); showError ?
//    void showNoNetwork();
//
//    //显示无数据
//    void showNoData();
}
