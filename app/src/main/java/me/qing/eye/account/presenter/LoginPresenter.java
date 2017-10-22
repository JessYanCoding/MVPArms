package me.qing.eye.account.presenter;

import com.jess.arms.mvp.BasePresenter;

import me.qing.eye.account.contract.LoginContract;
import me.qing.eye.account.model.IAccountModel;
import me.qing.eye.account.model.impl.AccountModel;

/**
 * Created by QING on 2017/10/22.
 */

public class LoginPresenter extends BasePresenter<IAccountModel,LoginContract.View>
        implements LoginContract.Presenter{

    public LoginPresenter(LoginContract.View view){
        mRootView = view;
        mModel = new AccountModel();
    }

    @Override
    public void requestLogin(String userName, String pwd) {
        mRootView.showLoading();
        mModel.login(userName,pwd);
        // TODO: 2017/10/22 如何结束转圈呢
    }
}
