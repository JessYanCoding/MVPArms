package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.app.utils.RxUtils;
import me.jessyan.mvparms.demo.mvp.contract.UserDetailContract;
import me.jessyan.mvparms.demo.mvp.model.entity.TextContent;
import me.jessyan.mvparms.demo.mvp.ui.adapter.TextContentAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * Created by xiaobailong24 on 2017/5/19.
 * MVP UserDetailPresenter
 */

@FragmentScope
public class UserDetailPresenter extends BasePresenter<UserDetailContract.Model, UserDetailContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<TextContent> mContents = new ArrayList<>();
    private BaseQuickAdapter mAdapter;

    @Inject
    public UserDetailPresenter(UserDetailContract.Model model, UserDetailContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;

        mContents.add(new TextContent("Username", ""));
        mContents.add(new TextContent("Name", ""));
        mContents.add(new TextContent("Company", ""));
        mContents.add(new TextContent("Location", ""));
        mContents.add(new TextContent("Public Repos", ""));
        mContents.add(new TextContent("Followers", ""));
        mContents.add(new TextContent("Following", ""));
        mContents.add(new TextContent("Created", ""));
        mContents.add(new TextContent("Updated", ""));
    }


    public void requestUserDetail(String username, boolean update) {
        if (mAdapter == null) {
            mAdapter = new TextContentAdapter(mContents);
        }
        mRootView.setAdapter(mAdapter);
        // TODO: 2017/5/19
        mModel.getUserDetail(username, update)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable ->
                        mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() ->
                        mRootView.hideLoading())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .flatMap(userDetail -> {
                    mContents.clear();
                    mContents.add(new TextContent("Username", userDetail.getLogin()));
                    mContents.add(new TextContent("Name", userDetail.getName()));
                    mContents.add(new TextContent("Company", userDetail.getCompany()));
                    mContents.add(new TextContent("Location", userDetail.getLocation()));
                    mContents.add(new TextContent("Public Repos", String.valueOf(userDetail.getPublicRepos())));
                    mContents.add(new TextContent("Followers", String.valueOf(userDetail.getFollowers())));
                    mContents.add(new TextContent("Following", String.valueOf(userDetail.getFollowing())));
                    mContents.add(new TextContent("Created", userDetail.getCreatedAt()));
                    mContents.add(new TextContent("Updated", userDetail.getUpdatedAt()));
                    return Observable.just(mAdapter);
                })
                .subscribe(new ErrorHandleSubscriber<BaseQuickAdapter>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseQuickAdapter baseQuickAdapter) {
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        this.mContents = null;
        this.mAdapter = null;
    }

}