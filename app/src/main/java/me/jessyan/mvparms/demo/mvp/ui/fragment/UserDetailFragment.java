package me.jessyan.mvparms.demo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.DaggerUserDetailComponent;
import me.jessyan.mvparms.demo.di.module.UserDetailModule;
import me.jessyan.mvparms.demo.mvp.contract.UserDetailContract;
import me.jessyan.mvparms.demo.mvp.presenter.UserDetailPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by xiaobailong24 on 2017/5/19.
 * MVP UserDetailFragment
 */

public class UserDetailFragment extends BaseFragment<UserDetailPresenter> implements UserDetailContract.View {

    @BindView(R.id.username)
    AppCompatTextView mUsername;

    private String username;

    public static UserDetailFragment newInstance() {
        return new UserDetailFragment();
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerUserDetailComponent
                .builder()
                .appComponent(appComponent)
                .userDetailModule(new UserDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mUsername.setText(username);
    }


    @Override
    public void setData(Object data) {
        username = (String) data;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

}