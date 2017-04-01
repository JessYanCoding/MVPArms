package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.RetryWithDelay;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.mvp.contract.UserContract;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import me.jessyan.mvparms.demo.mvp.ui.adapter.UserAdapter;

/**
 * Created by jess on 9/4/16 10:59
 * Contact with jess.yan.effort@gmail.com
 */
@ActivityScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    private AppManager mAppManager;
    private Application mApplication;
    private List<User> mUsers = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int lastUserId = 1;
    private boolean isFirst = true;


    @Inject
    public UserPresenter(UserContract.Model model, UserContract.View rootView,
                         AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mAppManager = appManager;
        mAdapter = new UserAdapter(mUsers);
        mRootView.setAdapter(mAdapter);//设置Adapter
    }

    public void requestUsers(final boolean pullToRefresh) {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(() -> {
            //request permission success, do something.
        }, mRootView.getRxPermissions(), mRootView);


        if (pullToRefresh)
            lastUserId = 1;//上拉刷新默认只请求第一页

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }

        mModel.getUsers(lastUserId, isEvictCache)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示上拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示下拉加载更多的进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(users -> {
                            lastUserId = users.get(users.size() - 1).getId();//记录最后一个id,用于下一次请求
                            if (pullToRefresh)
                                mUsers.clear();//如果是上拉刷新则清空列表
                            mUsers.addAll(users);
                            mAdapter.notifyDataSetChanged();//通知更新数据
                        },
                        throwable -> UiUtils.SnackbarText("net error")
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mUsers = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
