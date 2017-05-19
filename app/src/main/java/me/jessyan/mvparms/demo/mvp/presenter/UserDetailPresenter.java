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

import me.jessyan.mvparms.demo.mvp.contract.UserDetailContract;
import me.jessyan.mvparms.demo.mvp.model.entity.TextContent;
import me.jessyan.mvparms.demo.mvp.ui.adapter.TextContentAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


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
            mRootView.setAdapter(mAdapter);
        }
        // TODO: 2017/5/19
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}