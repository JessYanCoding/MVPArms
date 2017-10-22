package me.qing.eye.account.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

/**
 * 登录 Contract
 * 
 * Created by QING on 2017/10/22.
 */

public interface LoginContract {
    
    interface View extends IView{

        /**
         *  显示登录成功
         */
        void showLoginSuc();
    }
    
    interface Model extends IModel{

        /**
         * 登录
         */
        void login(String username,String pwd);
    }

    // TODO: 2017/10/22 think
    
    interface Presenter{

        /**
         * 请求登录
         */
        void  requestLogin(String username,String pwd);
    }
}
