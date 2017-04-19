package com.jess.arms.mvp;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by jess on 16/4/22.
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
    void showMessage(@NonNull String message);

    /**
     * 跳转activity
     */
    void launchActivity(@NonNull Intent intent);
    /**
     * 杀死自己
     */
    void killMyself();
}
