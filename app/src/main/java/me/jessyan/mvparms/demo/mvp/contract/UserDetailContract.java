package me.jessyan.mvparms.demo.mvp.contract;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.UserDetail;

/**
 * Created by xiaobailong24 on 2017/5/19.
 * MVP UserDetailContract
 */

public interface UserDetailContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setAdapter(BaseQuickAdapter adapter);

    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<UserDetail> getUserDetail(String username, boolean update);
    }
}