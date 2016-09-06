package me.jessyan.mvparms.demo.mvp.presenter;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.UserContract;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import me.jessyan.mvparms.demo.mvp.ui.adapter.UserAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by jess on 9/4/16 10:59
 * Contact with jess.yan.effort@gmail.com
 */
@ActivityScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    private RxErrorHandler mErrorHandler;
    private List<User> mUsers = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int lastUserId = 1;


    @Inject
    public UserPresenter(UserContract.Model model, UserContract.View rootView, RxErrorHandler handler) {
        super(model, rootView);
        this.mErrorHandler = handler;
        mAdapter = new UserAdapter(mUsers);
        mRootView.setAdapter(mAdapter);//设置Adapter
    }

    public void requestUsers(final boolean pullToRefresh) {
        if (pullToRefresh) lastUserId = 1;

        mModel.getUsers(lastUserId, pullToRefresh)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (pullToRefresh)
                            mRootView.showLoading();//显示上拉刷新的进度条
                        else
                            mRootView.startLoadMore();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (pullToRefresh)
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        else
                            mRootView.endLoadMore();
                    }
                })
                .compose(((BaseActivity)mRootView).<List<User>>bindToLifecycle())//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<List<User>>(mErrorHandler) {
            @Override
            public void onNext(List<User> users) {
                lastUserId = users.get(users.size() - 1).getId();//记录最后一个id,用于下一次请求
                if (pullToRefresh) mUsers.clear();//如果是上拉刷新则晴空列表
                for (User user : users) {
                    mUsers.add(user);
                }
                mAdapter.notifyDataSetChanged();//通知更新数据
            }
        });
    }
}
