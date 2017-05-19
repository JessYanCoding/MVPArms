package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.support.v4.app.FragmentManager;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.fragment.UserDetailFragment;
import timber.log.Timber;

/**
 * Created by xiaobailong24 on 2017/5/19.
 * MVP UserDetailActivity
 */

public class UserDetailActivity extends BaseActivity {

    private UserDetailFragment mDetailFragment;
    private String username;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView() {
        return R.layout.activity_user_detail;
    }

    @Override
    public void initData() {
        username = getIntent().getStringExtra("username");
        Timber.w("initData: username--->" + username);

        FragmentManager fm = getSupportFragmentManager();
        mDetailFragment = (UserDetailFragment) fm.findFragmentByTag("UserDetailFragment");

        if (mDetailFragment == null) {
            mDetailFragment = UserDetailFragment.newInstance();
            fm.beginTransaction().replace(R.id.user_detail_frame, mDetailFragment, "UserDetailFragment").commit();
            mDetailFragment.setData(username);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        mDetailFragment.setData(username);
    }
}
