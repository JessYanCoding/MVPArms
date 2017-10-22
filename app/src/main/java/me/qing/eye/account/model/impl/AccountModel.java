package me.qing.eye.account.model.impl;

import me.qing.eye.account.model.IAccountModel;
import me.qing.eye.pubblico.util.NetworkUtil;
import me.qing.eye.pubblico.util.SpUtil;

/**
 * Created by QING on 2017/10/22.
 */

public class AccountModel implements IAccountModel {

    @Override
    public void login(String username, String pwd) {

        // TODO: 2017/10/22 think 这一段是否要放到Presenter中呢

        //1.判断当前是否为免登录
        final boolean isLocalLogin = SpUtil.getsInstance().isLocalLogin();

        //2.判断无网络且不为免登录时弹窗提示
        if (!NetworkUtil.isNetworkAvailable() && !isLocalLogin){
//            ToastUtil.show(getString(R.string.net_error));
            // TODO: 2017/10/22 think 如何通知View呢 暂时先不管，弹出Toast
            // TODO: 2017/10/22 这个时候如何结束转圈呢
            return;
        }

        //3.要开始转圈（但这个动作应该发生在，点击登陆按钮生效的时候）



    }

    @Override
    public void onDestroy() {

    }
}
