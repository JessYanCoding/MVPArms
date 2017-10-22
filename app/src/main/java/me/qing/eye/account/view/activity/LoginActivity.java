package me.qing.eye.account.view.activity;

import android.os.Bundle;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.R;
import me.qing.eye.pubblico.base.BaseAty;

/**
 * 登录页
 *
 * Created by QING on 2017/10/22.
 */

public class LoginActivity extends BaseAty {
    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
