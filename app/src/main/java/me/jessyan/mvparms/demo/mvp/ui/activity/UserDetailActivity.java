package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.OnClick;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.fragment.UserDetailFragment;
import timber.log.Timber;

/**
 * Created by xiaobailong24 on 2017/5/19.
 * MVP UserDetailActivity
 * 使用Fragment，并保证在屏幕旋转等Activity重启时Fragment不重复创建
 * http://blog.csdn.net/lmj623565791/article/details/37992017
 */

public class UserDetailActivity extends BaseActivity {

    private UserDetailFragment mDetailFragment;
    private String mUsername;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_user_detail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mUsername = getIntent().getStringExtra("username");
        Timber.w("initData: mUsername--->" + mUsername);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (savedInstanceState == null) {
            mDetailFragment = UserDetailFragment.newInstance();
            transaction.add(R.id.user_detail_frame, mDetailFragment, "UserDetailFragment").commit();
            mDetailFragment.setData(mUsername);
        } else {
            mDetailFragment = (UserDetailFragment) fm.findFragmentByTag("UserDetailFragment");
            transaction.replace(R.id.user_detail_frame, mDetailFragment, "UserDetailFragment").commit();
        }
    }

    @OnClick(R.id.refresh)
    public void onViewClicked() {
        mDetailFragment.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mDetailFragment = null;
        this.mUsername = null;
    }
}
