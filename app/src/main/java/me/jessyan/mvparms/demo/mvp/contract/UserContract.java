package me.jessyan.mvparms.demo.mvp.contract;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.mvp.BaseView;

import java.util.List;

import me.jessyan.mvparms.demo.mvp.model.entity.User;
import rx.Observable;

/**
 * Created by jess on 9/4/16 10:47
 * Contact with jess.yan.effort@gmail.com
 */
public interface UserContract {
    interface View extends BaseView {
        void setAdapter(DefaultAdapter adapter);
        void startLoadMore();
        void endLoadMore();
    }

    interface Model {
        Observable<List<User>> getUsers(int lastIdQueried, boolean update);
    }
}
