package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.widget.update.UpdateDialogFragment;

/**
 * Created by chan on 2017/8/26.
 */
public class DownloadActivity extends BaseActivity {

    @BindView(R.id.show_dialog)
    Button mShowDialog;

    private UpdateDialogFragment mUpdateDialogFragment;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle saanceState) {
        return R.layout.activity_download;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.show_dialog)
    public void onViewClicked() {
        if (mUpdateDialogFragment == null)
            mUpdateDialogFragment = new UpdateDialogFragment();

        mUpdateDialogFragment.show(getFragmentManager(), "UpdateDialogFragment");
    }
}
