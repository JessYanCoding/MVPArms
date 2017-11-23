package me.qing.eye.account.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.mvparms.demo.R;
import me.qing.eye.account.contract.LoginContract;
import me.qing.eye.account.presenter.LoginPresenter;
import me.qing.eye.pubblico.base.BaseAty;
import me.qing.eye.pubblico.util.ToastUtil;

/**
 * 登录页
 *
 * Created by QING on 2017/10/22.
 */

public class LoginActivity extends BaseAty<LoginPresenter> implements LoginContract.View{

    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.et_username)
    EditText etUser;
    @BindView(R.id.et_pwd_login)
    EditText etPwd;



    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mPresenter = new LoginPresenter(this);
    }

    /**
     * IView相关
     */

    @Override
    public void showLoginSuc() {

    }

    @Override
    public void showLoading() {

        // TODO: 2017/10/22 这个转圈应该封装到BaseActivity中吧，那BaseFragment呢？ 有BaseUI么 
        // TODO: 2017/10/22 显示转圈啊!!  封装控件 

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showError(int Code, String msg) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }


    @OnClick({R.id.bt_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_login:
                confirmLogin();
                break;
        }
    }

    /**
     * 业务逻辑
     */


    // TODO: 2017/10/22 think 判空放到哪里？

    /**
     * 提交登录
     */
    private void confirmLogin() {

        String userName = etUser.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(userName)){
            ToastUtil.show("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            ToastUtil.show("密码不能为空");
            return;
        }
        mPresenter.requestLogin(userName,pwd);

    }


}
